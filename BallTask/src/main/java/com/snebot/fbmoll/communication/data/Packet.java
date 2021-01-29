package com.snebot.fbmoll.communication.data;

import com.snebot.fbmoll.graphic.Ball;

import java.io.Serializable;

public class Packet implements Serializable {
    private String greeting;
    private Ball ball;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Packet() {
    }

    public Packet(String greeting, Ball ball) {
        this.greeting = greeting;
        this.ball = ball;
    }
}
