package com.example.androidtest1;

public enum RoundType {
    WORK(String.valueOf(R.color.red)), REST(String.valueOf(R.color.blue));

    private final String color;

    RoundType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
