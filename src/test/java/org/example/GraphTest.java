package org.example;

import org.junit.jupiter.api.Test;

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
    void testArticulationPoints24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedAP = "11; 6; 4; 21; 20; 16; 14; 10; 7; 4; 1";
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
    void testFindBridges24n_01() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

        String expectedBridges = "{[0, 1], [6, 11], [11, 12], [20, 21]}";
        assertEquals(expectedBridges, graph.findBridges());
    }
}