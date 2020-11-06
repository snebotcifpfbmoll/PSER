package com.snebot.fbmoll.fire;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.awt.*;
import java.util.List;

@SpringBootTest
public class FlameTests {

    @Test
    void tryFillBottom() {
        int width = 10;
        int height = 10;
        Flame flame = new Flame(width, height, new ColorPalette(256), 20);
        flame.fillBottom();
        int[] buffer = flame.getBuffer();
        for (int i = flame.getHeight() - 1; i < flame.getHeight(); i++) {
            for (int j = 0; j < flame.getWidth(); j++) {
                System.out.printf("%d, ", buffer[i * flame.getWidth() + j]);
            }
            System.out.println();
        }
    }

    @Test
    void tryProcessBuffer() {
        int width = 20;
        int height = 20;
        Flame flame = new Flame(width, height, new ColorPalette(256), 20);
        flame.fillBottom();
        flame.processBuffer();
        int[] buffer = flame.getBuffer();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = i * width + j;
                System.out.printf("%d, ", buffer[index]);
            }
            System.out.println();
        }
    }

    @Test
    void tryAddColor() {
        ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(255, 165, 0, 255), 255);
        cp.addColor(new Color(255, 0, 0, 255), 128);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 0, 0, 255), 10);
        cp.addColor(new Color(255, 255, 0, 255), 10);
        cp.removeColor(0);
        for (Integer key : cp.getColorMapIndicies()) {
            System.out.println(key);
        }
    }

    @Test
    void tryGeneratePalette() {
        ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 0, 0, 255), 64);
        cp.addColor(new Color(255, 165, 0, 255), 128);
        cp.addColor(new Color(255, 255, 255, 255), 255);
        cp.generatePalette();
        List<Color> palette = cp.getPalette();

        int index = 0;
        for (Color color : palette) {
            System.out.printf("[%d] r: %d; g: %d; b: %d; a: %d\n", index++, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }
    }

    @Test
    void tryGenerateImage() {
        ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 0, 0, 255), 64);
        cp.addColor(new Color(255, 165, 0, 255), 128);
        cp.addColor(new Color(255, 255, 255, 255), 255);
        cp.generatePalette();

        Flame flame = new Flame(800, 800, cp, 20);
        flame.fillBottom();
        flame.processBuffer();
        Image image = flame.processImage();

        Assert.notNull(image, "failed to process image");
    }
}
