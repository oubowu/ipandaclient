package com.oubowu.daggerpractice.learn.module;

import android.util.Log;

import com.oubowu.daggerpractice.learn.bean.Cloth;
import com.oubowu.daggerpractice.learn.bean.Clothes;
import com.oubowu.daggerpractice.learn.bean.Shoe;
import com.oubowu.daggerpractice.learn.qualifier.BlackCloth;
import com.oubowu.daggerpractice.learn.scope.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/11/28 10:58.
 */
@Module
public class Main2Module {

    @Provides
    public Cloth provideCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    public Clothes provideClothes(@Named("blue") Cloth cloth){
        return new Clothes(cloth);
    }

    @Provides
    @Named("red")
    public Cloth provideRedCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    // @Singleton
    @PerActivity
    @Named("blue")
    public Cloth provideBlueCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }

    @Provides
    @BlackCloth
    public Cloth provideBlackCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("黑色");
        return cloth;
    }

//    @PerActivity
//    @Provides
//    public ClothHandler provideClothHandler(){
//        return new ClothHandler();
//    }

    private static final String TAG = "MainModule";
    //注意:这里没有声明作用域内单例
    @Provides
    @Named("white")
    public Cloth provideWhiteCloth() {
        Log.e(TAG, "provideWhiteCloth: ...");
        Cloth cloth = new Cloth();
        cloth.setColor("白色");
        return cloth;
    }
    //注意:这里没有声明作用域内单例
    @Provides
    @Named("providerShoe")
    public Shoe provideShoe(){
        Log.e(TAG, "provideShoe: ...");
        return new Shoe();
    }


}
