package com.serafinebot.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Image1 {
    private static final String imageSource = "/Users/hystrix/Development/DAM/PSER/conv-effect/";
    private static final int IMAGE_WIDTH = 1920;
    private static final int IMAGE_HEIGHT = 1080;
    private static final int TARGET_WIDTH = IMAGE_WIDTH / 3;
    private static final int TARGET_HEIGHT = IMAGE_HEIGHT / 3;

    public static void main(String[] args) {
        BufferedImage orgImage = null;
        BufferedImage grayImage = null;
        try {
            // load image
            orgImage = ImageIO.read(new File(imageSource + String.format("image-%dx%d.jpg", IMAGE_WIDTH, IMAGE_HEIGHT)));
            grayImage = imgCopy(orgImage);
            byte[] imgData = ((DataBufferByte) grayImage.getRaster().getDataBuffer()).getData();

            System.out.println(grayImage.getColorModel());

            for (int i = 0; i < imgData.length - 3; i += 3) {
                int sum = 0;
                for (int j = 0; j < 3; j++) sum += imgData[i + j];
                byte avg = (byte)(sum / 3);
                //System.out.println(String.format("r: %d; g: %d; b: %d -> %d / 3 = %d : %d", imgData[i + 0], imgData[i + 1], imgData[i + 2], sum, sum / 3, avg));
                for (int j = 0; j < 3; j++) imgData[i + j] = avg;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        JFrame frame = new JFrame("Image");
        frame.setSize(TARGET_WIDTH, TARGET_HEIGHT * 2 + 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel orgLabel = new JLabel(new ImageIcon(orgImage.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_DEFAULT)));
        orgLabel.setLocation(0, 0);

        JLabel grayLabel = new JLabel(new ImageIcon(grayImage.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_DEFAULT)));
        grayLabel.setLocation(orgImage.getWidth(), 0);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        panel.add(orgLabel);
        panel.add(grayLabel);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static BufferedImage imgCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }
}
