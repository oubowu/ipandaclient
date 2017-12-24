package com.oubowu.daggerpractice.learn.bean;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2017/11/28 11:24.
 */
public class Shoe {

    @Inject
    public Shoe() {
    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
