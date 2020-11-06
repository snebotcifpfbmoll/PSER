package com.serafinebot.pser.repaso.layouttest;

import javax.swing.*;
import java.awt.*;

public class LayoutTest {
    public static void addComponents(Container pane) {
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.ipady = 20;

        JButton button = new JButton("Button 1");
        constraints.gridx = 0;
        constraints.gridy = 0;
        pane.add(button, constraints);

        button = new JButton("Button 2");
        constraints.gridx = 1;
        constraints.gridy = 0;
        pane.add(button, constraints);

        button = new JButton("Button 3");
        constraints.gridx = 2;
        constraints.gridy = 0;
        pane.add(button, constraints);

        button = new JButton("Long button 4");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 80;
        constraints.weightx = 0.0;
        constraints.gridwidth = 3;
        pane.add(button, constraints);

        button = new JButton("Button 5");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = 0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridwidth = 1;
        pane.add(button, constraints);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }
}
