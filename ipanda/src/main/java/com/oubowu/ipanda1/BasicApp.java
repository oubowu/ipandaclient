package com.oubowu.ipanda1;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.oubowu.ipanda.BuildConfig;
import com.oubowu.ipanda.di.component.DaggerAppComponent;
import com.oubowu.ipanda.util.LoggerPrinterPlus;
import com.oubowu.ipanda1.db.AppDatabase;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Oubowu on 2017/12/13 17:16.
 */
public class BasicApp extends Application implements HasActivityInjector {

    private AppExecutors mAppExecutors;

    //    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().methodCount(4).methodOffset(4)
                .tag("Oubowu")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.printer(new LoggerPrinterPlus());
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        //        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).ipandaApiModule(new IpandaApiModule()).build();

        DaggerAppComponent.builder().application(this).build().inject(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
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

    //    public static AppComponent getAppComponent() {
    //        return sAppComponent;
    //    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
