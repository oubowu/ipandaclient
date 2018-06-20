package com.oubowu.ipanda;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.oubowu.ipanda.di.AppInject;
import com.oubowu.ipanda.util.ForegroundCallbacks;
import com.oubowu.ipanda.util.LoggerPrinterPlus;
import com.oubowu.ipanda.util.ToastUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Oubowu on 2017/12/13 17:16.
 */
public class BasicApp extends Application implements HasActivityInjector {

    private static Context sContext;

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().methodCount(4).methodOffset(4)
                .tag("Oubowu")
                .build();
        Logger.printer(new LoggerPrinterPlus());
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        sContext = this;

        AppInject.init(this);

        ForegroundCallbacks.init(this);

        ToastUtil.init(this);

    }

    public static Context getInstance() {
        return sContext;
    }
}
