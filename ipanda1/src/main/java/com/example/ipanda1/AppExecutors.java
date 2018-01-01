package com.example.ipanda1;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Oubowu on 2017/12/13 17:16.
 */
public class AppExecutors {

    private final Executor mDisIO;
    private final Executor mNetWorkIO;
    private final Executor mMainThread;

    public AppExecutors(Executor disIO, Executor netWorkIO, Executor mainThread) {
        mDisIO = disIO;
        mNetWorkIO = netWorkIO;
        mMainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());
    }

    public Executor disIO() {
        return mDisIO;
    }

    public Executor netWorkIO() {
        return mNetWorkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
