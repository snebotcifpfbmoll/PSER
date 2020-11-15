package com.snebot.fbmoll.imageeditor;

import com.snebot.fbmoll.data.ConvolutionData;

public interface ControlPanelDelegate {
    public void didApplyChanges(String sourcePath, ConvolutionData data);
}
