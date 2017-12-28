package com.oubowu.ipanda.di.qualifier;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by Oubowu on 2017/12/28 23:41.
 */
@MapKey
@Target(ElementType.METHOD)
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}