package com.oubowu.daggerpractice.learn.bean;

/**
 * Created by Oubowu on 2017/11/28 10:56.
 */
public class Cloth {

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "布料";
    }
}
