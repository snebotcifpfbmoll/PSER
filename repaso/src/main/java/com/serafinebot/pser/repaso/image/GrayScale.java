package com.serafinebot.pser.repaso.image;

import com.serafinebot.pser.repaso.util.FileUtils;
import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class GrayScale {
    private static final int IMAGE_WIDTH = 1920;
    private static final int IMAGE_HEIGHT = 1080;
    private static final int TARGET_WIDTH = IMAGE_WIDTH / 3;
    private static final int TARGET_HEIGHT = IMAGE_HEIGHT / 3;

    public static void main(String[] args) {
        BufferedImage orgImage = null;
        BufferedImage grayImage = null;
        try {
            orgImage = ImageIO.read(new File(FileUtils.getInstance().resourceDirectory() + String.format("image-%dx%d.jpg", IMAGE_WIDTH, IMAGE_HEIGHT)));
            grayImage = ImageUtils.copy(orgImage);
            byte[] imgData = ((DataBufferByte) grayImage.getRaster().getDataBuffer()).getData();

            for (int i = 0; i < imgData.length - 3; i += 3) {
                int sum = 0;
                for (int j = 0; j < 3; j++) sum += imgData[i + j] & 0xff;
                for (int j = 0; j < 3; j++) imgData[i + j] = (byte) (sum / 3);
            }
        } catch (IOException e) {
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
}
