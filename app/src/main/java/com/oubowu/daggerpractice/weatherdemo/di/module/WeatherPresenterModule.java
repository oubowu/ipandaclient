package com.oubowu.daggerpractice.weatherdemo.di.module;

import com.oubowu.daggerpractice.weatherdemo.contract.WeatherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/7 15:49.
 */
@Module
public class WeatherPresenterModule {

    private WeatherContract.View mView;

    public WeatherPresenterModule(WeatherContract.View view) {
        mView = view;
    }

    @Provides
    public WeatherContract.View provideView(){
        return mView;
    }

}
