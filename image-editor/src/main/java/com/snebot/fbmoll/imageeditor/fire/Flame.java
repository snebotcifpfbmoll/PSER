package com.snebot.fbmoll.imageeditor.fire;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Flame {
    private int width;
    private int height;
    private int[] buffer;
    private ColorPalette palette;
    private int sparkPercentage;

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

    public int getSparkPercentage() {
        return sparkPercentage;
    }

    public void setSparkPercentage(int sparkPercentage) {
        this.sparkPercentage = sparkPercentage;
    }

    public ColorPalette getPalette() {
        return palette;
    }

    public void setPalette(ColorPalette palette) {
        this.palette = palette;
    }

    private Flame() {
    }

    public Flame(int width, int height, ColorPalette palette, int sparkPercentage) {
        this.width = width;
        this.height = height;
        this.palette = palette;
        this.sparkPercentage = sparkPercentage;
    }

    public void coolSparks(byte[][] map) {
        int[] buffer = getBuffer();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    int index = (i * width) + j;
                    int random = (int)(Math.random() * 100);
                    if (random < sparkPercentage) buffer[index] = 0;
                }
            }
        }
    }

    public void addSparks(byte[][] map) {
        int[] buffer = getBuffer();
        int max_value = getPalette().getMaxValue();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1) {
                    int index = (i * width) + j;
                    int random = (int)(Math.random() * 100);
                    if (random < sparkPercentage) buffer[index] = max_value;
                }
            }
        }
    }

    public void coolBottom() {
        int[] buffer = getBuffer();
        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = (i * width) + j;
                int random = (int)(Math.random() * 100);
                if (random < sparkPercentage) {
                    buffer[index] = 0;
                }
            }
        }
    }

    public void fillBottom() {
        int[] buffer = getBuffer();
        int max_value = getPalette().getMaxValue();
        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = (i * width) + j;
                buffer[index] = 0;
                int random = (int)(Math.random() * 100);
                if (random < sparkPercentage) {
                    buffer[index] = max_value;
                }
            }
        }
    }

    public void processBuffer() {
        int[] buffer = getBuffer();
        int[] res = buffer.clone();

        for (int i = 0; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int index = i * width + j;
                int index_bottom = (i + 1) * width + j;
                res[index] = (int) ((buffer[index - 1] * 1.2D +
                        buffer[index] * 1.5D +
                        buffer[index + 1] * 1.2D +
                        buffer[index_bottom - 1] * 0.7D +
                        buffer[index_bottom] * 0.738D +
                        buffer[index_bottom + 1] * 0.7D) / 5.995D - 0.37D);

                if (res[index] < 0) {
                    res[index] = 0;
                } else if (res[index] > palette.getMaxValue()) {
                    res[index] = palette.getMaxValue();
                }
            }
        }
        System.arraycopy(res, 0, buffer, 0, buffer.length);
    }

    public Image processImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        ColorPalette palette = getPalette();
        int[] buffer = getBuffer();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == height - 1) continue;
                int index = y * width + x;
                Color color = palette.getColor(buffer[index]);
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }
}
