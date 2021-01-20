package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.data.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControlPanel extends JPanel implements Runnable {
    private int vWidth = 1;
    private int vHeight = 1;

    private static final int SMALL_INSET = 5;

    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private Statistics statistics = new Statistics(5, 6, 7, 8);
    private DefaultTableModel model = new DefaultTableModel();

    private ControlPanel() {
    }

    public ControlPanel(int width, int height) {
        super();
        this.vWidth = width;
        this.vHeight = height;
        setup();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        int ylevel = 0;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 2;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(SMALL_INSET, SMALL_INSET, SMALL_INSET, SMALL_INSET);
        constraints.anchor = GridBagConstraints.PAGE_START;

        JTable table = new JTable(this.model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setMinimumSize(new Dimension(100, 100));
        scroll.setMaximumSize(new Dimension(150, 200));
        add(scroll, constraints);
        updateTable();

        constraints.gridwidth = 1;
        ylevel += constraints.gridheight;
        constraints.gridy = ylevel;

        JButton play = new JButton("Play");
        add(play, constraints);

        constraints.gridx = 1;

        JButton pause = new JButton("Pause");
        add(pause, constraints);

        ylevel += constraints.gridheight;
        constraints.gridy = ylevel;
        constraints.gridx = 0;

        JButton stop = new JButton("Stop");
        add(stop, constraints);

        play.addActionListener(e -> {
        });
    }

    public void updateTable() {
        if (this.statistics == null) return;
        String[][] data = this.statistics.toArray();
        this.model.addColumn("Name");
        this.model.addColumn("Count");
        for (String[] rowData : data) {
            this.model.addRow(rowData);
        }
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.vWidth, this.vHeight);
    }
}
