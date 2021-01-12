package com.snebot.fbmoll.ui;

import javax.swing.*;

public class ControlPanel extends JPanel implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private final JTable table = new JTable();

    public ControlPanel() {}

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
    }
}
