package com.snebot.fbmoll;

import javax.swing.*;
import java.awt.*;

public class Restaurant extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private Thread viewThread = null;

    private Restaurant() {
    }

    public Restaurant(RestaurantView restaurantView) {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(restaurantView, constraints);

        viewThread = new Thread(restaurantView);
        viewThread.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        RestaurantView restaurantView = new RestaurantView(WINDOW_WIDTH, WINDOW_HEIGHT);
        restaurantView.add(3, 5, 10);
        Restaurant restaurant = new Restaurant(restaurantView);
        restaurant.pack();
        restaurant.setVisible(true);
    }
}