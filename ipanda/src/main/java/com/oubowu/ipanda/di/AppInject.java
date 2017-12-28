package com.oubowu.ipanda.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.oubowu.ipanda.di.component.DaggerAppComponent;
import com.oubowu.ipanda1.BasicApp;

import dagger.android.AndroidInjection;

/**
 * Created by Oubowu on 2017/12/28 23:31.
 */
public class AppInject {

    private AppInject(){
    }

    public static void init(BasicApp basicApp){
        DaggerAppComponent.builder().application(basicApp).build().inject(basicApp);

        basicApp.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                AndroidInjection.inject(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

}
