package com.snebot.fbmoll.data;

import com.snebot.fbmoll.view.fire.ColorPalette;

public class FlameData {
    public ColorPalette colorPalette = new ColorPalette(0);
    public int sparkPercentage = 0;
    public double mult_left = 0.0D;
    public double mult = 0.0D;
    public double mult_right = 0.0D;
    public double mult_bottom = 0.0D;
    public double mult_bottom_left = 0.0D;
    public double mult_bottom_right = 0.0D;
    public double divisor = 1.0D;
    public double constant = 0.0D;

    public FlameData() {
    }

    public FlameData(ColorPalette colorPalette, int sparkPercentage) {
        this.colorPalette = colorPalette;
        this.sparkPercentage = sparkPercentage;
    }
}
