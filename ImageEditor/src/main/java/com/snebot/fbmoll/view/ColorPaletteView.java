package com.snebot.fbmoll.view;

import com.snebot.fbmoll.view.fire.ColorPalette;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Map;

public class ColorPaletteView extends JComponent implements TableModel {
    private int viewWidth = 0;
    private int viewHeight = 0;
    private final JButton removeButton = new JButton("-");
    private final JButton addButton = new JButton("+");
    private final JTextField numColors = new JTextField("0");
    private final JTable table = new JTable(this);
    private final JScrollPane tableScroll = new JScrollPane(table);
    private ColorPalette colorPalette = new ColorPalette();

    private static final String[] TABLE_COLUMN_NAMES = new String[]{"Red", "Green", "Blue", "Alpha", "Index"};
    private int ylevel = 0;

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public ColorPalette getColorPalette() {
        this.colorPalette.generatePalette();
        return colorPalette;
    }

    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public ColorPaletteView() {
        super();
        setup();
    }

    public ColorPaletteView(int viewWidth, int viewHeight, ColorPalette colorPalette) {
        super();
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.colorPalette = colorPalette;
        setup();
    }

    public ColorPaletteView(int width, int height) {
        super();
        this.viewWidth = width;
        this.viewHeight = height;
        setup();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 5, 0, 0);
        constraints.anchor = GridBagConstraints.LINE_START;
        this.numColors.setHorizontalAlignment(JTextField.CENTER);
        this.add(this.numColors, constraints);

        constraints.gridx = 1;
        constraints.gridy = ylevel;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        this.add(this.removeButton, constraints);
        this.removeButton.addActionListener(e -> {
            int row = this.table.getSelectedRow();
            int nrows = colorPalette.getColorMapIndicies().length;
            if (row >= 0 && row < nrows) {
                int index = (int) getValueAt(row, TABLE_COLUMN_NAMES.length - 1);
                this.colorPalette.removeColor(index);
                this.table.revalidate();
            }
        });

        constraints.gridx = 2;
        constraints.gridy = ylevel;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 0, 20);
        constraints.anchor = GridBagConstraints.LINE_END;
        this.add(this.addButton, constraints);
        this.addButton.addActionListener(e -> {
            this.colorPalette.addColor(new Color(0, 0, 0, 0), 0);
            this.table.revalidate();
        });

        ylevel += constraints.gridheight;

        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 3;
        constraints.gridheight = 10;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 0, 0);
        this.tableScroll.setMinimumSize(new Dimension(this.viewWidth, this.viewHeight));
        this.add(this.tableScroll, constraints);
    }

    @Override
    public int getRowCount() {
        return colorPalette.getColorMapIndicies().length;
    }

    @Override
    public int getColumnCount() {
        return TABLE_COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return TABLE_COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map<Integer, Color> map = colorPalette.getColorMap();
        Object key = map.keySet().toArray()[rowIndex];
        if (columnIndex == TABLE_COLUMN_NAMES.length - 1) {
            return key;
        } else {
            Color color = map.get(key);
            switch (columnIndex) {
                case 0:
                    return color.getRed();
                case 1:
                    return color.getGreen();
                case 2:
                    return color.getBlue();
                case 3:
                    return color.getAlpha();
            }
        }
        return 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        int newValue = 0;
        try {
            newValue = Integer.parseInt((String)aValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Map<Integer, Color> map = colorPalette.getColorMap();
        Integer key = (Integer) map.keySet().toArray()[rowIndex];
        Color color = map.get(key);
        int index = key;

        if (columnIndex == TABLE_COLUMN_NAMES.length - 1) {
            index = newValue;
        } else {
            switch (columnIndex) {
                case 0:
                    color = new Color(newValue, color.getGreen(), color.getBlue(), color.getAlpha());
                    break;
                case 1:
                    color = new Color(color.getRed(), newValue, color.getBlue(), color.getAlpha());
                    break;
                case 2:
                    color = new Color(color.getRed(), color.getGreen(), newValue, color.getAlpha());
                    break;
                case 3:
                    color = new Color(color.getRed(), color.getGreen(), color.getBlue(), newValue);
                    break;
            }
        }

        colorPalette.removeColor(key);
        colorPalette.addColor(color, index);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.viewWidth, this.viewHeight);
    }
}