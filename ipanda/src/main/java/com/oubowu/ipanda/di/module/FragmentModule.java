package com.oubowu.ipanda.di.module;

import com.oubowu.ipanda.ui.HostFragment;
import com.oubowu.ipanda.ui.PandaLiveFragment;
import com.oubowu.ipanda.ui.PandaLiveSubFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Oubowu on 2018/1/9 10:12.
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract HostFragment contributeHostFragment();

    @ContributesAndroidInjector
    abstract PandaLiveFragment contributePandaLiveFragment();

    @ContributesAndroidInjector
    abstract PandaLiveSubFragment contributePandaLiveSubFragment();

}
