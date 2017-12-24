package com.oubowu.daggerpractice.weatherdemo.di.component;

import com.oubowu.daggerpractice.learn.scope.PerActivity;
import com.oubowu.daggerpractice.weatherdemo.di.module.WeatherPresenterModule;
import com.oubowu.daggerpractice.weatherdemo.model.WeatherModel;
import com.oubowu.daggerpractice.weatherdemo.view.WeatherActivity;

import dagger.Component;

/**
 * Created by Oubowu on 2017/12/7 15:52.
 */
@PerActivity
@Component(modules = {WeatherPresenterModule.class})
public interface WeatherPresenterComponent {

    void inject(WeatherActivity activity);

    void inject(WeatherModel weatherModel);

}
