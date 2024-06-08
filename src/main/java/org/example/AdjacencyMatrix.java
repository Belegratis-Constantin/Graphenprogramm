package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public Matrix calculateDistances() {
        Matrix distances = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) {
                    distances.setElement(i, j, 0);
                } else if (this.getElement(i, j) != 0) {
                    distances.setElement(i, j, this.getElement(i, j));
                } else {
                    distances.setElement(i, j, Integer.MAX_VALUE);
                }
            }
        }

        for (int k = 0; k < rows; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (distances.getElement(i, k) != Integer.MAX_VALUE && distances.getElement(k, j) != Integer.MAX_VALUE &&
                            distances.getElement(i, k) + distances.getElement(k, j) < distances.getElement(i, j)) {
                        distances.setElement(i, j, distances.getElement(i, k) + distances.getElement(k, j));
                    }
                }
            }
        }

        return distances;
    }

    public int calculateRadius() {
        Matrix distances = calculateDistances();
        int radius = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            int maxDistance = 0;
            for (int j = 0; j < cols; j++) {
                maxDistance = Math.max(maxDistance, distances.getElement(i,j));
            }
            radius = Math.min(radius, maxDistance);
        }
        return radius == Integer.MAX_VALUE ? -1 : radius;
    }

    public int calculateDiameter() {
        Matrix distances = calculateDistances();
        int diameter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (distances.getElement(i,j) != Integer.MAX_VALUE) {
                    diameter = Math.max(diameter, distances.getElement(i,j));
                } else {
                    return -1;
                }
            }
        }
        return diameter;
    }

    public void exportMatrix (String filename) throws MatrixException {
        if (filename == null || !filename.endsWith(".csv")) {
            throw new MatrixException("Fehler: Dateiname ist 'null' oder ungültig");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            String separator = System.getProperty("path.separator");
            for (int i = 0; i < rows; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < cols; j++) {
                    line.append(data[i][j]).append(separator);
                }
                line.delete(line.length()-1, line.length());
                bw.write(line.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AdjacencyMatrix importMatrix(String filename) throws MatrixException {
        if (filename == null || !filename.endsWith(".csv")) {
            throw new MatrixException("Fehler: Dateiname  ist 'null' oder ungültig");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                rowCount++;
            }

            AdjacencyMatrix importedMatrix = new AdjacencyMatrix(rowCount);

            try (BufferedReader br2 = new BufferedReader(new FileReader(filename))) {
                int row = 0;
                while ((line = br2.readLine()) != null) {
                    String[] values = line.split(";");
                    for (int col = 0; col < values.length; col++) {
                        int value = Integer.parseInt(values[col]);
                        importedMatrix.setElement(row, col, value);
                    }
                    row++;
                }
            }
            return importedMatrix;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] calculateEccentricities() {
        Matrix distances = calculateDistances();
        int[] eccentricities = new int[rows];

        for (int i = 0; i < rows; i++) {
            int maxDistance = 0;
            for (int j = 0; j < cols; j++) {
                if (distances.getElement(i,j) != Integer.MAX_VALUE) {
                    maxDistance = Math.max(maxDistance, distances.getElement(i,j));
                }
            }
            eccentricities[i] = maxDistance;
        }

        return eccentricities;
    }

    public List<Integer> calculateCenter() {
        int[] eccentricities = calculateEccentricities();
        int minEccentricity = Integer.MAX_VALUE;

        for (int e : eccentricities) {
            minEccentricity = Math.min(minEccentricity, e);
        }

        List<Integer> centerNodes = new ArrayList<>();
        for (int i = 0; i < eccentricities.length; i++) {
            if (eccentricities[i] == minEccentricity) {
                centerNodes.add(i);
            }
        }

        return centerNodes;
    }
}