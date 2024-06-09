package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Graph {
    private final int V;
    private boolean[] visited;
    private int[] parent;
    private int[] discTime; // Discovery Time
    private int[] lowTime; // Lowest discovery time possible
    private int time;
    private List<Integer> articulationPoints;
    private List<int[]> bridges;
    public final AdjacencyMatrix adjacencyMatrix;

    public Graph(int V) {
        this.V = V;
        adjacencyMatrix = new AdjacencyMatrix(V);
    }

    public void addEdge(int u, int v) {
        adjacencyMatrix.addEdge(u, v);
        adjacencyMatrix.addEdge(v, u);
    }

    public boolean hasEdge(int u, int v) {
        return adjacencyMatrix.hasEdge(u, v);
    }

    public String findArticulationPoints() {
        initializeDFS();
        articulationPoints = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, true, false);
            }
        }

        articulationPoints.sort(Comparator.comparingInt(e -> e));

        StringBuilder result = new StringBuilder();
        for (Integer articulationPoint : articulationPoints) {
            result.append(articulationPoint);
            result.append("; ");
        }
        result.delete(result.length()-2, result.length());
        return result.toString();
    }

    public String findBridges() {
        initializeDFS();
        bridges = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, false, true);
            }
        }

        bridges.sort(Comparator.comparingInt(e -> e[0]));

        // Format bridges as String
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (int[] bridge : bridges) {
            result.append(Arrays.toString(bridge));
            result.append(", ");
        }
        result.delete(result.length()-2, result.length());
        result.append("}");
        return result.toString();
    }

    public String findConnectedComponents() {
        visited = new boolean[V];
        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsConnectedComponents(i, component);
                component.sort(Comparator.comparingInt(e -> e));
                components.add(component);
            }
        }

        components.sort(Comparator.comparingInt(e -> e.get(0)));

        // Format components as String
        StringBuilder result = getResult(components);

        return result.toString();
    }

    private void initializeDFS() {
        visited = new boolean[V];
        parent = new int[V];
        discTime = new int[V];
        lowTime = new int[V];
        time = 0;

        Arrays.fill(parent, -1);
        Arrays.fill(visited, false);
    }

    private void dfs(int u, boolean findArticulationPoints, boolean findBridges) {
        visited[u] = true;
        discTime[u] = ++time; // Discovery time
        lowTime[u] = ++time; // Lowest discovery time possible
        int children = 0;

        for (int v = 0; v < V; v++) { // Iterate through all possible vertices
            if (adjacencyMatrix.getElement(u, v) == 1) { // Check if there is an edge between u and v
                if (!visited[v]) {
                    children++;
                    parent[v] = u;
                    dfs(v, findArticulationPoints, findBridges); // Going deeper from u to v

                    // Check if the subtree rooted with v has a connection to an ancestor of u
                    lowTime[u] = Math.min(lowTime[u], lowTime[v]);

                    // Check if u is an articulation point
                    if (findArticulationPoints) {
                        if ((parent[u] == -1 && children > 1) || (parent[u] != -1 && lowTime[v] >= discTime[u])) {
                            if (!articulationPoints.contains(u)) {
                                articulationPoints.add(u);
                            }
                        }
                    }

                    // Check for bridge
                    if (findBridges && lowTime[v] > discTime[u]) {
                        bridges.add(new int[]{u, v});
                    }
                    
                } else if (v != parent[u]) {
                    // Update low time of u for back edges
                    lowTime[u] = Math.min(lowTime[u], discTime[v]);
                }
            }
        }
    }

    private void dfsConnectedComponents(int u, List<Integer> component) {
        visited[u] = true;
        component.add(u);

        for (int v = 0; v < V; v++) {
            if (adjacencyMatrix.getElement(u, v) == 1 && !visited[v]) {
                dfsConnectedComponents(v, component);
            }
        }
    }

    public int getAdjacencyMatrixElement(int u, int v) {
        return adjacencyMatrix.data[u][v];
    }

    public Graph importAdjacencyMatrix(AdjacencyMatrix matrix) {
        Graph graph = new Graph(matrix.rows);
        for (int i=0; i< matrix.rows; i++) {
            for (int j=0; j< matrix.cols; j++) {
                if (matrix.hasEdge(i,j)) {
                    graph.addEdge(i,j);
                }
            }
        }

        return graph;
    }

    public List<Integer> findShortestPath(int src, int dest) {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(parent, -1);
        visited[src] = true;
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            // if destination was found recreate the path
            if (u == dest) {
                List<Integer> path = new ArrayList<>();
                for (int at = dest; at != -1; at = parent[at]) {
                    path.add(at);
                }
                Collections.reverse(path);
                return path;
            }

            // visit all neighbours
            for (int v = 0; v < V; v++) {
                if (adjacencyMatrix.getElement(u, v) == 1 && !visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        return new ArrayList<>(); // no path found
    }

    public int[] greedyColoring() {
        int[] result = new int[V];
        Arrays.fill(result, -1); // -1 = no color

        boolean[] colorAvailable = new boolean[V];
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        for (int startNode=0; startNode<V; startNode++) {
            if (!visited[startNode]) {
                queue.add(startNode);
                result[startNode] = 0;
                visited[startNode] = true;

                while (!queue.isEmpty()) {
                    int node = queue.poll();

                    Arrays.fill(colorAvailable, true);
                    for (int neighbor=0; neighbor<V; neighbor++) {
                        if (adjacencyMatrix.getElement(node, neighbor) == 1 && result[neighbor] != -1) {
                            colorAvailable[result[neighbor]] = false;
                        }
                    }

                    for (int color=0; color<V; color++) {
                        if (colorAvailable[color]) {
                            result[node] = color;
                            break;
                        }
                    }

                    for (int neighbor=0; neighbor<V; neighbor++) {
                        if (adjacencyMatrix.getElement(node, neighbor) == 1 && !visited[neighbor]) {
                            queue.add(neighbor);
                            visited[neighbor] = true;
                        }
                    }
                }
            }
        }
        return result;
    }


    public boolean isBipartite() {
        return Arrays.stream(greedyColoring()).noneMatch(e -> e==2);
    }

    public boolean isCompleteGraph() {
        for (int i=0; i<V; i++) {
            for (int j=0; j<V; j++) {
                if(!hasEdge(i,j) && i!=j) {
                    return false;
                }
            }
        }
        return true;
    }

    private StringBuilder getResult(List<List<Integer>> components) {
        StringBuilder result = new StringBuilder();
        result.append("(");
        for (List<Integer> component : components) {
            result.append("{");
            for (int i = 0; i < component.size(); i++) {
                result.append(component.get(i));
                if (i < component.size() - 1) {
                    result.append(", ");
                }
            }
            result.append("}; ");
        }
        result.delete(result.length()-2, result.length());
        result.append(")");
        return result;
    }
}
