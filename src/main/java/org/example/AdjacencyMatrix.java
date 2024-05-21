package org.example;

public class AdjacencyMatrix extends Matrix {

    public AdjacencyMatrix(int node) {
        super(node, node);
    }

    public void addEdge(int from, int to) {
        if (from >= 0 && from < rows && to >= 0 && to < cols) {
            setElement(from, to, 1);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void removeEdge(int from, int to) {
        if (from >= 0 && from < rows && to >= 0 && to < cols) {
            setElement(from, to, 0);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public boolean hasEdge(int from, int to) {
        if (from >= 0 && from < rows && to >= 0 && to < cols) {
            return getElement(from, to) != 0;
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public Matrix power(int exponent) {
        if (this.rows != this.cols) {
            throw new IllegalArgumentException("Matrix must be square for exponentiation.");
        }
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent isn't allowed to be negative.");
        }

        Matrix result = new Matrix(this.data);
        Matrix base = new Matrix(this.data);

        while (exponent > 1) {
            result = result.multiply(base);
            exponent--;
        }
        return result;
    }

    public int[][] calculateDistances() {
        int[][] distances = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                distances[i][j] = (i == j) ? 0 : (data[i][j] == 0 ? Integer.MAX_VALUE : data[i][j]);
            }
        }

        for (int k = 0; k < rows; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE &&
                            distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    }
                }
            }
        }

        return distances;
    }

    public int calculateRadius() {
        int[][] distances = calculateDistances();
        int radius = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            int maxDistance = 0;
            for (int j = 0; j < cols; j++) {
                maxDistance = Math.max(maxDistance, distances[i][j]);
            }
            radius = Math.min(radius, maxDistance);
        }
        return radius == Integer.MAX_VALUE ? -1 : radius;
    }

    public int calculateDiameter() {
        int[][] distances = calculateDistances();
        int diameter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (distances[i][j] != Integer.MAX_VALUE) {
                    diameter = Math.max(diameter, distances[i][j]);
                } else {
                    return -1;
                }
            }
        }
        return diameter;
    }
}
