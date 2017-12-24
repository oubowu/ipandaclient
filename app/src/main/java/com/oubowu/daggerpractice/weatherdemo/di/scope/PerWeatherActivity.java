package com.oubowu.daggerpractice.weatherdemo.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Oubowu on 2017/12/7 16:10.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerWeatherActivity {
}
