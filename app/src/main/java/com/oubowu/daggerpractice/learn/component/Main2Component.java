package com.oubowu.daggerpractice.learn.component;

import com.oubowu.daggerpractice.learn.Main2Activity;
import com.oubowu.daggerpractice.learn.module.Main2Module;
import com.oubowu.daggerpractice.learn.scope.PerActivity;

import dagger.Component;

/**
 * Created by Oubowu on 2017/11/28 11:00.
 */
// @Singleton
@PerActivity
@Component(modules = Main2Module.class,
        dependencies = BaseComponent.class)
public interface Main2Component {
    void inject(Main2Activity main2Activity);
}
