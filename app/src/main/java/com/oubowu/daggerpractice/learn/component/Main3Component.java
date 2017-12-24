package com.oubowu.daggerpractice.learn.component;

import com.oubowu.daggerpractice.learn.Main3Activity;
import com.oubowu.daggerpractice.learn.module.Main3Module;
import com.oubowu.daggerpractice.learn.scope.PerActivity;

import dagger.Component;

/**
 * Created by Oubowu on 2017/11/28 11:00.
 */
// @Singleton
@PerActivity
@Component(modules = Main3Module.class,
        dependencies = BaseComponent.class)
public interface Main3Component {
    void inject(Main3Activity main3Activity);
}
