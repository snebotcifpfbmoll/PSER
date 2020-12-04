package com.snebot.fbmoll;

import com.snebot.fbmoll.object.Consumer;
import com.snebot.fbmoll.object.Cook;
import com.snebot.fbmoll.object.Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurant extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;

    private final List<Table> tables = new ArrayList<>();
    private final List<Cook> cooks = new ArrayList<>();
    private final List<Consumer> consumers = new ArrayList<>();

    private static final Color[] colors = new Color[]{Color.BLUE, Color.GREEN, Color.CYAN};

    public void addTable(int count) {
        for (int i = 0; i < count; i++) tables.add(new Table(i));
    }

    public void addCook(int count) {
        if (tables.size() == 0) return;
        for (int i = 0; i < count; i++) {
            Cook cook = new Cook(i, tables.get(i % tables.size()));
            cook.color = colors[tables.indexOf(cook.getTable())];
            cooks.add(cook);
            //System.out.printf("cook: %d, %d\n", cook.getId(), i / count * tables.size());
        }
    }

    public void addConsumer(int count) {
        if (tables.size() == 0) return;
        for (int i = 0; i < count; i++) {
            Consumer consumer = new Consumer(i, tables.get(i % tables.size()));
            consumer.color = colors[tables.indexOf(consumer.getTable())];
            consumer.y = (WINDOW_HEIGHT - consumer.height);
            consumers.add(consumer);
        }
    }

    public void add(int tableCount, int cookCount, int consumerCount) {
        addTable(tableCount);
        addCook(cookCount);
        addConsumer(consumerCount);
    }

    public Restaurant() {
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

        RestaurantView restaurantView = new RestaurantView(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(3, 6, 10);
        restaurantView.setCooks(this.cooks);
        restaurantView.setTables(this.tables);
        restaurantView.setConsumers(this.consumers);
        add(restaurantView, constraints);
        restaurantView.start();

        pack();
        setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
    }
}