package com.snebot.fbmoll.communication.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snebot.fbmoll.graphic.Ball;

import java.awt.*;
import java.io.Serializable;

public class Packet implements Serializable {
    private String greeting;
    public int x = 0;
    public int y = 0;
    public int width = 40;
    public int height = 40;
    public int deltaX = 1;
    public int deltaY = 1;
    public int delay = 20;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
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

    public Packet(String greeting) {
        this.greeting = greeting;
    }

    public Packet(Ball ball) {
        setBall(ball);
    }
}
