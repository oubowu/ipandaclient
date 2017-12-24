package com.oubowu.daggerpractice.learn.module;

import com.oubowu.daggerpractice.learn.bean.Cloth;
import com.oubowu.daggerpractice.learn.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/11/30 11:30.
 */
@Module
public class Main3Module {

    @PerActivity
    @Provides
    public Cloth provideBlueCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }

//    @PerActivity
//    @Provides
//    public ClothHandler provideClothHandler(){
//        return new ClothHandler();
//    }


}
