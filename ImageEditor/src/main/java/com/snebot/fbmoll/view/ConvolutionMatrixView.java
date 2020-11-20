package com.snebot.fbmoll.view;

import javax.swing.*;
import java.awt.*;

public class ConvolutionMatrixView extends Container {
    private int width = 0;
    private int height = 0;
    private final int MATRIX_SIZE = 3;
    private int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];

    // UI
    private final JTextField[] textFields = new JTextField[MATRIX_SIZE * MATRIX_SIZE];

    public void setMatrix(int[][] matrix) {
        if (matrix == null || matrix.length != MATRIX_SIZE || matrix[0].length != MATRIX_SIZE) return;
        this.matrix = matrix;
        updateMatrix();
    }

    public ConvolutionMatrixView(int width, int heigth) {
        this.width = width;
        this.height = heigth;
        setup();
    }

    public ConvolutionMatrixView(int width, int heigth, int[][] matrix) {
        this.width = width;
        this.height = heigth;
        this.matrix = matrix;
        setup();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                JTextField textField = new JTextField();
                textField.setHorizontalAlignment(JTextField.CENTER);
                constraints.gridx = j;
                constraints.gridy = i;
                add(textField, constraints);
                textFields[i * MATRIX_SIZE + j] = textField;
            }
        }

        updateMatrix();
    }

    private void updateMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                JTextField textField = textFields[i * MATRIX_SIZE + j];
                if (textField == null) continue;
                textField.setText(String.valueOf(matrix[i][j]));
            }
        }
    }

    public int[][] getMatrix() {
        int[][] mat = new int[MATRIX_SIZE][MATRIX_SIZE];
        try {
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    JTextField textField = textFields[i * MATRIX_SIZE + j];
                    if (textField == null) continue;
                    mat[i][j] = Integer.parseInt(textField.getText());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return mat;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
