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

    public FlameData(ColorPalette colorPalette, int sparkPercentage, double mult_left, double mult, double mult_right, double mult_bottom, double mult_bottom_left, double mult_bottom_right, double divisor, double constant) {
        this.colorPalette = colorPalette;
        this.sparkPercentage = sparkPercentage;
        this.mult_left = mult_left;
        this.mult = mult;
        this.mult_right = mult_right;
        this.mult_bottom = mult_bottom;
        this.mult_bottom_left = mult_bottom_left;
        this.mult_bottom_right = mult_bottom_right;
        this.divisor = divisor;
        this.constant = constant;
    }
}
