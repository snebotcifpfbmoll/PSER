package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.data.Statistics;

public interface ControlPanelDelegate {
    Statistics getStatistics();
    void didPress(ControlPanelAction action);
}
