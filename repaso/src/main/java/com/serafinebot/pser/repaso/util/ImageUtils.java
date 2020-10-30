package com.serafinebot.pser.repaso.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ImageUtils {
    public static BufferedImage copy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }

    public static byte[] convolution(byte[] src, int width, int height, int pro, byte[][] conv) {
        byte[] ret = new byte[src.length];

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
                        ret[index] = src[index];
                        continue;
                    }

                    int sum = 0;
                    int conv_diff = conv.length / 2;
                    int index = ((i - conv_diff) * width * pro) + ((j - conv_diff) * pro) + k;
                    for (int l = 0; l < conv.length; l++) {
                        for (int m = 0; m < conv[l].length; m++) {
                            sum += Byte.toUnsignedInt(src[index]) * conv[l][m];
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

    public static BufferedImage convolution(BufferedImage src, byte[][] conv) {
        BufferedImage image = ImageUtils.copy(src); // make a copy of source image
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData(); // get reference to image byte array
        byte[] res = ImageUtils.convolution(imageData, image.getWidth(), image.getHeight(), 3, conv); // process image with convolution filter
        System.arraycopy(res, 0, imageData, 0, res.length); // copy results to the source byte array
        return image;
    }
}
