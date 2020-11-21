package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    public int width = 1;
    public int height = 1;
    public ControlPanelDelegate delegate = null;

    private static final int GRID_WIDTH = 3;
    private static final int SPARK_PERC_MIN = 0;
    private static final int SPARK_PERC_MAX = 100;
    private static final int SPARK_PERC_INIT = 10;

    // UI
    private final ArrayList<ConvolutionData> convolutionMatrixData = new ArrayList<>();
    private final JComboBox<String> comboBox = new JComboBox<>();
    private ConvolutionMatrixView matrixView = null;
    private JTextField sourceField = null;
    private JTextField kTextField = null;

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

        // Source image
        /*JLabel sourceLabel = new JLabel("Source file");
        sourceLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 0, 0);
        panel.add(sourceLabel, constraints);

        ylevel += constraints.gridheight;*/

        // File chooser
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = GRID_WIDTH;
        constraints.gridheight = constraints.gridwidth;
        constraints.insets = new Insets(0, 5, 0, 5);
        panel.add(fileChooser, constraints);

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
        JSlider sparkPercentage = new JSlider(SPARK_PERC_MIN, SPARK_PERC_MAX, SPARK_PERC_INIT);
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

        // Fire multipliers
        FireMultipliersView fireMult = new FireMultipliersView();
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

        JTextField divisorText = new JTextField("1.0");
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

        JTextField constantText = new JTextField("0.0");
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
        matrixView = new ConvolutionMatrixView(this.width, this.width);
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
        kTextField = new JTextField("1");
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
        scroll.setMinimumSize(new Dimension(width, height));
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
        if (delegate == null || sourceField == null) return;
        delegate.didApplyChanges(sourceField.getText(), getConvolutionData(), null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
