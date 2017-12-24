package com.oubowu.daggerpractice.component;

import com.oubowu.daggerpractice.Car;
import com.oubowu.daggerpractice.model.CarModel;

import dagger.Component;

/**
 * Created by Oubowu on 2017/11/21 14:10.
 */
@Component(modules = CarModel.class)
public interface CarComponent {

    public void inject(Car car);

}
