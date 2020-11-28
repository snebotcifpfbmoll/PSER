package com.snebot.fbmoll.util;

import java.awt.*;

public class ImageUtils {
    private ImageUtils() {}

    public static Dimension resize(int width, int height, int targetWidth, int targetHeight) {
        double wRatio = (double) targetWidth / width;
        double hRatio = (double) targetHeight / height;
        double ratio = Math.min(wRatio, hRatio);
        int newWidth = (int) (width * ratio);
        int newHeight = (int) (height * ratio);
        return new Dimension(newWidth, newHeight);
    }

    public static Image resize(Image image, int width, int height) {
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        Dimension dimen = resize(imageWidth, imageHeight, width, height);
        return image.getScaledInstance(dimen.width, dimen.height, Image.SCALE_DEFAULT);
    }
}
