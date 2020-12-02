package com.snebot.fbmoll;

import com.snebot.fbmoll.object.Consumer;
import com.snebot.fbmoll.object.Cook;
import com.snebot.fbmoll.object.Table;

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
            consumer.y = (viewHeight - consumer.height - padding);
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
            cook.x = (viewWidth / cooks.size()) * i + cook.width / 2;
            g.setColor(cook.color);
            g.fillRect(cook.x, cook.y, cook.width, cook.height);
        }

        int tableWidth = viewWidth / tables.size() - padding * 2;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            table.x = (viewWidth / tables.size()) * i + padding;
            table.y = viewHeight / 2 - table.height / 2;
            table.width = tableWidth;
            g.setColor(table.color);
            g.fillRect(table.x, table.y, table.width, table.height);
        }

        for (int i = 0; i < consumers.size(); i++) {
            Consumer consumer = consumers.get(i);
            consumer.x = (viewWidth / consumers.size()) * i + consumer.width / 2;
            g.setColor(consumer.color);
            g.fillRect(consumer.x, consumer.y, consumer.width, consumer.height);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(viewWidth, viewHeight);
    }
}