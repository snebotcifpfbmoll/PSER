package com.snebot.fbmoll.imageeditor;

import com.snebot.fbmoll.data.ConvolutionData;

public interface ControlPanelDelegate {
    void didApplyChanges(String sourcePath, ConvolutionData data);
}
