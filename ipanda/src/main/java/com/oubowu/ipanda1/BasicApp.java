package com.oubowu.ipanda1;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.oubowu.ipanda.BuildConfig;
import com.oubowu.ipanda.di.AppInject;
import com.oubowu.ipanda.util.LoggerPrinterPlus;
import com.oubowu.ipanda1.db.AppDatabase;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Oubowu on 2017/12/13 17:16.
 */
public class BasicApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();

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

        AppInject.init(this);

    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
