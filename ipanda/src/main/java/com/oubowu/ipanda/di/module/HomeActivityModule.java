package com.oubowu.ipanda.di.module;


import com.oubowu.ipanda.ui.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Oubowu on 2017/12/28 02:46.
 */
@Module
public abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class}/*注入到HomeActivity的module*/)
    abstract HomeActivity contributesHomeActivity();

}
