package com.snebot.fbmoll.communication.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snebot.fbmoll.graphic.Ball;
import com.snebot.fbmoll.graphic.WallPosition;

import java.awt.*;
import java.io.Serializable;

public class Packet implements Serializable {
    public static final String BALL_TASK_GREETING = "BallTask";
    public static final String ACK_HEADER = "ACK";
    public static final String YES_HEADER = "YES";

    private String header;
    public int x = 0;
    public int y = 0;
    public int width = 40;
    public int height = 40;
    public int deltaX = 1;
    public int deltaY = 1;
    public int delay = 20;
    public WallPosition position = WallPosition.NONE;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBall(Ball ball) {
        this.x = ball.point.x;
        this.y = ball.point.y;
        this.width = ball.size.width;
        this.height = ball.size.height;
        this.deltaX = ball.deltaX;
        this.deltaY = ball.deltaY;
        this.delay = ball.delay;
    }

    @JsonIgnore
    public Ball getBall() {
        Ball ball = new Ball();
        ball.point = new Point(this.x, this.y);
        ball.size = new Dimension(this.width, this.height);
        ball.deltaX = this.deltaX;
        ball.deltaY = this.deltaY;
        ball.delay = this.delay;
        return ball;
    }

    public Packet() {
    }

    public Packet(String header) {
        this.header = header;
    }

    public Packet(Ball ball, WallPosition position) {
        this.position = position;
        setBall(ball);
    }

    public static Packet headerACK() {
        return new Packet(ACK_HEADER);
    }

    public static Packet headerYES() {
        return new Packet(YES_HEADER);
    }
}
