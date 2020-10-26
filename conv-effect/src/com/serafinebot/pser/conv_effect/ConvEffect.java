package com.serafinebot.pser.conv_effect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;

public class ConvEffect {
    private static final String imageSource = "/Users/hystrix/Development/DAM/PSER/conv-effect/";
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;
    private static final int IMAGE_WIDTH = 1920;
    private static final int IMAGE_HEIGHT = 1080;
    private static final int TARGET_WIDTH = WINDOW_WIDTH / 4;
    private static final int TARGET_HEIGHT = WINDOW_HEIGHT / 4;

    public static void main(String[] args) {
        ArrayList<BufferedImage> imageArray = new ArrayList<>();
        try {
            BufferedImage originalImage = ImageIO.read(new File(imageSource + String.format("image-%dx%d.jpg", IMAGE_WIDTH, IMAGE_HEIGHT)));
            imageArray.add(originalImage);

            byte[][][] convArray = {
                    {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}},
                    {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}},
                    {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}};

            for (byte[][] conv : convArray) {
                BufferedImage image = copyImage(imageArray.get(0));
                byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                byte[] res = calculateConv(imageData, conv, image.getWidth(), image.getHeight(), 3);
                System.arraycopy(res, 0, imageData, 0, res.length);
                imageArray.add(image);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        JFrame frame = new JFrame("Image");
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

    public static BufferedImage copyImage(BufferedImage bufferedImage) {
        ColorModel cm = bufferedImage.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }

    public static byte[] calculateConv(byte[] vec, byte[][] conv, int width, int height, int pro) {
        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            for (int j = 0; j < conv[i].length; j++) {
                K += conv[i][j];
            }
        }

        int svec = vec.length;
        byte[] ret = new byte[svec];
        for (int i = 0; i < svec; i++) {
            if (i / (width * pro) == 0 || i / (width * pro) == height - 1 || (i / pro) % width == 0 || (i / pro) % width == width - 1) {
                ret[i] = vec[i];
                continue;
            }

            int sum = 0;
            int index = i - ((width + 1) * pro);
            for (int j = 0; j < conv.length; j++) {
                for (int k = 0; k < conv[j].length; k++) {
                    sum += vec[index] * conv[j][k];
                    index += pro;
                }

                index += (width - conv[j].length) * pro;
            }

            if (K == 0) K = 1;
            int value = sum / K;
            ret[i] = (byte)value;

            if (value > Byte.MAX_VALUE) {
                ret[i] = Byte.MAX_VALUE;
            } else if (value < Byte.MIN_VALUE) {
                ret[i] = Byte.MIN_VALUE;
            }
        }

        return ret;
    }
}