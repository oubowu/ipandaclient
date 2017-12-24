package com.oubowu.daggerpractice.learn.component;

import com.oubowu.daggerpractice.learn.Main2Activity;
import com.oubowu.daggerpractice.learn.module.Main2Module;
import com.oubowu.daggerpractice.learn.scope.PerActivity;

import dagger.Subcomponent;

/**
 * Created by Oubowu on 2017/11/30 11:59.
 */
@PerActivity
@Subcomponent(modules = Main2Module.class)
public interface SubMain2Component {

    void inject(Main2Activity main2Activity);

}
