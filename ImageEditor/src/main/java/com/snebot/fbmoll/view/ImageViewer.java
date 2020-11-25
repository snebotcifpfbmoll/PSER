package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;
import com.snebot.fbmoll.view.fire.Flame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ImageViewer extends Canvas implements Runnable {
    private int width = 1;
    private int height = 1;
    private Timer timer = null;
    public int delay = 10;

    private boolean update = true;
    private Thread flameThread = null;
    private Image sourceImage = null;
    private Image convolutionImage = null;
    private Image resultImage = null;
    private Image fireImage = null;
    private Flame flame = null;

    public ImageViewer() {
        super();
    }

    public ImageViewer(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        timer = new Timer(this.delay, e -> {
            paint();
        });
    }

    public byte[] convolution(byte[] src, int width, int height, int pro, int startX, int startY, int endX, int endY, int[][] conv, int K) {
        if (conv.length != conv[0].length) return null;
        byte[] ret = new byte[src.length];

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

    private BufferedImage convolution(BufferedImage src, int[][] conv, int K) {
        BufferedImage image = copy(src);
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[] res = convolution(imageData, image.getWidth(), image.getHeight(), 3, 0, 0, image.getWidth(), image.getHeight(), conv, K);
        System.arraycopy(res, 0, imageData, 0, res.length);
        return image;
    }

    private BufferedImage copy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean alpha = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, alpha, null);
    }

    private int getAverage(Color color) {
        return color.getRed() + color.getGreen() + color.getBlue() / 3;
    }

    private ArrayList<Integer[]> createTemperatureMap(Image image, int threshold) {
        if (!(image instanceof BufferedImage)) return null;
        BufferedImage bufferedImage = (BufferedImage) image;
        int imageWidth = bufferedImage.getWidth(null);
        int imageHeight = bufferedImage.getHeight(null);

        ArrayList<Integer[]> map = new ArrayList<>();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int avg = getAverage(new Color(bufferedImage.getRGB(x, y)));
                if (avg >= threshold) map.add(new Integer[]{x, y});
            }
        }
        return map;
    }

    private ArrayList<Integer[]> testMap(int w, int h) {
        ArrayList<Integer[]> map = new ArrayList<>();
        for (int y = h - 1; y < h; y++) {
            for (int x = 0; x < w; x++) {
                map.add(new Integer[]{x, y});
            }
        }
        return map;
    }

    private Image createImageFromTempMap(int width, int height, ArrayList<Integer[]> map) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
        for (Integer[] coord : map) {
            image.setRGB(coord[0], coord[1], Color.WHITE.getRGB());
        }
        return image;
    }

    private Dimension resize(int width, int height, int targetWidth, int targetHeight) {
        double wRatio = (double) targetWidth / width;
        double hRatio = (double) targetHeight / height;
        double ratio = Math.min(wRatio, hRatio);
        int newWidth = (int) (width * ratio);
        int newHeight = (int) (height * ratio);
        return new Dimension(newWidth, newHeight);
    }

    private Image resize(Image image, int width, int height) {
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        Dimension dimen = resize(imageWidth, imageHeight, width, height);
        return image.getScaledInstance(dimen.width, dimen.height, Image.SCALE_DEFAULT);
    }

    public void process(BufferedImage image, ConvolutionData data, FlameData flameData) {
        int targetWidth = this.width / 3;
        double ratio = (double) image.getWidth() / image.getHeight();
        int newHeight = (int) (targetWidth / ratio);
        int newWidth = (int) (newHeight * ratio);
        this.sourceImage = resize(image, newWidth, newHeight);

        Image convolutionImage = convolution(image, data.matrix, data.k);
        this.convolutionImage = resize(convolutionImage, newWidth, newHeight);

        ArrayList<Integer[]> map = createTemperatureMap(convolutionImage, data.temperatureThreshold);
        Image resultImage = createImageFromTempMap(convolutionImage.getWidth(null), convolutionImage.getHeight(null), map);
        this.resultImage = resize(resultImage, newWidth, newHeight);

        /*map = testMap(200, 200);
        int y = this.sourceImage.getHeight(null);
        this.fireImage = resize(image, this.width, this.height - y);
        this.flame = new Flame(200, 200, flameData, map);*/
        //map = testMap(resultImage.getWidth(null), resultImage.getHeight(null));
        int y = this.sourceImage.getHeight(null);
        this.fireImage = resize(image, this.width, this.height - y);
        this.flame = new Flame(resultImage.getWidth(null), resultImage.getHeight(null), flameData, map);
        this.update = true;
    }

    private void paint() {
        Graphics g = this.getGraphics();
        if (g == null) return;
        if (update) {
            int xOffset = 0;
            if (sourceImage != null) {
                g.drawImage(sourceImage, xOffset, 0, null);
                xOffset += sourceImage.getWidth(null);
            }
            if (convolutionImage != null) {
                g.drawImage(convolutionImage, xOffset, 0, null);
                xOffset += convolutionImage.getWidth(null);
            }
            if (resultImage != null) {
                g.drawImage(resultImage, xOffset, 0, null);
            }
            update = false;
        }
        if (this.flame != null && this.fireImage != null) {
            int y = sourceImage.getHeight(null);
            Image flameImage = resize(flame, width, height - y);
            g.clearRect(0, y, width, height - y);
            g.drawImage(this.fireImage, 0, y, null);
            g.drawImage(flameImage, 0, y, null);
        }
    }

    @Override
    public void run() {
        if (this.flame != null) {
            if (this.flameThread != null && this.flameThread.isAlive()) {
                this.flame.animate = false;
                try {
                    this.flameThread.join(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            this.flameThread = new Thread(flame);
            this.flameThread.start();
        }
        if (timer != null) timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}