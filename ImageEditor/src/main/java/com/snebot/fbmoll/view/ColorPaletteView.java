package com.snebot.fbmoll.view;

import com.snebot.fbmoll.view.fire.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorPaletteView extends JComponent {
    private int viewWidth = 0;
    private int viewHeight = 0;
    private final JButton removeButton = new JButton("-");
    private final JButton addButton = new JButton("+");
    private final JTextField numColors = new JTextField("0");
    private final JScrollPane tableScroll = new JScrollPane();
    private JTable table = null;
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
            Integer[] indicies = colorPalette.getColorMapIndicies();
            if (row >= 0 && row < indicies.length) {
                Integer key = indicies[row];
                colorPalette.removeColor(key);
                updateColorPalette();
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
            updateColorPalette();
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

        // Post UI setup
        updateColorPalette();
    }

    public void updateColorPalette() {
        ArrayList<Integer[]> values = new ArrayList<>();
        this.colorPalette.getColorMap().forEach((index, color) -> {
            Integer[] element = new Integer[]{
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    color.getAlpha(),
                    index
            };
            values.add(element);
        });

        table = new JTable(values.toArray(new Integer[0][0]), TABLE_COLUMN_NAMES);
        table.setFillsViewportHeight(true);
        this.tableScroll.setViewportView(table);
        this.numColors.setText(String.valueOf(this.colorPalette.getSize()));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.viewWidth, this.viewHeight);
    }
}