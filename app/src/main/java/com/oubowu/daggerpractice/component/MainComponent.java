package com.oubowu.daggerpractice.component;

import com.oubowu.daggerpractice.view.MainActivity;
import com.oubowu.daggerpractice.model.MainModel;

import dagger.Component;

/**
 * Created by Oubowu on 2017/11/20 17:35.
 */
@Component(modules = MainModel.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
