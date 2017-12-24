package com.oubowu.ipanda.di.component;

import com.oubowu.ipanda.di.module.IpandaApiModule;
import com.oubowu.ipanda1.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oubowu on 2017/12/21 16:50.
 */
@Singleton
@Component(modules = {IpandaApiModule.class})
public interface IpandaApiComponent {

    void inject(HomeActivity homeActivity);

}
