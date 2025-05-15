package com.hollywooddos.jessica;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

import com.hollywooddos.jessica.data.TmdbDatabaseClient;
import com.hollywooddos.jessica.views.SeparationListEntry;

import info.movito.themoviedbapi.tools.TmdbException;

// code from algorithms class with Dr. Xu:
// Note that Dr. Xu's BFS psuedocode is for exploring the entire graph, 
// but we start the breadth first search with a starting actor and a target actor.
// BSF(G)
// 1. for each vertex u ∈ G.V
// 2. mark u as unvisited
// 3. u.π
// 4. for each vertex u ∈ G.V
// 5. if u is unvisited
// 6. BFS-Visit(G, u)
// BFS-Visit(G, s)
// mark all vertices as unvisited (line is already completed by BSF(G)
// line 1. mark s as visited
// line 2. s.π = NIL
// line 3. Q = ∅
// line 4. ENQUEUE(Q, s)
// line 5. while Q != ∅
// line 6. u = DEQUEUE(Q)
// line 7. for each v in G.Adj[u]
// line 8. if v is unvisited
// line 9. mark v as visited
// line 10. v.π = u
// line 11. ENQUEUE(Q, v)

public class ActorConnectionFinder {

    private TmdbDatabaseClient client;

    public ActorConnectionFinder() {
        this.client = new TmdbDatabaseClient();
    }

    // Helper class that stores the predecessor of an actor and the movie that links
    // them.
    private static class Predecessor implements SeparationListEntry {
        Actor parent;
        Movie connectingMovie; // the movie that links parent with the current actor

        private Predecessor(Actor parent, Movie connectingMovie) {
            this.parent = parent;
            this.connectingMovie = connectingMovie;
        }

        @Override
        public String getText() {
            return "Actor " + parent.getName() + " in " + connectingMovie.getName();
        }

        @Override
        public void setText(String text) {
            // This method is not used in this context.
            throw new UnsupportedOperationException("setText is not supported");
        }

        @Override
        public void setIsLast(boolean isLast) {
            // TODO Auto-generated method stub
        }
    }

    public List<SeparationListEntry> findConnection(Actor startActor, Actor targetActor) throws TmdbException {
        // create a queue for the breadth first search
        Queue<Actor> queue = new LinkedList<>();
        // step 1 and 2 from pseudocode:
        // 1. for each vertex u ∈ G.V
        // 2. mark u as unvisited
        // We do these steps implicitly by creating a new, empty set which means
        // that all actors are unvisited at the start.
        Map<Actor, Predecessor> cameFrom = new HashMap<>();
        Set<Actor> visited = new HashSet<>();
        Map<Actor, List<Movie>> moviesForActorCache = new HashMap<>();
        Map<Movie, List<Actor>> actorsForMovieCache = new HashMap<>();

        // Debug: print starting actor information
        System.out.println("Breadth First Search beginning with the starting actor: " + startActor.getName());
        // steps 4, 5, and 6
        // for each vertex u ∈ G.V if u is unvisited call BFS-Visit(G, u)
        // enqueue the starting actor into the queue
        queue.add(startActor);
        visited.add(startActor);

        // while loop is out BFS-Visit
        while (!queue.isEmpty()) {

            Actor currentActor = queue.poll();
            // Debug: track current actor being expanded
            System.out.println("Expanding actor: " + currentActor.getName());

            // Cache movies for the current actor.
            List<Movie> movies = moviesForActorCache.computeIfAbsent(currentActor, actor -> {
                List<Movie> mvs = client.getMoviesForActor(actor);
                System.out.println("Found " + (mvs != null ? mvs.size() : 0) + " movies for " + actor.getName());
                return mvs;
            });

            // If no movies are found, log that information.
            if (movies.isEmpty()) {
                System.out.println("No movies found for actor" + currentActor.getName());
            }

            for (Movie movie : movies) {
                // Debug: log the movie being processed
                System.out.println("Processing movie: " + movie.getName() + " for actor " + currentActor.getName());

                // Cache actors for the current movie.
                List<Actor> coActors = actorsForMovieCache.computeIfAbsent(movie, m -> {
                    List<Actor> acs = client.getActorsForMovie(m);
                    System.out.println(
                            "Found " + (acs != null ? acs.size() : 0) + " co-actors from the movie " + movie.getName());
                    return acs;
                });

                for (Actor coActor : coActors) {
                    System.out.println("Checking co-actor: " + coActor.getName());
                    if (!visited.contains(coActor)) {
                        System.out.println(
                                "Adding co-actor: " + coActor.getName() + " from the movie: " + movie.getName() + ")");
                        visited.add(coActor);
                        cameFrom.put(coActor, new Predecessor(currentActor, movie));
                        queue.add(coActor);

                        if (coActor.equals(targetActor)) {
                            System.out.println("Target actor " + targetActor.getName() + " found!");
                            return buildPath(cameFrom, startActor, targetActor);
                        }
                    }
                }
            }
        }

        System.out.println("No connection found between " + startActor.getName() + " and " + targetActor.getName());
        return Collections.emptyList(); // no connection found
    }

    private List<SeparationListEntry> buildPath(Map<Actor, Predecessor> cameFrom, Actor start, Actor end) {
        List<SeparationListEntry> path = new ArrayList<>();
        Actor current = end;
        // Reconstruct the path by following the Predecessor map.
        while (!current.equals(start)) {
            Predecessor pred = cameFrom.get(current);
            if (pred == null) {
                System.out.println("Error: Predecessor not found for actor " + current.getName());
                break; // Should not happen if a full path exists.
            }
            // Debug: log path construction step
            System.out.println("Path step: " + current.getName() + " via movie " + pred.connectingMovie.getName());
            path.add(current);
            path.add(pred.connectingMovie);
            current = pred.parent;
        }
        path.add(start);
        Collections.reverse(path);
        System.out.println("Final path (start to target):");
        for (SeparationListEntry entry : path) {
            System.out.println("-> " + entry.getText());
        }
        return path;
    }

    public int shortestPathLength(Actor startingActor, Actor targetActor) throws TmdbException {
        List<SeparationListEntry> path = findConnection(startingActor, targetActor);
        if (path.isEmpty()) {
            return Integer.MAX_VALUE; // no path
        }
        // Each movie in the alternating path = one degree of separation
        return (path.size() - 1) / 2; // not sure whether to count the movies in the returned path??
    }
}
