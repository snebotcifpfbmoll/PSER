package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    public int width = 1;
    public int height = 1;
    public ControlPanelDelegate delegate = null;

    // UI
    private final ArrayList<ConvolutionData> convolutionMatrixData = new ArrayList<>();
    private final JComboBox<String> comboBox = new JComboBox<>();
    private ConvolutionMatrixView matrixView = null;
    private JTextField sourceField = null;
    private JTextField kTextField = null;

    public ControlPanel() {
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
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.1;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        int ylevel = 0;

        // Source image
        JLabel sourceLabel = new JLabel("Source path: ");
        sourceLabel.setHorizontalAlignment(JLabel.RIGHT);
        sourceLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 0);
        add(sourceLabel, constraints);

        // Source Text Field
        sourceField = new JTextField();
        constraints.gridx = 1;
        constraints.insets = new Insets(5, 0, 5, 10);
        add(sourceField, constraints);

        ylevel += constraints.gridheight;

        // Convolution Title
        JLabel convolutionTitle = new JLabel("Convolution: ");
        convolutionTitle.setHorizontalAlignment(JLabel.RIGHT);
        convolutionTitle.setVerticalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.insets = new Insets(5, 10, 5, 0);
        add(convolutionTitle, constraints);

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
        constraints.insets = new Insets(5, 0, 5, 10);
        add(comboBox, constraints);

        // Convolution Matrix View
        matrixView = new ConvolutionMatrixView(this.width, this.width);
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 2;
        constraints.gridheight = constraints.gridwidth;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 10, 5, 10);
        add(matrixView, constraints);

        ylevel += constraints.gridheight;

        // K Label
        JLabel kLabel = new JLabel("K: ");
        kLabel.setHorizontalAlignment(JLabel.RIGHT);
        kLabel.setVerticalAlignment(JLabel.CENTER);
        constraints.gridy = ylevel;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 0);
        add(kLabel, constraints);

        // K Text
        kTextField = new JTextField("1");
        kTextField.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.insets = new Insets(5, 0, 5, 10);
        add(kTextField, constraints);

        ylevel += constraints.gridheight;

        // Apply Button
        JButton applyButton = new JButton("Apply");
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 2;
        add(applyButton, constraints);

        applyButton.addActionListener(actionEvent -> {
            didPressApplyButton();
        });

        ylevel += constraints.gridheight;

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
