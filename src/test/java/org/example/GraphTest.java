package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testAddEdge() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,4);

        assertTrue(graph.hasEdge(0,4));
        assertTrue(graph.hasEdge(8,1));
        assertTrue(graph.hasEdge(8,4));
        assertFalse(graph.hasEdge(0,8));
    }

    @Test
    void testFindArticulationPoints3() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,2);

        String expectedAP = "8";
        assertEquals(expectedAP, graph.findArticulationPoints());
    }

    @Test
    void findArticulationPoints26nodes() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedAP = "0; 1; 5; 7; 9; 12; 14; 15; 16; 17; 19; 21";
        assertEquals(expectedAP, graph.findArticulationPoints());

    }

    @Test
    void testArticulationPoints24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedAP = "1; 4; 6; 7; 10; 11; 14; 16; 20; 21";
        assertEquals(expectedAP, graph.findArticulationPoints());
    }

    @Test
    void testFindConnectedComponents() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,2);

        String expectedComponents = "({0, 4}; {1, 2, 8}; {3}; {5}; {6}; {7}; {9})";
        assertEquals(expectedComponents, graph.findConnectedComponents());
    }

    @Test
    void testFindConnectedComponents26nodes() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedComponents = "({0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 17, 20}; {2}; {4}; {15, 16, 18, 22, 25}; {19, 21, 23, 24})";
        assertEquals(expectedComponents, graph.findConnectedComponents());
    }

    @Test
    void testFindConnectedComponents24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedComponents = "({0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23})";
        assertEquals(expectedComponents, graph.findConnectedComponents());
    }

    @Test
    void testFindBridges() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,2);
        graph.addEdge(1,2);

        String expectedBridges = "{[0, 4]}";
        assertEquals(expectedBridges, graph.findBridges());
    }

    @Test
    void testFindBridges26nodes() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedBridges = "{[0, 1], [0, 8], [1, 7], [5, 12], [7, 5], [7, 9], [9, 3], [9, 10], [9, 11], [12, 6], [12, 14], [12, 17], [14, 20], [15, 16], [15, 25], [16, 18], [16, 22], [17, 13], [19, 21], [19, 24], [21, 23]}";
        assertEquals(expectedBridges, graph.findBridges());
    }

    @Test
    void testFindBridges24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedBridges = "{[0, 1], [6, 11], [11, 12], [20, 21]}";
        assertEquals(expectedBridges, graph.findBridges());
    }

    @Test
    void testFindShortestPath() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(0,8);
        graph.addEdge(1,8);
        graph.addEdge(8,2);
        graph.addEdge(1,2);

        List<Integer> expectedRoute = new ArrayList<>();
        expectedRoute.add(0);
        expectedRoute.add(8);
        expectedRoute.add(2);

        assertEquals(expectedRoute, graph.findShortestPath(0,2));
    }

    @Test
    void testShortestPath26nodes_A_O() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        List<Integer> expectedRoute = new ArrayList<>();
        expectedRoute.add(0);
        expectedRoute.add(1);
        expectedRoute.add(7);
        expectedRoute.add(5);
        expectedRoute.add(12);
        expectedRoute.add(14);

        assertEquals(expectedRoute, graph.findShortestPath(0,14));
    }

    @Test
    void testShortestPath26nodes_R_X() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        List<Integer> expectedRoute = new ArrayList<>();

        assertEquals(expectedRoute, graph.findShortestPath(17,23));
    }

    @Test
    void testFindShortestPath24n_01_A_U() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        List<Integer> expectedRoute = new ArrayList<>();
        expectedRoute.add(0);
        expectedRoute.add(1);
        expectedRoute.add(2);
        expectedRoute.add(4);
        expectedRoute.add(7);
        expectedRoute.add(10);
        expectedRoute.add(16);
        expectedRoute.add(15);

        assertEquals(expectedRoute, graph.findShortestPath(0,15));
    }

    @Test
    void testFindShortestPath24n_01_A_S() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        List<Integer> expectedRoute = new ArrayList<>();
        expectedRoute.add(0);
        expectedRoute.add(1);
        expectedRoute.add(2);
        expectedRoute.add(4);
        expectedRoute.add(7);
        expectedRoute.add(10);
        expectedRoute.add(13);
        expectedRoute.add(14);
        expectedRoute.add(18);

        assertEquals(expectedRoute, graph.findShortestPath(0,18));
    }

    @Test
    void testFindShortestPath24n_01_M_R() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        List<Integer> expectedRoute = new ArrayList<>();
        expectedRoute.add(12);
        expectedRoute.add(11);
        expectedRoute.add(6);
        expectedRoute.add(4);
        expectedRoute.add(7);
        expectedRoute.add(10);
        expectedRoute.add(13);
        expectedRoute.add(14);
        expectedRoute.add(17);

        assertEquals(expectedRoute, graph.findShortestPath(12,17));
    }

    @Test
    void testGreedyColoring() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,2);

        int[] expectedColors = new int[graph.adjacencyMatrix.rows];
        expectedColors[0]=0;
        expectedColors[1]=0;
        expectedColors[2]=0;
        expectedColors[3]=0;
        expectedColors[4]=1;
        expectedColors[5]=0;
        expectedColors[6]=0;
        expectedColors[7]=0;
        expectedColors[8]=1;
        expectedColors[9]=0;

        assertEquals(Arrays.toString(expectedColors), Arrays.toString(graph.greedyColoring()));
    }

    @Test
    void testIsBipartiteTrue() {
        Graph graph = new Graph(7);
        graph.addEdge(0,3);
        graph.addEdge(0,4);
        graph.addEdge(0,5);
        graph.addEdge(5,1);
        graph.addEdge(1,6);
        graph.addEdge(2,6);

        assertTrue(graph.isBipartite());
    }

    @Test
    void testIsBipartiteFalse() {
        Graph graph = new Graph(7);
        graph.addEdge(0,3);
        graph.addEdge(0,4);
        graph.addEdge(0,5);
        graph.addEdge(5,1);
        graph.addEdge(1,6);
        graph.addEdge(2,6);
        graph.addEdge(5,6); // makes it not bipartite

        assertFalse(graph.isBipartite());
    }

    @Test
    void testIsBipartite26nodes() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        System.out.println(Arrays.toString(graph.greedyColoring()));
        graph.greedyColoring();
        assertTrue(graph.isBipartite());
    }

    @Test
    void testIsBipartite24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        assertFalse(graph.isBipartite());
    }

    @Test
    void  testIsCompleteGraphFalse() {
        Graph graph = new Graph(10);
        graph.addEdge(0,4);
        graph.addEdge(1,8);
        graph.addEdge(8,2);

        assertFalse(graph.isCompleteGraph());
    }

    @Test
    void testIsCompleteGraphTrue() {
        Graph graph = new Graph(5);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,3);
        graph.addEdge(0,4);
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(2,3);
        graph.addEdge(2,4);
        graph.addEdge(3,4);

        assertTrue(graph.isCompleteGraph());
    }

    @Test
    void isCompleteGraph26nodes() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("graph_26_nodes_21_edges.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        assertFalse(graph.isCompleteGraph());
    }

    @Test
    void isCompleteGraph24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        assertFalse(graph.isCompleteGraph());
    }
}