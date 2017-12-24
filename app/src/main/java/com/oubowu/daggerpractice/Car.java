package com.oubowu.daggerpractice;

import com.oubowu.daggerpractice.component.DaggerCarComponent;
import com.oubowu.daggerpractice.model.CarModel;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2017/11/21 11:49.
 */
public class Car {

    @Inject
    Engine mEngine;

    @Inject
    Seat mSeat;

    @Inject
    Wheel mWheel;

    public Car() {
        DaggerCarComponent.builder().carModel(new CarModel()).build().inject(this);
    }
}
