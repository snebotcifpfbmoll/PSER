package com.snebot.fbmoll.imageeditor.fire;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ColorPalette {
    private int size;
    private ArrayList<Color> palette = new ArrayList<>();
    private HashMap<Integer, Color> colorMap = new HashMap<>();

    private ColorPalette() { }

    public ColorPalette(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxValue() {
        return size - 1;
    }

    public List<Color> getPalette() {
        return palette;
    }

    public HashMap<Integer, Color> getColorMap() {
        return colorMap;
    }

    public Integer[] getColorMapIndicies() {
        Integer[] indexes = getColorMap().keySet().toArray(new Integer[0]);
        Arrays.sort(indexes);
        return indexes;
    }

    public void addColor(Color color, int index) {
        if (index < size && !colorMap.containsKey(index)) colorMap.put(index, color);
    }

    public void removeColor(int key) {
        colorMap.remove(key);
    }

    public Color getColor(int index) {
        Color color = null;
        if (index < palette.size()) color = palette.get(index);
        return color;
    }

    public void generatePalette() {
        palette.clear();
        Integer[] indicies = getColorMapIndicies();
        int ngradients = colorMap.size() - 1;

        float baseRed = 0;
        float baseGreen = 0;
        float baseBlue = 0;
        float baseAlpha = 0;

        for (int i = 0; i < ngradients; i++) {
            int sizeDiff = indicies[i + 1] - indicies[i];

            Color baseColor = colorMap.get(indicies[i]);
            baseRed = baseColor.getRed();
            baseGreen = baseColor.getGreen();
            baseBlue = baseColor.getBlue();
            baseAlpha = baseColor.getAlpha();

            Color nextColor = colorMap.get(indicies[i + 1]);
            float nextRed = nextColor.getRed();
            float nextGreen = nextColor.getGreen();
            float nextBlue = nextColor.getBlue();
            float nextAlpha = nextColor.getAlpha();

            float redDiff = nextRed - baseRed;
            float greenDiff = nextGreen - baseGreen;
            float blueDiff = nextBlue - baseBlue;
            float alphaDiff = nextAlpha - baseAlpha;

            float redInc = redDiff / sizeDiff;
            float greenInc = greenDiff / sizeDiff;
            float blueInc = blueDiff / sizeDiff;
            float alphaInc = alphaDiff / sizeDiff;

            for (int j = 0; j < sizeDiff; j++) {
                palette.add(new Color((int)baseRed, (int)baseGreen, (int)baseBlue, (int)baseAlpha));
                baseRed += redInc;
                baseGreen += greenInc;
                baseBlue += blueInc;
                baseAlpha += alphaInc;
            }
        }

        palette.add(new Color((int)baseRed, (int)baseGreen, (int)baseBlue, (int)baseAlpha));
    }
}
