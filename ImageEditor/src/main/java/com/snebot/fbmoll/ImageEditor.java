package com.snebot.fbmoll;

import com.snebot.fbmoll.view.ImageViewer;

import javax.swing.*;
import java.awt.*;

public class ImageEditor extends JFrame {
    private static final String testPath = "/Users/hystrix/Development/DAM/PSER/image-editor/src/main/resources/image-1920x1080-4.jpg";
    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 850;

    // UI
    //private ControlPanel controlPanel = null;
    //private ImageViewer imageViewer = null;

    public ImageEditor() {
        super();
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        ImageViewer imageViewer = new ImageViewer(200, 100);
        pane.add(imageViewer, constraints);
        /*controlPanel = new ControlPanel(width / 4, height);
        controlPanel.delegate = this;
        pane.add(controlPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        imageViewer = new ImageViewer(width - controlPanel.width, height);
        imageViewer.setBackground(Color.lightGray);
        pane.add(imageViewer, constraints);*/
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageEditor imageEditor = new ImageEditor();
            imageEditor.pack();
            imageEditor.setVisible(true);
        });
    }
}
