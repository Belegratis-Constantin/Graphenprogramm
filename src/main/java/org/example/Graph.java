package org.example;

import java.util.*;

public class Graph {
    private int V; // Number of vertices
    private List<List<Integer>> adj; // Adjacency list
    private boolean[] visited;
    private int[] parent;
    private int[] discTime; // Discovery time of each vertex
    private int[] lowTime; // Low time of each vertex
    private ArrayList<Integer> articulationPoints;
    private AdjacencyMatrix adjacencyMatrix; // Adjacency matrix

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        adjacencyMatrix = new AdjacencyMatrix(V);
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
        adjacencyMatrix.addEdge(u, v);
    }

    // Method to find articulation points
    public List<Integer> findArticulationPoints() {
        visited = new boolean[V];
        parent = new int[V];
        discTime = new int[V];
        lowTime = new int[V];
        articulationPoints = new ArrayList<>();

        // Initialize parent and visited arrays
        Arrays.fill(parent, -1);
        Arrays.fill(visited, false);

        // Perform DFS traversal to find articulation points
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }

        return articulationPoints;
    }

    private void dfs(int u) {
        int time = 0; // Initialize time variable for this DFS traversal
        visited[u] = true;
        discTime[u] = lowTime[u] = ++time;
        int children = 0;

        for (int v : adj.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfs(v);

                // Check if the subtree rooted with v has a connection to an ancestor of u
                lowTime[u] = Math.min(lowTime[u], lowTime[v]);

                // Check if u is an articulation point
                if (parent[u] == -1 && children > 1) {
                    articulationPoints.add(u);
                }
                if (parent[u] != -1 && lowTime[v] >= discTime[u]) {
                    articulationPoints.add(u);
                }
            } else if (v != parent[u]) {
                // Update low time of u for back edges
                lowTime[u] = Math.min(lowTime[u], discTime[v]);
            }
        }
    }

    // Method to find connected components
    public List<List<Integer>> findConnectedComponents() {
        // TODO
        return null;
    }

    // Method to find bridges
    public List<List<Integer>> findBridges() {
        //TODO
        return null;
    }

    // Method to display the adjacency matrix
    public void displayAdjacencyMatrix() {
        System.out.println(adjacencyMatrix.toString());
    }
}

