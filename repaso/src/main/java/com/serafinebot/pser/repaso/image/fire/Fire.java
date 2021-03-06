package com.serafinebot.pser.repaso.image.fire;

import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Fire {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int IMAGE_WIDTH = WINDOW_WIDTH / 4;
    private static final int IMAGE_HEIGHT = WINDOW_HEIGHT / 4;

    public static void main(String[] args) {
        Image image = Fire.createFireImage(IMAGE_WIDTH, IMAGE_HEIGHT);

        // display all of the images that we've processed
         
    }

    public static Image createFireImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] imageBuffer = ImageUtils.getByteArray(image);
        int pro = 3;

        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int random = (int) (Math.random() * 100);
                if (random % 4 == 0) {
                    int index = (i * width * pro) + (j * pro);
                    imageBuffer[index] = (byte) 0x22;
                    imageBuffer[index + 1] = (byte) 0x58;
                    imageBuffer[index + 2] = (byte) 0xe2;
                }
            }
        }

        BufferedImage tmpImage = ImageUtils.copy(image);
        byte[] tmpBuffer = ImageUtils.getByteArray(tmpImage);
        byte[][] conv = {{0, 0, 0}, {0, 0, 0}, {1, 1, 1}};

        for (int i = height - 2; i > 0; i--) {
            byte[] res = ImageUtils.convolution(tmpBuffer, width, height, 3, 0, i, width, i + 1, conv);
            if (res == null) break;
            System.arraycopy(res, 0, tmpBuffer, 0, tmpBuffer.length);
        }

        System.arraycopy(tmpBuffer, 0, imageBuffer, 0, imageBuffer.length);

        return image;
    }
}
