package com.serafinebot.pser.repaso.image;

import com.serafinebot.pser.repaso.util.FileUtils;
import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Convolution {
    private static final int IMAGE_WIDTH = 1920;
    private static final int IMAGE_HEIGHT = 1080;
    private static final int TARGET_WIDTH = IMAGE_WIDTH / 3;
    private static final int TARGET_HEIGHT = IMAGE_HEIGHT / 3;
    private static final int WINDOW_WIDTH = TARGET_WIDTH * 2;
    private static final int WINDOW_HEIGHT = TARGET_HEIGHT * 2;

    public static void main(String[] args) {
        ArrayList<BufferedImage> imageArray = new ArrayList<>();
        try {
            // open source image from resource directory
            FileUtils fileUtils = FileUtils.getInstance();
            String imageName = String.format("image-%dx%d-3.jpg", IMAGE_WIDTH, IMAGE_HEIGHT);
            File imageFile = fileUtils.resourceFile(imageName);
            BufferedImage originalImage = ImageIO.read(imageFile);
            imageArray.add(originalImage);

            byte[][][] convArray = {
                    {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}},
                    {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}},
                    {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}};

            // cycle through convolution array and create a new copy of the image with the convolution filter applied
            for (byte[][] conv : convArray) {
                BufferedImage image = ImageUtils.convolution(imageArray.get(0), conv);
                imageArray.add(image);
            }
        } catch (Exception e) {
            System.out.printf("filed to in main: %s", e.getMessage());
            return;
        }

        // display all of the images that we've processed
        JFrame frame = new JFrame("Convolution");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        int imageOffset = 0;
        for (BufferedImage image : imageArray) {
            JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_DEFAULT)));
            label.setLocation(imageOffset, 0);
            imageOffset += label.getWidth();
            panel.add(label);
        }

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
