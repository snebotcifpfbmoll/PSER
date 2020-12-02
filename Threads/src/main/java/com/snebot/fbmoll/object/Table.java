package com.snebot.fbmoll.object;

import java.awt.*;

public class Table extends RestaurantObject {
    private static final int MAX_FOOD = 5;
    private int tableNumber = 0;
    private int foodCount = 0;

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.width = 80;
        this.height = 80;
        this.color = Color.GRAY;
    }

    public synchronized void take(Consumer consumer) {
        while (this.foodCount < 1) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        this.foodCount--;
        //System.out.printf("table: %d; consumer: %d; count: %d\n", tableNumber, consumer.getId(), foodCount);
        notifyAll();
    }

    public synchronized void put(Cook cook) {
        while (this.foodCount >= MAX_FOOD) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        this.foodCount++;
        //System.out.printf("table: %d; cook: %d; count: %d\n", tableNumber, cook.getId(), foodCount);
        notifyAll();
    }
}
