package com.snebot.fbmoll.imageeditor;

import com.snebot.fbmoll.data.ConvolutionData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageEditor extends JFrame implements ControlPanelDelegate {
    private static final String testPath = "/Users/hystrix/Development/DAM/PSER/image-editor/src/main/resources/image-1920x1080-4.jpg";
    private int width = 1400;
    private int height = 850;

    // UI
    private ImageViewer imageViewer = null;
    private ControlPanel controlPanel = null;

    public ImageEditor() {
        setup();
    }

    public ImageEditor(int width, int height) {
        this.width = width;
        this.height = height;
        setup();
    }

    public void setup() {
        if (imageViewer == null && controlPanel == null) {
            Container pane = getContentPane();
            pane.setLayout(new GridBagLayout());
            pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill = GridBagConstraints.BOTH;

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 0;
            constraints.weighty = 1;
            controlPanel = new ControlPanel(width / 4, height);
            controlPanel.delegate = this;
            pane.add(controlPanel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.weightx = 1;
            constraints.weighty = 1;
            imageViewer = new ImageViewer(width - controlPanel.width, height);
            imageViewer.setBackground(Color.lightGray);
            pane.add(imageViewer, constraints);

            pack();
            setVisible(true);
        }
    }

    /* Control Panel Delegate */
    @Override
    public void didApplyChanges(String sourcePath, ConvolutionData data) {
        if (imageViewer == null) return;
        try {
            if (sourcePath == null || sourcePath.equals("")) sourcePath = testPath;
            File file = new File(sourcePath);
            BufferedImage image = ImageIO.read(file);
            imageViewer.process(image, data);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageEditor imageEditor = new ImageEditor();
        });
    }
}