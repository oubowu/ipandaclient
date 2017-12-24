package com.oubowu.ipanda.di.component;

import android.content.Context;

import com.oubowu.ipanda.di.module.AppModule;
import com.oubowu.ipanda.di.module.IpandaApiModule;
import com.oubowu.ipanda.http.IpandaApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oubowu on 2017/12/25 2:30.
 */
@Singleton
@Component(modules = {AppModule.class, IpandaApiModule.class})
public interface AppComponent {

    Context getContext();

    IpandaApi getIpandaApi();

}
