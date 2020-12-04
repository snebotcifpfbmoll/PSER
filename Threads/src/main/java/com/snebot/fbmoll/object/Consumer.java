package com.snebot.fbmoll.object;

import java.awt.*;

public class Consumer extends RestaurantObject {
    private int id = 0;
    private Table table = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Consumer(int id, Table table) {
        this.id = id;
        this.table = table;
        this.width = 50;
        this.height = 50;
        this.color = Color.RED;

        this.minTime = 1000;
        this.maxTime = 4000;

        super.start(() -> {
            try {
                Thread.sleep(getRandomTime());

                int initialY = y;
                int diff = y - table.y - table.height;
                int nsteps = diff / step;
                int res = diff % step;
                for (int i = 0; i < nsteps; i++) {
                    y -= step;
                    Thread.sleep(speed);
                }
                y -= res;

                table.take(this);

                diff = initialY - y;
                nsteps = diff / step;
                res = diff % step;
                for (int i = 0; i < nsteps; i++) {
                    y += step;
                    Thread.sleep(speed);
                }
                y += res;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
