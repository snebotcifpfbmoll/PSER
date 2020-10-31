package com.serafinebot.pser.repaso.image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FireAnimation extends JComponent implements ActionListener {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int IMAGE_WIDTH = WINDOW_WIDTH / 4;
    private static final int IMAGE_HEIGHT = WINDOW_HEIGHT / 4;

    private Timer timer = new Timer(200, this);

    public static void main(String[] args) {
        JFrame window = new JFrame("Test");
        window.add(new FireAnimation());
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image fire = FireTest.createFireImage(IMAGE_WIDTH, IMAGE_HEIGHT).getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT);
        g.drawImage(fire, 0, 0, null);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
