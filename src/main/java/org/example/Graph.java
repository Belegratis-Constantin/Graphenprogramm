package org.example;

import java.util.*;

public class Graph {
    public final int V;
    private boolean[] visited;
    private int[] parent;
    private int[] discTime; // Discovery Time
    private int[] lowTime; // Lowest discovery time possible
    private int time;
    private List<Integer> articulationPoints;
    private List<int[]> bridges;
    List<List<Integer>> components;
    public final AdjacencyMatrix adjacencyMatrix;

    public Graph(int V) {
        this.V = V;
        adjacencyMatrix = new AdjacencyMatrix(V);
    }

    public void addEdge(int u, int v) throws MatrixException {
        adjacencyMatrix.addEdge(u, v);
        adjacencyMatrix.addEdge(v, u); // because it is an undirected Graph
    }

    public boolean hasEdge(int u, int v) throws MatrixException {
        return adjacencyMatrix.hasEdge(u, v);
    }

    public String findArticulationPoints() throws MatrixException {
        initializeDFS();
        articulationPoints = new ArrayList<>();

        for (int i=0; i<V; i++) {
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

    public void findBridges() throws MatrixException {
        initializeDFS();
        bridges = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, false, true);
            }
        }

        bridges.sort(Comparator.comparingInt(e -> e[0]));
    }

    public String printBridges() throws MatrixException {
        findBridges();
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

    public void findConnectedComponents() throws MatrixException {
        visited = new boolean[V];
        Arrays.fill(visited, false);
        components = new ArrayList<>();

        for (int i=0; i<V; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfsConnectedComponents(i, component);
                component.sort(Comparator.comparingInt(e -> e));
                components.add(component);
            }
        }

        components.sort(Comparator.comparingInt(e -> e.get(0)));
    }

    public String printConnectedComponents() throws MatrixException {
        findConnectedComponents();
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
        return result.toString();
    }

    public Graph importAdjacencyMatrix(AdjacencyMatrix matrix) throws MatrixException {
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

    public List<Integer> findShortestPath(int src, int dest) throws MatrixException {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(parent, -1);
        visited[src] = true;
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == dest) {
                List<Integer> path = new ArrayList<>();
                for (int at = dest; at != -1; at = parent[at]) {
                    path.add(at);
                }
                Collections.reverse(path);
                return path;
            }

            for (int neighbors=0; neighbors<V; neighbors++) {
                if (adjacencyMatrix.getElement(u, neighbors) == 1 && !visited[neighbors]) {
                    visited[neighbors] = true;
                    parent[neighbors] = u;
                    queue.add(neighbors);
                }
            }
        }

        return new ArrayList<>(); // no path found
    }

    public int[] greedyColoring() throws MatrixException {
        int[] result = new int[V];
        boolean[] colorAvailable = new boolean[V];
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(result, -1); // -1 = no color

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


    public boolean isBipartite() throws MatrixException {
        return Arrays.stream(greedyColoring()).noneMatch(e -> e==2);
    }

    public boolean isCompleteGraph() throws MatrixException {
        for (int i=0; i<V; i++) {
            for (int j=0; j<V; j++) {
                if(!hasEdge(i,j) && i!=j) {
                    return false;
                }
            }
        }
        return true;
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

    private void dfs(int u, boolean findArticulationPoints, boolean findBridges) throws MatrixException {
        visited[u] = true;
        discTime[u] = ++time; // Discovery time
        lowTime[u] = ++time; // Lowest discovery time possible
        int children = 0;

        for (int v = 0; v < V; v++) {
            if (adjacencyMatrix.getElement(u, v) == 1) {
                if (!visited[v]) {
                    children++;
                    parent[v] = u;
                    dfs(v, findArticulationPoints, findBridges); // Going deeper from u to v

                    // Check if the subtree rooted with v has a connection to an ancestor of u
                    lowTime[u] = Math.min(lowTime[u], lowTime[v]);

                    if (findArticulationPoints) {
                        if ((parent[u] == -1 && children > 1) || (parent[u] != -1 && lowTime[v] >= discTime[u])) {
                            if (!articulationPoints.contains(u)) {
                                articulationPoints.add(u);
                            }
                        }
                    }

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

    private void dfsConnectedComponents(int u, List<Integer> component) throws MatrixException {
        visited[u] = true;
        component.add(u);

        for (int v=0; v<V; v++) {
            if (adjacencyMatrix.getElement(u, v) == 1 && !visited[v]) {
                dfsConnectedComponents(v, component);
            }
        }
    }
}
