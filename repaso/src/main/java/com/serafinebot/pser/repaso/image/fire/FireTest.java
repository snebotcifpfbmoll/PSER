package com.serafinebot.pser.repaso.image.fire;

import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FireTest {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int IMAGE_WIDTH = WINDOW_WIDTH / 4;
    private static final int IMAGE_HEIGHT = WINDOW_HEIGHT / 4;

    public static void main(String[] args) {
        Image image = FireTest.createFireImage(IMAGE_WIDTH, IMAGE_HEIGHT);

        // display all of the images that we've processed
        JFrame frame = new JFrame("Fire test");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 22);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT)));
        label.setLocation(0, 0);
        panel.add(label);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static Image createFireImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] imageBuffer = ImageUtils.getByteArray(image);
        int pro = 3;

        fillBottomRows(imageBuffer, width, height, pro, 1);

        BufferedImage tmpImage = ImageUtils.copy(image);
        byte[] tmpBuffer = ImageUtils.getByteArray(tmpImage);
        byte[][] conv = {{0, 0, 0}, {0, 1, 0}, {8, 8, 8}};

        for (int i = height - 2; i > 0; i--) {
            tmpBuffer = ImageUtils.convolution(tmpBuffer, width, height, 3, 0, i, width, i + 1, conv);
        }

        if (tmpBuffer != null) System.arraycopy(tmpBuffer, 0, imageBuffer, 0, imageBuffer.length);

        return image;
    }

    public static void fillBottomRows(byte[] src, int width, int height, int pro, int rows) {
        int startY = height - rows;
        for (int i = startY; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int random = (int)(Math.random() * 2);
                if (random % 2 != 0) continue;
                int index = (i * width * pro) + (j * pro);
                src[index] = (byte) 0x22;
                src[index + 1] = (byte) 0x58;
                src[index + 2] = (byte) 0xe2;
            }
        }

        /*byte[][] conv = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        byte[] tmp = ImageUtils.convolution(src, width, height, pro, 0, startY, width, height, conv);
        if (tmp != null) System.arraycopy(tmp, 0, src, 0, src.length);*/
    }
}
