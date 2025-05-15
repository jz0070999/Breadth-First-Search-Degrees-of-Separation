package com.hollywooddos.jessica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@Deprecated
public class BreadthFirstSearch<T> {

    // pseudo code from algorithms class
    // BSF(G)
    // 1. for each vertex u ∈ G.V
    // 2. mark u as unvisited
    // 3. u.π
    // 4. for each vertex u ∈ G.V
    // 5. if u is unvisited
    // 6. BFS-Visit(G, u)

    // pseudo code from algorithms class
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

    public List<T> bfs(Map<T, List<T>> graph, T start, T target) {
        // 1. for each vertex u ∈ G.V
        // 2. mark u as unvisited
        Set<T> visited = new HashSet<>();
        // 3. u.π
        Map<T, T> parent = new HashMap<>();
        // 4. for each vertex u ∈ G.V
        // initialize queue
        Queue<T> queue = new LinkedList<>();
        // 5. if u is unvisited
        queue.add(start);
        visited.add(start);
        parent.put(start, null); // start has no parent

        while (!queue.isEmpty()) {
            T u = queue.poll();
            // If we have reached the target, we can stop the search.
            if (u.equals(target)) {
                break;
            }
            // For each neighbor v in the adjacency list for u
            List<T> neighbors = graph.get(u);
            if (neighbors != null) {
                for (T v : neighbors) {
                    if (!visited.contains(v)) {
                        visited.add(v);
                        parent.put(v, u);
                        queue.add(v);
                    }
                }
            }
        }

        // If the target was never reached, return an empty list.
        if (!visited.contains(target)) {
            return Collections.emptyList();
        }

        // Step 6: Reconstruct the path by following parentMap from target to start.
        List<T> path = new ArrayList<>();
        for (T current = target; current != null; current = parent.get(current)) {
            path.add(current);
        }
        // Reverse to get the path from start to target.
        Collections.reverse(path);
        return path;
    }

}
