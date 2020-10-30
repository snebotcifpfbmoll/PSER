package com.serafinebot.pser.repaso.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Fire {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        byte[] imageBuffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int width = image.getWidth();
        int height = image.getHeight();
        int pro = 3;

        // fill bottom row
        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int random = (int)(Math.random() * 100);
                if (random % 4 == 0) {
                    for (int k = 0; k < pro; k++) {
                        int index = (i * width * pro) + (j * pro) + k;
                        imageBuffer[index] = (byte)255;
                    }
                }
            }
        }

        for (int i = height - 2; i > 1; i--) {
            for (int j = 1; j < width - 1; j++) {
                Integer sum = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = 0; l < pro; l++) {
                        int index = ((i + 1) * width * pro) + ((j - k) * pro) + l;
                        sum += Byte.toUnsignedInt(imageBuffer[index]);
                    }

                    sum /= pro;
                    byte avg = sum.byteValue();
                    for (int l = 0; l < pro; l++) {
                        int index = (i * width * pro) + (j * pro) + l;
                        imageBuffer[index] = avg;
                    }
                }
            }
        }

        // display all of the images that we've processed
        JFrame frame = new JFrame("Fire");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 22);
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
