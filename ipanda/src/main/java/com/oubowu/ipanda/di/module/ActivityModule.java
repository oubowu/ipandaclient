package com.oubowu.ipanda.di.module;


import com.oubowu.ipanda.ui.HomeActivity;
import com.oubowu.ipanda.ui.VideoActivity;
import com.oubowu.ipanda.ui.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Oubowu on 2017/12/28 02:46.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class}/*注入到HomeActivity的module*/)
    abstract HomeActivity contributesHomeActivity();

    @ContributesAndroidInjector()
    abstract VideoActivity contributesVideoActivity();

    @ContributesAndroidInjector()
    abstract WebViewActivity contributesWebViewActivity();

}
