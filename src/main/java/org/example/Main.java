package org.example;

public class Main {
    public static void main(String[] args) {
        int[][] data1 = {
                {0, 1, 1, 1, 0},
                {1, 0, 0, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0}
        };

        int[][] data2 = {
                {0, 1, 1, 1, 0},
                {1, 0, 0, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0}
        };

        Matrix matrix = new Matrix(data1);
        System.out.println(matrix);

        Matrix matrix2 = new Matrix(data2);
        System.out.println(matrix2);

        Matrix sumMatrix = matrix.add(matrix2);
        System.out.println("Sum of matrices:");
        System.out.println(sumMatrix);

        Matrix multiplyMatrix = matrix.multiply(matrix2);
        System.out.println("Product of matrices:");
        System.out.println(multiplyMatrix);
    }
}