package com.oubowu.daggerpractice.model;

import com.oubowu.daggerpractice.Engine;
import com.oubowu.daggerpractice.Leather;
import com.oubowu.daggerpractice.Seat;
import com.oubowu.daggerpractice.Wheel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/11/21 11:55.
 */
@Module
public class CarModel {

    @Provides
    public Engine provideEngine(){
        return new Engine();
    }

    @Provides
    public Seat provideSeat(Leather leather){
        return new Seat(leather);
    }

    @Provides
    public Leather provideLeather(){
        return new Leather();
    }

    @Provides
    public Wheel provideWheel(){
        return new Wheel();
    }

}
