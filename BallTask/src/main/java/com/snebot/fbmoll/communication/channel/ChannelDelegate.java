package com.snebot.fbmoll.communication.channel;

import com.snebot.fbmoll.graphic.Ball;
import com.snebot.fbmoll.graphic.WallPosition;

public interface ChannelDelegate {
    void didReceiveBall(Ball ball, WallPosition originalWall);
}
