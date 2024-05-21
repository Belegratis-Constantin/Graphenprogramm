package org.example;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class AdjacencyMatrixTest {
    @Test
    public void testAddEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(0, 2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddEdgeInvalidIndex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(3, 0);  // This should throw an exception
    }

    @Test
    public void testRemoveEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveEdgeInvalidIndex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.removeEdge(3, 0);  // This should throw an exception
    }

    @Test
    public void testHasEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        graph.addEdge(1, 0);
        assertTrue(graph.hasEdge(1, 0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHasEdgeInvalidIndex() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.hasEdge(3, 0);  // This should throw an exception
    }

    @Test
    public void testToString() {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expectedString = "0 1 0 \n0 0 1 \n0 0 0 \n";
        assertEquals(expectedString, graph.toString());
    }
}