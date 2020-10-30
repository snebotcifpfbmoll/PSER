package com.serafinebot.pser.repaso.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Fire {
    private static final int WINDOW_WIDTH = 1920 / 2;
    private static final int WINDOW_HEIGHT = 1080 / 2;

    public static void main(String[][] args) {
        BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        byte[] imageBuffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int width = image.getWidth();
        int height = image.getHeight();
        int pro = 3;

        // fill bottom row
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((Math.random() * 2 + 1) % 2 == 0) {
                    for (int k = 0; k < pro; k++) {
                        int index = (i * width * pro) + (j * pro) + k;
                        imageBuffer[index] = (byte) 255;
                    }
                }
            }
        }

        // display all of the images that we've processed
        JFrame frame = new JFrame("Image");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel label = new JLabel(new ImageIcon(image));
        label.setLocation(0, 0);
        panel.add(label);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
