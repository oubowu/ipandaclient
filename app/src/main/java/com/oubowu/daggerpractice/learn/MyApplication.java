package com.oubowu.daggerpractice.learn;

import android.app.Application;

import com.oubowu.daggerpractice.learn.component.BaseComponent;
import com.oubowu.daggerpractice.learn.component.DaggerBaseComponent;
import com.oubowu.daggerpractice.learn.module.BaseModule;

/**
 * Created by Oubowu on 2017/11/30 11:52.
 */
public class MyApplication extends Application {

    private BaseComponent mBaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseComponent = DaggerBaseComponent.builder().baseModule(new BaseModule()).build();
    }

    public BaseComponent getBaseComponent() {
        return mBaseComponent;
    }
}
