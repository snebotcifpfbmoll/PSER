package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.FlameData;

import javax.swing.*;
import java.awt.*;

public class FireMultipliersView extends Container {
    private static final int MULT_WIDTH = 3;
    private static final int MULT_HEIGHT = 2;
    private static final String DEFAULT_TEXT = "0.0";
    private final JTextField[] textFields = new JTextField[MULT_WIDTH * MULT_HEIGHT];

    public FireMultipliersView() {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        for (int i = 0; i < MULT_HEIGHT; i++) {
            for (int j = 0; j < MULT_WIDTH; j++) {
                JTextField textField = new JTextField(DEFAULT_TEXT);
                textField.setHorizontalAlignment(JTextField.CENTER);
                constraints.gridx = j;
                constraints.gridy = i;
                add(textField, constraints);
                textFields[i * MULT_WIDTH + j] = textField;
            }
        }
    }

    public void setData(FlameData data) {
        try {
            int index = 0;
            textFields[index++].setText(String.valueOf(data.mult_left));
            textFields[index++].setText(String.valueOf(data.mult));
            textFields[index++].setText(String.valueOf(data.mult_right));
            textFields[index++].setText(String.valueOf(data.mult_bottom_left));
            textFields[index++].setText(String.valueOf(data.mult_bottom));
            textFields[index].setText(String.valueOf(data.mult_bottom_right));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public FlameData getData() {
        FlameData data = new FlameData();
        try {
            int index = 0;
            data.mult_left = Double.parseDouble(textFields[index++].getText());
            data.mult = Double.parseDouble(textFields[index++].getText());
            data.mult_right = Double.parseDouble(textFields[index++].getText());
            data.mult_bottom_left = Double.parseDouble(textFields[index++].getText());
            data.mult_bottom = Double.parseDouble(textFields[index++].getText());
            data.mult_bottom_right = Double.parseDouble(textFields[index].getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
