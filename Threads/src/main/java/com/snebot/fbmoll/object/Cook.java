package com.snebot.fbmoll.object;

import java.awt.*;

public class Cook extends RestaurantObject {
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

    public Cook(int id, Table table) {
        super();
        this.id = id;
        this.table = table;
        this.width = 50;
        this.height = 50;
        this.color = Color.BLUE;

        super.start(() -> {
            if (table != null) table.put(this);
        });
    }
}
