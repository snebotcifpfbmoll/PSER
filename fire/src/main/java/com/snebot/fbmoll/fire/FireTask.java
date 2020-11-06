package com.snebot.fbmoll.fire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FireTask extends JFrame implements ActionListener {
    private final int WINDOW_WIDTH = 458;
    private final int WINDOW_HEIGHT = WINDOW_WIDTH; // / 36 * 6;
    private FlameView flameView;

    private FireTask() {}

    public FireTask(String title) {
        super(title);
        setup();
    }

    public void setup() {
        if (flameView == null) {
            Container pane = getContentPane();
            pane.setLayout(new GridBagLayout());
            pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            flameView = new FlameView(WINDOW_WIDTH, WINDOW_HEIGHT, 20);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 0;

            pane.add(flameView, constraints);

            pack();
            setVisible(true);

            flameView.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FireTask fireTask = new FireTask("Fire task");
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed");
    }
}
