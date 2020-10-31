package com.serafinebot.pser.repaso.image;

public class Fire {
    private int width;
    private int height;
    private byte max_value = (byte)255;
    private byte[] buffer;

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
        return buffer;
    }

    public byte getMaxValue() {
        return max_value;
    }

    public void setMaxValue(byte max_value) {
        this.max_value = max_value;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public Fire() {}

    public Fire(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void resizeBuffer() {
        byte[] tmp_buffer = buffer.clone();
        buffer = new byte[width * height];
        System.arraycopy(tmp_buffer, 0, buffer, 0, buffer.length);
    }

    public void fillBottom() {
        for (int i = height - 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int random = (int) (Math.random() * max_value);
                if (random % 2 == 0) {
                    int index = (i * width) + j;
                    buffer[index] = max_value;
                }
            }
        }
    }
}