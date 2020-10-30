package com.serafinebot.pser.repaso.image;

import com.serafinebot.pser.repaso.util.FileUtils;
import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;

public class Convolution {
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;
    private static final int IMAGE_WIDTH = 1920;
    private static final int IMAGE_HEIGHT = 1080;
    private static final int TARGET_WIDTH = WINDOW_WIDTH / 3;
    private static final int TARGET_HEIGHT = WINDOW_HEIGHT / 3;

    public static void main(String[] args) {
        ArrayList<BufferedImage> imageArray = new ArrayList<>();
        byte[][][] convArray = {
                {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}},
                {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}},
                {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}};
        try {
            BufferedImage originalImage = ImageIO.read(new File(FileUtils.getInstance().resourceDirectory() + String.format("image-%dx%d-3.jpg", IMAGE_WIDTH, IMAGE_HEIGHT)));
            imageArray.add(originalImage);

            for (byte[][] conv : convArray) {
                BufferedImage image = ImageUtils.copy(imageArray.get(0));
                byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                byte[] res = convolution(imageData, image.getWidth(), image.getHeight(), 3, conv);
                System.arraycopy(res, 0, imageData, 0, res.length);
                imageArray.add(image);
            }
        } catch (Exception e) {
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

    public static byte[] convolution(byte[] vec, int width, int height, int pro, byte[][] conv) {
        byte[] ret = new byte[vec.length];

        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            for (int j = 0; j < conv[i].length; j++) {
                K += conv[i][j];
            }
        }

        if (K == 0) K = 1;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < pro; k++) {
                    if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                        int index = (i * width * pro) + (j * pro) + k;
                        ret[index] = vec[index];
                        continue;
                    }

                    int sum = 0;
                    int conv_diff = conv.length / 2;
                    int index = ((i - conv_diff) * width * pro) + ((j - conv_diff) * pro) + k;
                    for (int l = 0; l < conv.length; l++) {
                        for (int m = 0; m < conv[l].length; m++) {
                            sum += Byte.toUnsignedInt(vec[index]) * conv[l][m];
                            index += pro;
                        }

                        index += (width - conv[l].length) * pro;
                    }

                    index = (i * width * pro) + (j * pro) + k;
                    Integer value = sum / K;
                    if (value > 255) {
                        value = 255;
                    } else if (value < 0) {
                        value = 0;
                    }

                    ret[index] = value.byteValue();
                }
            }
        }

        return ret;
    }
}
