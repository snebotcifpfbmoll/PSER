package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;
import com.snebot.fbmoll.view.fire.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    public int width = 1;
    public int height = 1;
    public ControlPanelDelegate delegate = null;

    private static final int GRID_WIDTH = 3;
    private static final int SPARK_PERC_MIN = 0;
    private static final int SPARK_PERC_MAX = 100;
    private static final int SPARK_PERC_INIT = 10;
    private static final int TEMP_THRESH_MIN = 0;
    private static final int TEMP_THRESH_MAX = 255;
    private static final int TEMP_THRESH_INIT = 128;

    private static final FlameData DEFAULT_FLAME_DATA = new FlameData();
    static {
        final ColorPalette cp = new ColorPalette(256);
        cp.addColor(new Color(0, 0, 0, 0), 0);
        cp.addColor(new Color(255, 50, 50, 64), 64);
        cp.addColor(new Color(255, 255, 120, 255), 80);
        cp.addColor(new Color(240, 115, 120, 255), 100);
        cp.addColor(new Color(80, 110, 190, 192), 128);
        cp.addColor(new Color(90, 165, 235, 128), 165);
        cp.addColor(new Color(255, 255, 255, 255), 255);
        cp.generatePalette();
        DEFAULT_FLAME_DATA.colorPalette = cp;
        DEFAULT_FLAME_DATA.sparkPercentage = SPARK_PERC_INIT;
        DEFAULT_FLAME_DATA.mult_left = 1.2D;
        DEFAULT_FLAME_DATA.mult = 1.5D;
        DEFAULT_FLAME_DATA.mult_right = 1.2D;
        DEFAULT_FLAME_DATA.mult_bottom_left = 0.7D;
        DEFAULT_FLAME_DATA.mult_bottom = 0.738D;
        DEFAULT_FLAME_DATA.mult_bottom_right = 0.7D;
        DEFAULT_FLAME_DATA.divisor = 5.999D;
        DEFAULT_FLAME_DATA.constant = 0.4D;
    }

    // UI
    private final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
    private ColorPaletteView paletteView = null;
    private final JSlider sparkPercentage = new JSlider(SPARK_PERC_MIN, SPARK_PERC_MAX, DEFAULT_FLAME_DATA.sparkPercentage);
    private final JSlider temperatureThreshold = new JSlider(TEMP_THRESH_MIN, TEMP_THRESH_MAX, TEMP_THRESH_INIT);
    private final FireMultipliersView fireMult = new FireMultipliersView();
    private final JTextField divisorText = new JTextField(String.valueOf(DEFAULT_FLAME_DATA.divisor));
    private final JTextField constantText = new JTextField(String.valueOf(DEFAULT_FLAME_DATA.constant));
    private final ArrayList<ConvolutionData> convolutionMatrixData = new ArrayList<>();
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final ConvolutionMatrixView matrixView = new ConvolutionMatrixView();
    private final JTextField kTextField = new JTextField("1");

    private ControlPanel() {
        super();
        setup();
    }

    public ControlPanel(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setup();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width, height));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.1;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        int ylevel = 0;

        // File chooser
        JLabel fileLabel = new JLabel("Source file");
        fileLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(fileLabel, constraints);

        ylevel += constraints.gridheight;

        JTextArea filePath = new JTextArea();
        filePath.setLineWrap(true);
        filePath.setWrapStyleWord(true);
        filePath.setEnabled(false);
        constraints.gridy = ylevel;
        panel.add(filePath, constraints);

        fileChooser.addActionListener(e -> {
            File file = fileChooser.getSelectedFile();
            filePath.setText(file.getAbsolutePath());
        });

        ylevel += constraints.gridheight;

        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = constraints.gridwidth;
        constraints.insets = new Insets(0, 5, 0, 5);
        panel.add(fileChooser, constraints);

        ylevel += constraints.gridheight;

        // Color palette
        JLabel colorPaletteLabel = new JLabel("Color Palette");
        colorPaletteLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(colorPaletteLabel, constraints);

        ylevel += constraints.gridheight;

        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = 6;
        constraints.insets = new Insets(0, 0, 0, 0);
        this.paletteView = new ColorPaletteView(this.width, this.width / 2, DEFAULT_FLAME_DATA.colorPalette);
        panel.add(paletteView, constraints);

        ylevel += constraints.gridheight;

        // Spark percentage
        JLabel fireLabel = new JLabel("Spark percentage");
        fireLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(fireLabel, constraints);

        ylevel += constraints.gridheight;

        // Spark percentage
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, 0, 5, 0);
        panel.add(sparkPercentage, constraints);

        JLabel sparkPercentageLabel = new JLabel(SPARK_PERC_INIT + "%");
        fireLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = constraints.gridwidth;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - constraints.gridwidth;
        constraints.gridheight = 1;
        panel.add(sparkPercentageLabel, constraints);

        sparkPercentage.addChangeListener(e -> {
            sparkPercentageLabel.setText(sparkPercentage.getValue() + "%");
        });

        ylevel += constraints.gridheight;

        // Temperature threshold
        JLabel tempLabel = new JLabel("Temperature threshold");
        tempLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(tempLabel, constraints);

        ylevel += constraints.gridheight;

        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, 0, 5, 0);
        panel.add(temperatureThreshold, constraints);

        JLabel tempThresholdLabel = new JLabel(String.valueOf(TEMP_THRESH_INIT));
        tempLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = constraints.gridwidth;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - constraints.gridwidth;
        constraints.gridheight = 1;
        panel.add(tempThresholdLabel, constraints);

        temperatureThreshold.addChangeListener(e -> {
            tempThresholdLabel.setText(String.valueOf(temperatureThreshold.getValue()));
        });

        ylevel += constraints.gridheight;

        // Fire multipliers
        fireMult.setData(DEFAULT_FLAME_DATA);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - 1;
        constraints.gridheight = constraints.gridwidth;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(fireMult, constraints);

        ylevel += constraints.gridheight;

        // Divisor
        JLabel divisorLabel = new JLabel("Divisor: ");
        divisorLabel.setHorizontalAlignment(JLabel.RIGHT);
        divisorLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        panel.add(divisorLabel, constraints);

        divisorText.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        panel.add(divisorText, constraints);

        ylevel += constraints.gridheight;

        // Constant
        JLabel constantLabel = new JLabel("Constant: ");
        constantLabel.setHorizontalAlignment(JLabel.RIGHT);
        constantLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        panel.add(constantLabel, constraints);

        constantText.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        panel.add(constantText, constraints);

        ylevel += constraints.gridheight;

        /* Convoluiton */
        // Convolution Title
        JLabel convolutionTitle = new JLabel("Convolution: ");
        convolutionTitle.setHorizontalAlignment(JLabel.RIGHT);
        convolutionTitle.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 10, 5, 0);
        panel.add(convolutionTitle, constraints);

        ylevel += constraints.gridheight;

        // Convolution Matrix Data
        convolutionMatrixData.add(new ConvolutionData("None", new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, 1));
        convolutionMatrixData.add(new ConvolutionData("Sharpen", new int[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}, 1));
        convolutionMatrixData.add(new ConvolutionData("Blur", new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 9));
        convolutionMatrixData.add(new ConvolutionData("Edge detection", new int[][]{{0, 1, 0}, {1, -4, 1}, {0, 1, 0}}, 1));

        // Convolution Combo Box
        for (ConvolutionData data : convolutionMatrixData) comboBox.addItem(data.name);
        comboBox.addActionListener(actionEvent -> {
            didSelectConvolution();
        });
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 0, 5, 10);
        panel.add(comboBox, constraints);

        // Convolution Matrix View
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH - 1;
        constraints.gridheight = constraints.gridwidth;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 10, 5, 10);
        panel.add(matrixView, constraints);

        ylevel += constraints.gridheight;

        // K Label
        JLabel kLabel = new JLabel("K: ");
        kLabel.setHorizontalAlignment(JLabel.RIGHT);
        kLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 0, 5, 0);
        panel.add(kLabel, constraints);

        // K Text
        kTextField.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(5, 0, 5, 10);
        panel.add(kTextField, constraints);

        ylevel += constraints.gridheight;

        // Apply Button
        JButton applyButton = new JButton("Apply");
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridwidth = GRID_WIDTH - 1;
        panel.add(applyButton, constraints);

        applyButton.addActionListener(actionEvent -> {
            didPressApplyButton();
        });

        ylevel += constraints.gridheight;

        // Scroll pane
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setMinimumSize(new Dimension(width + 20, height));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.insets = new Insets(0, 0, 0,0);
        add(scroll, constraints);

        // Post UI setup
        comboBox.setSelectedIndex(0);
    }

    public ConvolutionData getConvolutionData() {
        ConvolutionData data = null;
        try {
            if (matrixView != null && kTextField != null) {
                int k = Integer.parseInt(kTextField.getText());
                if (k == 0) {
                    kTextField.setText("1");
                    k = 1;
                }
                data = new ConvolutionData("", matrixView.getMatrix(), k);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    public void didSelectConvolution() {
        int index = comboBox.getSelectedIndex();
        if (index >= convolutionMatrixData.size() || matrixView == null || kTextField == null) return;
        ConvolutionData data = convolutionMatrixData.get(index);
        matrixView.setMatrix(data.matrix);
        kTextField.setText(String.valueOf(data.k));
    }

    public void didPressApplyButton() {
        File file = fileChooser.getSelectedFile();
        FlameData flameData = fireMult.getData();
        flameData.colorPalette = paletteView.getColorPalette();
        flameData.sparkPercentage = this.sparkPercentage.getValue();
        ConvolutionData convolutionData = getConvolutionData();
        convolutionData.temperatureThreshold = this.temperatureThreshold.getValue();

        try {
            flameData.divisor = Double.parseDouble(divisorText.getText());
            if (flameData.divisor == 0.0D) {
                divisorText.setText("1.0");
                flameData.divisor = 1.0;
            }
            flameData.constant = Double.parseDouble(constantText.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (delegate == null || file == null) return;
        delegate.didApplyChanges(file, convolutionData, flameData);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
        //return new Dimension(width, height);
    }
}
