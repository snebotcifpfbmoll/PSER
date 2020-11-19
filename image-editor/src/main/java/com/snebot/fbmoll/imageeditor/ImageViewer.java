package com.snebot.fbmoll.imageeditor;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.imageeditor.fire.ColorPalette;
import com.snebot.fbmoll.imageeditor.fire.Flame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ImageViewer extends Canvas {
    public int width = 0;
    public int height = 0;

    // conf
    private int temperatureThreshold = 128;

    // UI
    private Image sourceImage = null;
    private Image convolutionImage = null;
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

    public BufferedImage copy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }

    public int getAverage(Color color) {
        return color.getRed() + color.getGreen() + color.getBlue() / 3;
    }

    public byte[][] createTemperatureMap(Image image) {
        if (!(image instanceof BufferedImage)) return null;
        BufferedImage bufferedImage = (BufferedImage)image;
        int imageWidth = bufferedImage.getWidth(null);
        int imageHeight = bufferedImage.getHeight(null);

        byte[][] tempMap = new byte[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int avg = getAverage(new Color(bufferedImage.getRGB(j, i)));
                if (avg >= temperatureThreshold) tempMap[i][j] = 1;
            }
        }
        return tempMap;
    }

    private Image createImageFromTempMap(byte[][] temp) {
        if (temp == null) return null;
        int width = temp[0].length;
        int height = temp.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = temp[i][j] == 1 ? Color.WHITE : Color.BLACK;
                image.setRGB(j, i, color.getRGB());
            }
        }
        return image;
    }

    public void process(BufferedImage image, ConvolutionData data) {
        sourceImage = image;
        convolutionImage = convolution(image, data.getMatrix(), data.getK());


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
        byte[][] map = createTemperatureMap(convolutionImage);
        resultImage = createImageFromTempMap(map);
        if (sourceImage != null && convolutionImage != null && resultImage != null) {
            double targetWidth = this.width * 0.5D;
            double targetHeight = this.height * 0.5D;
            double width = sourceImage.getWidth(null);
            double height = sourceImage.getHeight(null);
            double widthRatio = targetWidth / width;
            double heightRatio = targetHeight / height;
            double ratio = Math.min(widthRatio, heightRatio);
            int newWidth = (int)(width * ratio);
            int newHeight = (int)(height * ratio);
            g.drawImage(sourceImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, 0, null);
            g.drawImage(convolutionImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), newWidth, 0, null);
            g.drawImage(resultImage.getScaledInstance(newWidth * 2, newHeight * 2, Image.SCALE_DEFAULT), 0, newHeight, null);

            // fire
            ColorPalette cp = new ColorPalette(256);
            cp.addColor(new Color(0, 0, 0, 0), 0);
            cp.addColor(new Color(255, 50, 50, 64), 64);
            cp.addColor(new Color(255, 255, 120, 255), 80);
            cp.addColor(new Color(240, 115, 120, 255), 100);
            cp.addColor(new Color(80, 110, 190, 192), 128);
            cp.addColor(new Color(90, 165, 235, 128), 165);
            cp.addColor(new Color(255, 255, 255, 255), 255);
            cp.generatePalette();

            Flame flame = new Flame(sourceImage.getWidth(null), sourceImage.getHeight(null), cp, 30);
            for (int i = 0; i < 10; i++) {
                flame.coolSparks(map);
                flame.addSparks(map);
                flame.processBuffer();
            }
            Image fire = flame.processImage();
            g.drawImage(fire.getScaledInstance(newWidth * 2, newHeight * 2, Image.SCALE_DEFAULT), 0, newHeight, null);
        }
    }
}
