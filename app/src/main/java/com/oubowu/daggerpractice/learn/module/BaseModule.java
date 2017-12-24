package com.oubowu.daggerpractice.learn.module;

import com.oubowu.daggerpractice.learn.ClothHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/11/30 11:40.
 */
@Module
public class BaseModule {

    @Singleton
    @Provides
    public ClothHandler provideClothHandler(){
        return new ClothHandler();
    }

}
