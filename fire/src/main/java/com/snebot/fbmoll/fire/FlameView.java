package com.snebot.fbmoll.fire;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FlameView extends Canvas implements ActionListener {
    public int width;
    public int height;
    private int sizeModifier = 1;
    private Image backgroundImage;
    private Flame flame;
    private int flameWidth = 78;
    private int flameHeight = 50;
    private int speed;
    private Timer timer;

    private FlameView() {
    }

    public FlameView(int width, int height, int speed) {
        super();
        this.width = width;
        this.height = height;
        this.speed = speed;
        setupFlame();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSizeModifier() {
        return sizeModifier;
    }

    public void setSizeModifier(int sizeModifier) {
        this.sizeModifier = sizeModifier;
    }

    public void setupFlame() {
        if (flame == null) {
            ColorPalette cp = new ColorPalette(256);
            cp.addColor(new Color(0, 0, 0, 0), 0);
            cp.addColor(new Color(255, 50, 50, 64), 64);
            cp.addColor(new Color(255, 255, 120, 255), 80);
            cp.addColor(new Color(240, 115, 120, 255), 100);
            cp.addColor(new Color(80, 110, 190, 192), 128);
            cp.addColor(new Color(90, 165, 235, 128), 165);
            cp.addColor(new Color(255, 255, 255, 255), 255);
            cp.generatePalette();

            flame = new Flame(flameWidth / sizeModifier, flameHeight / sizeModifier, cp, 30);

            timer = new Timer(speed, e -> {
                validate();
                repaint();
            });
        }

        if (backgroundImage == null) {
            try {
                File imageFile = new File("/Users/hystrix/Development/DAM/PSER/fire/src/main/resources/chimenea.jpg");
                backgroundImage = ImageIO.read(imageFile);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void start() {
        timer.start();
        /*Graphics g = this.getGraphics();
        if (g != null && backgroundImage != null && flame != null) {
            g.drawImage(backgroundImage, 0, 0, null);
            while (run) {
                flame.coolBottom();
                flame.fillBottom();
                flame.processBuffer();
                g.drawImage(flame.processImage(), 193, 300, null);

                try {
                    Thread.sleep(speed);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }*/
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (flame != null && backgroundImage != null) {
            g.drawImage(backgroundImage, 0,0, null);
            flame.coolBottom();
            flame.fillBottom();
            flame.processBuffer();
            g.drawImage(flame.processImage(), 193, 300, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}