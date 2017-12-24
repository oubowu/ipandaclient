package com.oubowu.daggerpractice.model;

import com.oubowu.daggerpractice.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/11/20 17:30.
 */
@Module
public class MainModel implements MainContract.Model {
    private final MainContract.View mView;

    public MainModel(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}
