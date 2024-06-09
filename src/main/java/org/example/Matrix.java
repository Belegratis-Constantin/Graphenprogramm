package org.example;

public class Matrix {
    protected int[][] data;
    protected int rows;
    protected int cols;

    public Matrix(int[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    public int getElement(int row, int col) throws MatrixException {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return data[row][col];
        } else {
            throw new MatrixException("Invalid index");
        }
    }

    public void setElement(int row, int col, int value) throws MatrixException {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            data[row][col] = value;
        } else {
            throw new MatrixException("Invalid index");
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Matrix add(Matrix other) throws MatrixException {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new MatrixException("Matrices dimensions must match for addition");
        }

        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(i, j, this.data[i][j] + other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) throws MatrixException {
        if (this.cols != other.rows) {
            throw new MatrixException("Matrices dimensions must match for multiplication");
        }

        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                int sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    sum += this.data[i][k] * other.data[k][j];
                }
                result.setElement(i, j, sum);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
