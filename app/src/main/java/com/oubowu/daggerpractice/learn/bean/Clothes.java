package com.oubowu.daggerpractice.learn.bean;

/**
 * Created by Oubowu on 2017/11/28 11:34.
 */
public class Clothes {

    private Cloth cloth;

    public Clothes(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    @Override
    public String toString() {
        return cloth.getColor()+"衣服";
    }
}
