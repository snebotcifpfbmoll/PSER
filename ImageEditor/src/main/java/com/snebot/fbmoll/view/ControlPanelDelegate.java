package com.snebot.fbmoll.view;

import com.snebot.fbmoll.data.ConvolutionData;
import com.snebot.fbmoll.data.FlameData;

import java.io.File;

public interface ControlPanelDelegate {
    void didApplyChanges(File file, ConvolutionData convolutionData, FlameData flameData);
}
