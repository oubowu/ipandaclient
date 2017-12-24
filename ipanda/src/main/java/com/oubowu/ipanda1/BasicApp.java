package com.oubowu.ipanda1;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.oubowu.ipanda.BuildConfig;
import com.oubowu.ipanda.di.component.AppComponent;
import com.oubowu.ipanda.di.component.DaggerAppComponent;
import com.oubowu.ipanda.di.module.AppModule;
import com.oubowu.ipanda.di.module.IpandaApiModule;
import com.oubowu.ipanda.util.LoggerPrinterPlus;
import com.oubowu.ipanda1.db.AppDatabase;

/**
 * Created by Oubowu on 2017/12/13 17:16.
 */
public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodCount(4)
                .methodOffset(4)
                .tag("Oubowu")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.printer(new LoggerPrinterPlus());
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).ipandaApiModule(new IpandaApiModule()).build();

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

}
