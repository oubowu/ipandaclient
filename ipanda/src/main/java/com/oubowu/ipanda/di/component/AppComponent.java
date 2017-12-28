package com.oubowu.ipanda.di.component;

import android.app.Application;

import com.oubowu.ipanda.di.module.AppModule;
import com.oubowu.ipanda.di.module.HomeActivityModule;
import com.oubowu.ipanda1.BasicApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Oubowu on 2017/12/25 2:30.
 */
//@Singleton
//@Component(modules = {AppModule.class, IpandaApiModule.class})
//public interface AppComponent {
//
//    Context getContext();
//
//    IpandaApi getIpandaApi();
//
//}

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, HomeActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(BasicApp basicApp);

}
