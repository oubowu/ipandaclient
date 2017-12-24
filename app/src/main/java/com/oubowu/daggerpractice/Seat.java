package com.oubowu.daggerpractice;

import android.util.Log;

/**
 * 车座
 */
public class Seat {
    private Leather leather;

    //默认无车套车座
    public Seat(){
        Log.d(Config.TAG,"new Seat()");
    }
    //提供有车套车座
    public Seat(String str){
        Log.d(Config.TAG,str);
    }

    public Seat(Leather leather){
        this.leather = leather;
        Log.d(Config.TAG,"new Seat(Leather)");
    }

}