package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.data.Statistics;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

public class ControlPanel extends JPanel implements Runnable, TableModel {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private final JTable table = new JTable(this);
    private final JScrollPane tableScroll = new JScrollPane(table);
    private Statistics statistics = new Statistics(5, 6, 7, 8);

    public ControlPanel() {
        super();
        setup();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        int ylevel = 0;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        this.tableScroll.setMaximumSize(new Dimension(100, 100));
        add(this.tableScroll, constraints);
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
    }

    @Override
    public int getRowCount() {
        if (this.statistics == null) return 0;
        return this.statistics.toArray().length;
    }

    @Override
    public int getColumnCount() {
        if (this.statistics == null) return 0;
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.statistics.toArray()[rowIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
}
