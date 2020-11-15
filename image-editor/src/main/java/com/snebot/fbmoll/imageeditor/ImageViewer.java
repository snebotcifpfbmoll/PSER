package com.snebot.fbmoll.imageeditor;

import com.snebot.fbmoll.data.ConvolutionData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ImageViewer extends Canvas {
    public int width = 0;
    public int height = 0;

    // UI
    private Image sourceImage = null;
    private Image resultImage = null;

    public ImageViewer() {
        super();
    }

    public ImageViewer(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public byte[] convolution(byte[] src, int width, int height, int pro, int startX, int startY, int endX, int endY, int[][] conv, int K) {
        if (conv.length != conv[0].length) return null;

        byte[] ret = src.clone();

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

    public BufferedImage convolution(BufferedImage src, int[][] conv, int K) {
        BufferedImage image = copy(src);
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[] res = convolution(imageData, image.getWidth(), image.getHeight(), 3, 0, 0, image.getWidth(), image.getHeight(), conv, K);
        System.arraycopy(res, 0, imageData, 0, res.length);
        return image;
    }

    public static BufferedImage copy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }

    public void process(BufferedImage image, ConvolutionData data) {
        sourceImage = image;
        resultImage = convolution(image, data.getMatrix(), data.getK());
        validate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (sourceImage != null && resultImage != null) {
            double targetWidth = this.width / 1.5D;
            double targetHeight = this.height / 1.5D;
            double width = sourceImage.getWidth(null);
            double height = sourceImage.getHeight(null);
            double widthRatio = targetWidth / width;
            double heightRatio = targetHeight / height;
            double ratio = Math.min(widthRatio, heightRatio);
            int newWidth = (int)(width * ratio);
            int newHeight = (int)(height * ratio);
            g.drawImage(sourceImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, 0, null);
            g.drawImage(resultImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, newHeight, null);
        }
    }
}
