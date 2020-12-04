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
    public int delay = 40;
    private static final int PADDING = 20;

    private List<Table> tables = new ArrayList<>();
    private List<Cook> cooks = new ArrayList<>();
    private List<Consumer> consumers = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Cook> getCooks() {
        return cooks;
    }

    public void setCooks(List<Cook> cooks) {
        this.cooks = cooks;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
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
                Thread.sleep(delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void paint() {
        Graphics g = this.getGraphics();
        if (g == null) return;
        super.paint(g);

        int tableWidth = viewWidth / tables.size() - PADDING * 2;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            table.x = (viewWidth / tables.size()) * i + PADDING;
            table.y = viewHeight / 2 - table.height / 2;
            table.width = tableWidth;
            g.setColor(table.color);
            g.fillRect(table.x, table.y, table.width, table.height);

            g.setColor(Color.RED);
            g.setFont(new Font("Courier", Font.PLAIN, 35));
            g.drawChars(String.valueOf(table.getFoodCount()).toCharArray(), 0, 1, table.x + table.width / 2, table.y + table.height / 2);
        }

        for (int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            int initx = cook.getTable().x;
            int tableSize = cook.getTable().width;
            cook.x = (tableSize / cooks.size()) * i + initx;
            g.setColor(cook.color);
            g.fillRect(cook.x, cook.y, cook.width, cook.height);
        }

        for (int i = 0; i < consumers.size(); i++) {
            Consumer consumer = consumers.get(i);
            int initx = consumer.getTable().x;
            int tableSize = consumer.getTable().width;
            consumer.x = (tableSize / consumers.size()) * i + initx;
            g.setColor(consumer.color);
            g.fillRect(consumer.x, consumer.y, consumer.width, consumer.height);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(viewWidth, viewHeight);
    }
}