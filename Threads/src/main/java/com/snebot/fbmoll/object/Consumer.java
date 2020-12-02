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

        super.start(() -> {
            if (table != null) table.take(this);
        });
    }
}
