package com.serafinebot.pser.repaso.util;

import java.awt.*;
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

    public static int calculateK(byte[][] conv) {
        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            for (int j = 0; j < conv[i].length; j++) {
                K += conv[i][j];
            }
        }
        return K == 0 ? 1 : K;
    }

    public static int calculateK(byte[] conv) {
        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            K += conv[i];
        }
        return K == 0 ? 1 : K;
    }

    public static byte[] convolution(byte[] src, int width, int height, int pro, int startX, int startY, int endX, int endY, byte[] conv) {
        byte[] ret = src.clone();
        int K = calculateK(conv);

        int conv_diff = conv.length / 2;
        if (startX < conv_diff) startX = conv_diff;
        //if (startY < conv_diff) startY = conv_diff;
        if (endX > width - conv_diff) endX = width - conv_diff;
        //if (endY > height - conv_diff) endY = height - conv_diff;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                for (int z = 0; z < pro; z++) {
                    Integer sum = 0;
                    int index = ((y - conv_diff) * width * pro) + ((x - conv_diff) * pro) + z;
                    for (int i = 0; i < conv.length; i++) {
                        sum += Byte.toUnsignedInt(src[index]) * conv[i];
                        index += pro;
                    }
                    index = (y * width * pro) + (x * pro) + z;
                    sum /= K;
                    if (sum > 255) {
                        sum = 255;
                    } else if (sum < 0) {
                        sum = 0;
                    }

                    ret[index] = sum.byteValue();
                }
            }
        }

        return ret;
    }

    public static byte[] convolution(byte[] src, int width, int height, int pro, int startX, int startY, int endX, int endY, byte[][] conv) {
        if (conv.length != conv[0].length) return null;

        byte[] ret = src.clone();
        int K = calculateK(conv);

        int conv_diff = conv.length / 2;
        if (startX < conv_diff) startX = conv_diff;
        if (startY < conv_diff) startY = conv_diff;
        if (endX > width - conv_diff) endX = width - conv_diff;
        if (endY > height - conv_diff) endY = height - conv_diff;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                for (int z = 0; z < pro; z++) {
                    Integer sum = 0;
                    int index = ((y - conv_diff) * width * pro) + ((x - conv_diff) * pro) + z;
                    for (int i = 0; i < conv.length; i++) {
                        for (int j = 0; j < conv[i].length; j++) {
                            sum += Byte.toUnsignedInt(src[index]) * conv[i][j];
                            index += pro;
                        }
                        index += (width - conv[i].length) * pro;
                    }

                    index = (y * width * pro) + (x * pro) + z;
                    sum /= K;
                    if (sum > 255) {
                        sum = 255;
                    } else if (sum < 0) {
                        sum = 0;
                    }

                    ret[index] = sum.byteValue();
                }
            }
        }

        return ret;
    }

    public static byte[] convolution(byte[] src, int width, int height, int pro, byte[][] conv) {
        return convolution(src, width, height, pro, 0, 0, width, height, conv);
    }

    public static BufferedImage convolution(BufferedImage src, byte[][] conv) {
        BufferedImage image = ImageUtils.copy(src); // make a copy of source image
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData(); // get reference to image byte array
        byte[] res = ImageUtils.convolution(imageData, image.getWidth(), image.getHeight(), 3, conv); // process image with convolution filter
        System.arraycopy(res, 0, imageData, 0, res.length); // copy results to the source byte array
        return image;
    }

    public static byte[] getByteArray(BufferedImage image) {
        return ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
    }
}
