package com.snebot.fbmoll;

import java.awt.*;

public class Table {
    private static final int maxFoodCount = 5;
    private int tableNumber = 0;
    private int foodCount = 0;

    private int objectWidth = 200;
    private int objectHeight = 80;
    private int xPos = 0;
    private int yPos = 0;
    private Color color = Color.GRAY;

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
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

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public synchronized void take(Consumer consumer) {
        while (foodCount < 1) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        foodCount--;
        System.out.printf("table: %d; consumer: %d; count: %d\n", tableNumber, consumer.getId(), foodCount);
        notifyAll();
    }

    public synchronized void put(Cook cook) {
        while (foodCount >= maxFoodCount) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        foodCount++;
        System.out.printf("table: %d; cook: %d; count: %d\n", tableNumber, cook.getId(), foodCount);
        notifyAll();
    }
}
