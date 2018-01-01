package com.oubowu.ipanda.di.module;


import com.oubowu.ipanda.ui.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Oubowu on 2017/12/28 02:46.
 */
@Module
public abstract class HomeActivityModule {

    @ContributesAndroidInjector
    abstract HomeActivity contributesHomeActivity();

}
