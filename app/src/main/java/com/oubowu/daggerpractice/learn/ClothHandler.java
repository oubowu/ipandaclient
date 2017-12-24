package com.oubowu.daggerpractice.learn;

import com.oubowu.daggerpractice.learn.bean.Cloth;
import com.oubowu.daggerpractice.learn.bean.Clothes;

/**
 * Created by Oubowu on 2017/11/30 11:22.
 */
public class ClothHandler {

    public Clothes handle(Cloth cloth){
        return new Clothes(cloth);
    }

}
