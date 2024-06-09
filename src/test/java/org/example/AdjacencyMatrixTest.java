package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class AdjacencyMatrixTest {
    @Test
    public void testAddEdge() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(0, 2));
    }

    @Test(expected = MatrixException.class)
    public void testAddEdgeInvalidIndex() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(3, 0);
    }

    @Test
    public void testRemoveEdge() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }

    @Test(expected = MatrixException.class)
    public void testRemoveEdgeInvalidIndex() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.removeEdge(3, 0);
    }

    @Test
    public void testHasEdge() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        graph.addEdge(1, 0);
        assertTrue(graph.hasEdge(1, 0));
    }

    @Test(expected = MatrixException.class)
    public void testHasEdgeInvalidIndex() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.hasEdge(3, 0);
    }

    @Test
    public void testToString() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expectedString = "0 1 0 \n0 0 1 \n0 0 0 \n";
        assertEquals(expectedString, graph.toString());
    }

    @Test
    public void testExportMatrix() throws MatrixException {
        AdjacencyMatrix graph = new AdjacencyMatrix(10);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(4,6);
        graph.addEdge(7,9);
        graph.addEdge(2,5);
        graph.addEdge(3,8);
        graph.exportMatrix("matrix.csv");
    }

    @Test
    public void testImportMatrix() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("testMatrix.csv");
        AdjacencyMatrix expectedMatrix = new AdjacencyMatrix(10);
        expectedMatrix.addEdge(0,1);
        expectedMatrix.addEdge(1,2);
        expectedMatrix.addEdge(2,5);
        expectedMatrix.addEdge(3,8);
        expectedMatrix.addEdge(4,6);
        expectedMatrix.addEdge(7,9);
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                assertEquals(expectedMatrix.hasEdge(i,j),matrix.hasEdge(i,j));
            }
        }
    }

    @Test
    public void testImport24n_01Distances() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        int[][] expectedDistancesData = {
                {0, 1, 2, 2, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 6, 8, 8, 7, 7, 8, 9, 9},
                {1, 0, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 5, 7, 7, 6, 6, 7, 8, 8},
                {2, 1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                {2, 1, 1, 0, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                {3, 2, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 3, 5, 5, 4, 4, 5, 6, 6},
                {4, 3, 2, 2, 1, 0, 1, 2, 2, 3, 3, 2, 3, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                {4, 3, 2, 2, 1, 1, 0, 2, 2, 3, 3, 1, 2, 4, 5, 5, 4, 6, 6, 5, 5, 6, 7, 7},
                {4, 3, 2, 2, 1, 2, 2, 0, 1, 1, 1, 3, 4, 2, 3, 3, 2, 4, 4, 3, 3, 4, 5, 5},
                {4, 3, 2, 2, 1, 2, 2, 1, 0, 2, 2, 3, 4, 3, 4, 4, 3, 5, 5, 4, 4, 5, 6, 6},
                {5, 4, 3, 3, 2, 3, 3, 1, 2, 0, 1, 4, 5, 2, 3, 3, 2, 4, 4, 3, 3, 4, 5, 5},
                {5, 4, 3, 3, 2, 3, 3, 1, 2, 1, 0, 4, 5, 1, 2, 2, 1, 3, 3, 2, 2, 3, 4, 4},
                {5, 4, 3, 3, 2, 2, 1, 3, 3, 4, 4, 0, 1, 5, 6, 6, 5, 7, 7, 6, 6, 7, 8, 8},
                {6, 5, 4, 4, 3, 3, 2, 4, 4, 5, 5, 1, 0, 6, 7, 7, 6, 8, 8, 7, 7, 8, 9, 9},
                {6, 5, 4, 4, 3, 4, 4, 2, 3, 2, 1, 5, 6, 0, 1, 2, 2, 2, 2, 3, 3, 4, 5, 5},
                {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 1, 0, 1, 2, 1, 1, 3, 3, 4, 5, 5},
                {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 2, 1, 0, 1, 2, 2, 2, 2, 3, 4, 4},
                {6, 5, 4, 4, 3, 4, 4, 2, 3, 2, 1, 5, 6, 2, 2, 1, 0, 3, 3, 1, 1, 2, 3, 3},
                {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 2, 1, 2, 3, 0, 1, 4, 4, 5, 6, 6},
                {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 2, 1, 2, 3, 1, 0, 4, 4, 5, 6, 6},
                {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 3, 3, 2, 1, 4, 4, 0, 1, 2, 3, 3},
                {7, 6, 5, 5, 4, 5, 5, 3, 4, 3, 2, 6, 7, 3, 3, 2, 1, 4, 4, 1, 0, 1, 2, 2},
                {8, 7, 6, 6, 5, 6, 6, 4, 5, 4, 3, 7, 8, 4, 4, 3, 2, 5, 5, 2, 1, 0, 1, 1},
                {9, 8, 7, 7, 6, 7, 7, 5, 6, 5, 4, 8, 9, 5, 5, 4, 3, 6, 6, 3, 2, 1, 0, 1},
                {9, 8, 7, 7, 6, 7, 7, 5, 6, 5, 4, 8, 9, 5, 5, 4, 3, 6, 6, 3, 2, 1, 1, 0}
        };

        Matrix expectedDistances = new Matrix(expectedDistancesData);

        Matrix distances = matrix.calculateDistances();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                assertEquals(expectedDistances.getElement(i,j), distances.getElement(i,j));
            }
        }
    }

    @Test
    public void test24n_01Radius() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        assertEquals(5, matrix.calculateRadius());
    }

    @Test
    public void test24n_01Diameter() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        assertEquals(9, matrix.calculateDiameter());
    }

    @Test
    public void test24n_01CalculateCenter() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        List<Integer> expectedCenter = Arrays.asList(7, 9, 10);

        assertEquals(expectedCenter, matrix.calculateCenter());
    }

    @Test
    public void test24n_01Eccentricities() throws MatrixException {
        AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix("24n_01.csv");
        int[] eccentricities = matrix.calculateEccentricities();
        int[] expectedEccentricities = {9, 8, 7, 7, 6, 7, 7, 5, 6, 5, 5, 8, 9, 6, 7, 7, 6, 8, 8, 7, 7, 8, 9, 9};

        assertArrayEquals(expectedEccentricities, eccentricities);
    }
}