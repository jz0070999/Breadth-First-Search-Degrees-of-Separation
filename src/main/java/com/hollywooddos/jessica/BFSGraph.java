package com.hollywooddos.jessica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// for the actorsToMoviesAdjList the keys are the Actor objects which are our vertices 
// The list or values each of those actors have are the movies and those represent our edges 

// for the moviesToActorsAdjList the keys are the Movie objects which are our vertices
// The list or values each of those movies have are the actors and those represent our edges
// This is a bipartite graph where the two sets of vertices are actors and movies
// The edges are the connections between actors and movies?? 

@Deprecated
public class BFSGraph {
    // Adjacency list for bipartite graph:
    // One map for actors → movies
    private Map<Actor, List<Movie>> actorToMoviesAdjList = new HashMap<>();
    private Map<Movie, List<Actor>> moveieToActorsAdjList = new HashMap<>();

    public void addActorMovieConnection(Actor actor, Movie movie) {
        actorToMoviesAdjList.computeIfAbsent(actor, k -> new ArrayList<>()).add(movie);
        moveieToActorsAdjList.computeIfAbsent(movie, k -> new ArrayList<>()).add(actor);
    }

    public List<Movie> getMoviesForActor(Actor actor) {
        return actorToMoviesAdjList.getOrDefault(actor, Collections.emptyList());
    }

    public List<Actor> getActorsForMovie(Movie movie) {
        return moveieToActorsAdjList.getOrDefault(movie, Collections.emptyList());
    }

    public List<Actor> getAllActors() {
        return new ArrayList<>(actorToMoviesAdjList.keySet());
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(moveieToActorsAdjList.keySet());
    }

    public void clear() {
        actorToMoviesAdjList.clear();
        moveieToActorsAdjList.clear();
    }

    public boolean isEmpty() {
        return actorToMoviesAdjList.isEmpty() && moveieToActorsAdjList.isEmpty();
    }

}
