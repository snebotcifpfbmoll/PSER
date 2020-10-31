package com.serafinebot.pser.repaso.image;

import com.serafinebot.pser.repaso.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FireTests {
    private int width = 100;
    private int height = 100;
    private byte[] buffer;
    private final byte[][] conv = {{0, 0, 0}, {0, 0, 0}, {1, 1, 1}};
    private int ncolors = 3;
    private byte max_value = 99;
    private byte[][] colors;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        resizeBuffer();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        resizeBuffer();
    }

    public byte[] getBuffer() {
        if (buffer == null) buffer = new byte[width * height];
        return buffer;
    }

    public byte getMaxValue() {
        max_value += max_value % ncolors;
        return max_value;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public FireTests() {
    }

    public FireTests(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void resizeBuffer() {
        byte[] tmp_buffer = buffer.clone();
        buffer = new byte[width * height];
        System.arraycopy(tmp_buffer, 0, buffer, 0, buffer.length);
    }

    private void setColor(int index, byte red, byte green, byte blue) {
        byte[][] colors = getColors();
        colors[index][0] = blue;
        colors[index][1] = green;
        colors[index][2] = red;
    }

    public byte getRed(int index) {
        return getColor(index, 2);
    }

    public byte getGreen(int index) {
        return getColor(index, 1);
    }

    public byte getBlue(int index) {
        return getColor(index, 0);
    }

    public byte getColor(int index, int i) {
        byte[][] colors = getColors();
        index = Math.min(index, colors.length - 1);
        i = Math.min(i, 2);
        return colors[index][i];
    }

    public byte getColorForValue(int value, int color) {
        return getColor(value, color);
    }

    private byte incrementColor(byte color, byte increment) {
        int colorValue = Byte.toUnsignedInt(color);
        int incrementValue = Byte.toUnsignedInt(increment);
        int value = colorValue + incrementValue;
        return (byte) (Math.min(value, 255));
    }

    private byte[][] generateColors() {
        if (colors != null) return colors;

        byte max_value = getMaxValue();
        colors = new byte[max_value][3];

        int index = 0;
        byte color_diff = (byte) (max_value / ncolors);
        byte color_inc = (byte) (255 / color_diff);
        byte red = 0;
        byte green = 0;
        byte blue = 0;

        for (int i = 0; i < ncolors; i++) {
            for (int j = 0; j < color_diff; j++) {
                switch (i) {
                    case 0:
                        setColor(index++, red, green, blue);
                        red = incrementColor(red, color_inc);
                        break;
                    case 1:
                        setColor(index++, red, green, blue);
                        red = incrementColor(red, color_inc);
                        blue = incrementColor(blue, color_inc);
                        break;
                    case 2:
                        setColor(index++, red, green, blue);
                        blue = incrementColor(blue, color_inc);
                        green = incrementColor(green, color_inc);
                        break;
                    default:
                        System.out.println("unexpected");
                        break;
                }
            }
        }

        return colors;
    }

    public byte[][] getColors() {
        if (colors == null) return generateColors();
        return colors;
    }

    private void fillBottom() {
        byte max_value = getMaxValue();
        byte[] buffer = getBuffer();
        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byte random = (byte) (Math.random() * max_value);
                int index = (i * height) + j;
                buffer[index] = random;
            }
        }
    }

    private void process() {
        for (int i = height - 2; i > 0; i--) {
            byte[] res = ImageUtils.convolution(buffer, width, height, 1, 1, i, width - 1, height - 1, conv);
            if (res == null) break;
            System.arraycopy(res, 0, buffer, 0, buffer.length);
        }
    }

    public Image getImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] imageBuffer = ImageUtils.getByteArray(image);
        byte[] buffer = getBuffer();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 3; k++) {
                    int bufferIndex = (i * width) + j;
                    int imageIndex = (i * width * 3) + (j * 3) + k;
                    imageBuffer[imageIndex] = getColor(buffer[bufferIndex], k);
                }
            }
        }
        return image;
    }

    public static void main(String[] args) {
        FireTests fireTests = new FireTests(200, 200);
        fireTests.fillBottom();
        fireTests.process();
        Image image = fireTests.getImage();

        // display all of the images that we've processed
        int WINDOW_WIDTH = 800;
        int WINDOW_HEIGHT = 800;
        JFrame frame = new JFrame("Fire");
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
}