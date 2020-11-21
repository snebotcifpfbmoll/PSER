package com.snebot.fbmoll.view;

import javax.swing.*;
import java.awt.*;

public class FireMultipliersView extends Container {
    private static final int MULT_WIDTH = 3;
    private static final int MULT_HEIGHT = 2;
    private static final String DEFAULT_TEXT = "0.0";
    private JTextField[] textFields = new JTextField[MULT_WIDTH * MULT_HEIGHT];

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
}
