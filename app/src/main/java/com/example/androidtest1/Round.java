package com.example.androidtest1;

public class Round {

    private RoundType type;
    private int duration;

    public Round(int duration, RoundType type) {
        this.duration = duration;
        this.type = type;
    }

    public RoundType getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }
}
