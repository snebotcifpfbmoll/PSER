package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;
import com.snebot.fbmoll.view.fire.ColorPalette;
import com.snebot.fbmoll.view.fire.Flame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ImageViewer extends JPanel {
    private int width = 1;
    private int height = 1;
    private Thread flameThread = null;
    private FlameView flameView = null;

    // UI
    private final JLabel source = new JLabel(new ImageIcon());
    private final JLabel convolution = new JLabel(new ImageIcon());
    private final JLabel result = new JLabel(new ImageIcon());

    public FlameView getFlameView() {
        return flameView;
    }

    public void setFlameView(FlameView flameView) {
        this.flameView = flameView;
    }

    public ImageViewer() {
        super();
    }

    public ImageViewer(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 0.4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(source, constraints);

        constraints.gridx = 1;
        add(convolution, constraints);

        constraints.weighty = 0.6;
        constraints.gridx = 0;
        constraints.gridy = constraints.gridheight;
        constraints.gridwidth = 2;
        result.setBackground(Color.BLACK);
        result.setOpaque(true);
        add(result, constraints);
        /*flameView = new FlameView();
        add(flameView, constraints);*/
    }

    public void start() {
        if (flameView == null) return;
        if (flameThread == null) flameThread = new Thread(flameView);
        flameThread.start();
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

    private Image createImageFromTempMap(int width, int height, ArrayList<Integer[]> map) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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

    public void process(BufferedImage image, ConvolutionData data) {
        Dimension dimen = resize(image.getWidth(), image.getHeight(), source.getWidth(), source.getHeight());
        Image sourceImage = image.getScaledInstance(dimen.width, dimen.height, Image.SCALE_DEFAULT);
        source.setIcon(new ImageIcon(sourceImage));

        Image convolutionResult = convolution(image, data.matrix, data.k);
        dimen = resize(convolutionResult.getWidth(null), convolutionResult.getHeight(null), convolution.getWidth(), convolution.getHeight());
        Image convolutionImage = convolutionResult.getScaledInstance(dimen.width, dimen.height, Image.SCALE_DEFAULT);
        convolution.setIcon(new ImageIcon(convolutionImage));

        ArrayList<Integer[]> map = createTemperatureMap(convolutionResult, 128);
        Image tempMapImage = createImageFromTempMap(convolutionResult.getWidth(null), convolutionResult.getHeight(null), map);
        dimen = resize(tempMapImage.getWidth(null), tempMapImage.getHeight(null), result.getWidth(), result.getHeight());
        Image resultImage = tempMapImage.getScaledInstance(dimen.width, dimen.height, Image.SCALE_DEFAULT);
        result.setIcon(new ImageIcon(resultImage));

        /*ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 50, 50, 64), 64);
        cp.addColor(new Color(255, 255, 120, 255), 80);
        cp.addColor(new Color(240, 115, 120, 255), 100);
        cp.addColor(new Color(80, 110, 190, 192), 128);
        cp.addColor(new Color(90, 165, 235, 128), 165);
        cp.addColor(new Color(255, 255, 255, 255), 255);
        cp.generatePalette();
        FlameData flameData = new FlameData(cp, 30);
        flameData.mult_left = 1.2D;
        flameData.mult = 1.5D;
        flameData.mult_right = 1.2D;
        flameData.mult_bottom_left = 0.7D;
        flameData.mult_bottom = 0.738D;
        flameData.mult_bottom_right = 0.7D;
        flameData.divisor = 5.995D;
        flameData.constant = 0.37D;

        Flame flame = new Flame(dimen.width, dimen.height, flameData, map);
        flameView.setFlame(flame);
        start();*/
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
