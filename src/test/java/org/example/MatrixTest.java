package org.example;

import org.example.Matrix;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {

    @Test
    public void testConstructorAndGetElement() throws MatrixException {
        int[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Matrix matrix = new Matrix(data);
        assertEquals(1, matrix.getElement(0, 0));
        assertEquals(5, matrix.getElement(1, 1));
        assertEquals(9, matrix.getElement(2, 2));
    }

    @Test(expected = MatrixException.class)
    public void testGetElementInvalidIndex() throws MatrixException {
        Matrix matrix = new Matrix(3, 3);
        matrix.getElement(3, 3);
    }

    @Test
    public void testSetElement() throws MatrixException {
        Matrix matrix = new Matrix(3, 3);
        matrix.setElement(0, 0, 10);
        assertEquals(10, matrix.getElement(0, 0));
    }

    @Test(expected = MatrixException.class)
    public void testSetElementInvalidIndex() throws MatrixException {
        Matrix matrix = new Matrix(3, 3);
        matrix.setElement(3, 3, 10);
    }

    @Test
    public void testAdd() throws MatrixException {
        int[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        Matrix matrix1 = new Matrix(data1);
        Matrix matrix2 = new Matrix(data2);
        Matrix result = matrix1.add(matrix2);

        int[][] expectedData = {
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expectedData[i][j], result.getElement(i, j));
            }
        }
    }

    @Test(expected = MatrixException.class)
    public void testAddIncompatibleMatrices() throws MatrixException {
        Matrix matrix1 = new Matrix(2, 3);
        Matrix matrix2 = new Matrix(3, 2);
        matrix1.add(matrix2);
    }

    @Test
    public void testMultiply() throws MatrixException {
        int[][] data1 = {
                {1, 2},
                {3, 4}
        };

        int[][] data2 = {
                {2, 0},
                {1, 2}
        };

        Matrix matrix1 = new Matrix(data1);
        Matrix matrix2 = new Matrix(data2);
        Matrix result = matrix1.multiply(matrix2);

        int[][] expectedData = {
                {4, 4},
                {10, 8}
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedData[i][j], result.getElement(i, j));
            }
        }
    }

    @Test(expected = MatrixException.class)
    public void testMultiplyIncompatibleMatrices() throws MatrixException {
        Matrix matrix1 = new Matrix(2, 3);
        Matrix matrix2 = new Matrix(2, 3);
        matrix1.multiply(matrix2);
    }
}
