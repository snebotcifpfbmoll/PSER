package com.snebot.fbmoll;

import java.awt.*;
import java.util.ArrayList;

public class RestaurantView extends Canvas implements Runnable {
    private boolean running = false;

    public int viewWidth = 100;
    public int viewHeight = 100;
    private static final int padding = 20;

    private final ArrayList<Table> tables = new ArrayList<>();
    private final ArrayList<Cook> cooks = new ArrayList<>();
    private final ArrayList<Consumer> consumers = new ArrayList<>();

    public void addTable(int count) {
        for (int i = 0; i < count; i++) tables.add(new Table(i));
    }

    public void addCook(int count) {
        if (tables.size() == 0) return;
        for (int i = 0; i < count; i++) cooks.add(new Cook(i, tables.get(i % tables.size())));
    }

    public void addConsumer(int count) {
        if (tables.size() == 0) return;
        for (int i = 0; i < count; i++) {
            Consumer consumer = new Consumer(i, tables.get(i % tables.size()));
            consumer.setyPos(viewHeight - consumer.getObjectHeight() - padding);
            consumers.add(consumer);
        }
    }

    public void add(int tableCount, int cookCount, int consumerCount) {
        addTable(tableCount);
        addCook(cookCount);
        addConsumer(consumerCount);
    }

    public RestaurantView() {
    }

    public RestaurantView(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                this.paint();
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
    }

    private void paint() {
        Graphics g = this.getGraphics();
        if (g == null) return;

        for (int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            cook.setxPos((viewWidth / cooks.size()) * i + cook.getObjectWidth() / 2);
            g.setColor(cook.getColor());
            g.fillRect(cook.getxPos(), cook.getyPos(), cook.getObjectWidth(), cook.getObjectHeight());
        }

        int tableWidth = viewWidth / tables.size() - padding * 2;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            table.setxPos((viewWidth / tables.size()) * i + padding);
            table.setyPos(viewHeight / 2 - table.getObjectHeight() / 2);
            table.setObjectWidth(tableWidth);
            g.setColor(table.getColor());
            g.fillRect(table.getxPos(), table.getyPos(), table.getObjectWidth(), table.getObjectHeight());
        }

        for (int i = 0; i < consumers.size(); i++) {
            Consumer consumer = consumers.get(i);
            consumer.setxPos((viewWidth / consumers.size()) * i + consumer.getObjectWidth() / 2);
            g.setColor(consumer.getColor());
            g.fillRect(consumer.getxPos(), consumer.getyPos(), consumer.getObjectWidth(), consumer.getObjectHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(viewWidth, viewHeight);
    }
}