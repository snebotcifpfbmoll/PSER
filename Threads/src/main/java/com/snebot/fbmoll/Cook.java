package com.snebot.fbmoll;

import java.awt.*;
import java.util.Random;

public class Cook implements Runnable {
    private int id = 0;
    private Table table = null;
    private int minTime = 5000;
    private int maxTime = 9000;

    private int objectWidth = 50;
    private int objectHeight = 50;
    private int xPos = 0;
    private int yPos = 0;
    private Color color = Color.RED;

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

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getObjectWidth() {
        return objectWidth;
    }

    public void setObjectWidth(int objectWidth) {
        this.objectWidth = objectWidth;
    }

    public int getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(int objectHeight) {
        this.objectHeight = objectHeight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Cook() {
    }

    public Cook(int id, Table table) {
        this.id = id;
        this.table = table;
    }

    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(maxTime + 1 - minTime) + minTime);
                if (table != null) {
                    table.put(this);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
