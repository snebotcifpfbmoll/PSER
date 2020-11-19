package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.FlameData;
import com.snebot.fbmoll.view.fire.ColorPalette;
import com.snebot.fbmoll.view.fire.Flame;

import javax.swing.*;
import java.awt.*;

public class ImageViewer extends JPanel {
    private int width = 0;
    private int height = 0;
    private Thread flameThread = null;
    private FlameView flameView = null;

    public byte[][] testSparkMap(int w, int h) {
        byte[][] map = new byte[h][w];
        for (int i = h - 1; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i][j] = 1;
            }
        }
        return map;
    }

    public ImageViewer(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        // tests
        ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 50, 50, 64), 64);
        cp.addColor(new Color(255, 255, 120, 255), 80);
        cp.addColor(new Color(240, 115, 120, 255), 100);
        cp.addColor(new Color(80, 110, 190, 192), 128);
        cp.addColor(new Color(90, 165, 235, 128), 165);
        cp.addColor(new Color(255, 255, 255, 255), 255);
        cp.generatePalette();
        FlameData flameData = new FlameData(cp, 30);
        flameData.mult_left = 1.2D;
        flameData.mult = 1.5D;
        flameData.mult_right = 1.2D;
        flameData.mult_bottom_left = 0.7D;
        flameData.mult_bottom = 0.738D;
        flameData.mult_bottom_right = 0.7D;
        flameData.divisor = 5.995D;
        flameData.constant = 0.37D;

        Flame flame = new Flame(width, height, flameData, testSparkMap(width, height));
        flameView = new FlameView(flame, 100);
        add(flameView);
        start();
    }

    public void start() {
        if (flameView == null) return;
        if (flameThread == null) flameThread = new Thread(flameView);
        flameThread.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
