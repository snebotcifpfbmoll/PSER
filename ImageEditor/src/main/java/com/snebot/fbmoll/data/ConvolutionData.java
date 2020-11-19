package com.snebot.fbmoll.data;

public class ConvolutionData {
    public String name = null;
    public int[][] matrix;
    public int k = 1;

    public ConvolutionData() {
    }

    public ConvolutionData(String name, int[][] matrix, int k) {
        this.name = name;
        this.matrix = matrix;
        this.k = k;
    }
}
