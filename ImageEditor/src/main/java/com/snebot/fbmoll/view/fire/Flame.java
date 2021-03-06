package com.snebot.fbmoll.view.fire;

import com.snebot.fbmoll.data.FlameData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Flame extends BufferedImage implements Runnable {
    private int width;
    private int height;
    private int[] buffer;
    private ArrayList<Integer[]> map;
    public FlameData flameData;
    public int delay = 8;

    private final Thread thread = new Thread(this, "Flame thread");
    private volatile boolean running = false;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getBuffer() {
        if (buffer == null) buffer = new int[width * height];
        return buffer;
    }

    public Flame(int width, int height, FlameData flameData, ArrayList<Integer[]> map) {
        super(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        this.width = width;
        this.height = height;
        this.flameData = flameData;
        this.map = map;
    }

    private void coolSparks() {
        int[] buffer = getBuffer();
        for (Integer[] coord : map) {
            if (coord[0] >= width || coord[1] >= height) continue;
            int index = coord[1] * width + coord[0];
            int random = (int) (Math.random() * 100);
            if (random < flameData.sparkPercentage) buffer[index] = 0;
        }
    }

    private void addSparks() {
        int[] buffer = getBuffer();
        int max_value = flameData.colorPalette.getMaxValue();
        for (Integer[] coord : map) {
            if (coord[0] >= width || coord[1] >= height) continue;
            int index = coord[1] * width + coord[0];
            int random = (int) (Math.random() * 100);
            if (random < flameData.sparkPercentage) buffer[index] = max_value;
        }
    }

    private void processBuffer() {
        int[] buffer = getBuffer();
        int[] res = buffer.clone();

        for (int i = 0; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int index = i * width + j;
                int index_bottom = (i + 1) * width + j;
                res[index] = (int) ((buffer[index - 1] * flameData.mult_left +
                        buffer[index] * flameData.mult +
                        buffer[index + 1] * flameData.mult_right +
                        buffer[index_bottom - 1] * flameData.mult_bottom_left +
                        buffer[index_bottom] * flameData.mult_bottom +
                        buffer[index_bottom + 1] * flameData.mult_bottom_right) / flameData.divisor - flameData.constant);

                if (res[index] < 0) {
                    res[index] = 0;
                } else if (res[index] > flameData.colorPalette.getMaxValue()) {
                    res[index] = flameData.colorPalette.getMaxValue();
                }
            }
        }
        System.arraycopy(res, 0, buffer, 0, buffer.length);
    }

    private void processFrame() {
        ColorPalette palette = flameData.colorPalette;
        int[] buffer = getBuffer();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == height - 1) continue;
                int index = y * width + x;
                Color color = palette.getColor(buffer[index]);
                if (color == null) color = new Color(0, 0, 0, 0);
                this.setRGB(x, y, color.getRGB());
            }
        }
    }

    private void process() {
        this.coolSparks();
        this.addSparks();
        this.processBuffer();
        this.processFrame();
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void interrupt() {
        running = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            process();
            try {
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println("Flame - " + e.getMessage());
            }
        }
    }
}
