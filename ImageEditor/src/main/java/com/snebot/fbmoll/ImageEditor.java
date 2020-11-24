package com.snebot.fbmoll;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;
import com.snebot.fbmoll.view.ControlPanel;
import com.snebot.fbmoll.view.ControlPanelDelegate;
import com.snebot.fbmoll.view.ImageViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageEditor extends JFrame implements ControlPanelDelegate {
    private static final String TEST_PATH = "/Users/hystrix/Development/DAM/PSER/image-editor/src/main/resources/image-1920x1080-4.jpg";
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 850;

    // UI
    private ImageViewer imageViewer = null;
    private Thread imageViewerThread = null;

    public ImageEditor() {
        super();
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;

        ControlPanel controlPanel = new ControlPanel(WINDOW_WIDTH / 4, WINDOW_HEIGHT * 2);
        controlPanel.delegate = this;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.gridwidth = 4;
        pane.add(controlPanel, constraints);

        imageViewer = new ImageViewer(WINDOW_WIDTH - controlPanel.width, WINDOW_HEIGHT);
        imageViewer.setBackground(Color.lightGray);
        constraints.gridx = constraints.gridwidth;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.gridwidth = 6;
        pane.add(imageViewer, constraints);
    }

    @Override
    public void didApplyChanges(File file, ConvolutionData convolutionData, FlameData flameData) {
        if (imageViewer == null) return;
        try {
            BufferedImage image = ImageIO.read(file);
            imageViewer.process(image, convolutionData, flameData);
            imageViewerThread = new Thread(imageViewer);
            imageViewerThread.start();
        } catch (Exception e) {
            System.out.println("main - Error: " + e.getMessage() + e.getClass());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageEditor imageEditor = new ImageEditor();
            imageEditor.pack();
            imageEditor.setVisible(true);
        });
    }
}
