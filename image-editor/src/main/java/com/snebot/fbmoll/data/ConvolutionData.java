package com.snebot.fbmoll.data;

public class ConvolutionData {
    private final int MATRIX_SIZE = 3;
    private String name;
    private int[][] matrix;
    private int k = 1;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        if (k == 0) k = calculateK();
        this.k = k;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConvolutionData(String name, int[][] matrix, int k) {
        this.name = name;
        this.matrix = matrix;
        this.k = k;
    }

    public int calculateK() {
        int sum = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                sum += matrix[i][j];
            }
        }
        sum /= MATRIX_SIZE * MATRIX_SIZE;
        if (sum == 0) sum = 1;
        return sum;
    }
}
