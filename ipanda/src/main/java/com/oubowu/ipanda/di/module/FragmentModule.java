package com.oubowu.ipanda.di.module;

import com.oubowu.ipanda.ui.HostFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Oubowu on 2018/1/9 10:12.
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract HostFragment contributeHostFragment();

}
