package com.snebot.fbmoll;

import java.awt.*;
import java.util.Random;

public class Consumer implements Runnable {
    private int id = 0;
    private int objectSize = 100;
    private Table table = null;
    private int minTime = 2000;
    private int maxTime = 5000;

    private int objectWidth = 50;
    private int objectHeight = 50;
    private int xPos = 0;
    private int yPos = 0;
    private Color color = Color.BLUE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(int objectSize) {
        this.objectSize = objectSize;
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Consumer() {
        setup();
    }

    public Consumer(int id, Table table) {
        this.id = id;
        this.table = table;
        setup();
    }

    public void setup() {
    }

    public void run() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(random.nextInt(maxTime + 1 - minTime) + minTime);
                if (table != null) {
                    table.take(this);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
