package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;

public interface ControlPanelDelegate {
    void didApplyChanges(String sourcePath, ConvolutionData convolutionData, FlameData flameData);
}
