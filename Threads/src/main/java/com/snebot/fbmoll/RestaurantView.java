package com.snebot.fbmoll;

import com.snebot.fbmoll.object.Consumer;
import com.snebot.fbmoll.object.Cook;
import com.snebot.fbmoll.object.Table;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantView extends Canvas implements Runnable {
    private final Thread thread = new Thread(this, "Restaurant view");
    private volatile boolean running = false;

    public int viewWidth = 100;
    public int viewHeight = 100;
    private static final int PADDING = 20;

    private final List<Table> tables = new ArrayList<>();
    private final List<Cook> cooks = new ArrayList<>();
    private final List<Consumer> consumers = new ArrayList<>();

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
            consumer.y = (viewHeight - consumer.height - PADDING);
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

    public void start() {
        thread.start();
    }

    public void stop() {
        running = false;
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

    private void paint() {
        Graphics g = this.getGraphics();
        if (g == null) return;
        super.paint(g);

        for (int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            cook.x = (viewWidth / cooks.size()) * i + cook.width / 2;
            g.setColor(cook.color);
            g.fillRect(cook.x, cook.y, cook.width, cook.height);
        }

        int tableWidth = viewWidth / tables.size() - PADDING * 2;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            table.x = (viewWidth / tables.size()) * i + PADDING;
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