package com.oubowu.ipanda.di.component;

import android.app.Application;

import com.oubowu.ipanda.BasicApp;
import com.oubowu.ipanda.di.module.AppModule;
import com.oubowu.ipanda.di.module.ActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Oubowu on 2017/12/25 2:30.
 */
// 依赖AndroidSupportInjectionModule.class即可在四大组件+Fragment+V4版本Fragement进行注入；
// AppModule提供Service和ViewModelProvider.Factory；
// HomeActivityModule提供具体的注入Activity为HomeActivity
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityModule.class})
public interface AppComponent {

    // 创建builder，传入application注入，后续注入地方@Inject Applcation即可获取Application实例
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(BasicApp basicApp);

}
