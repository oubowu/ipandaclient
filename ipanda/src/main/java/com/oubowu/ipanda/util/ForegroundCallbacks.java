package com.oubowu.ipanda.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * 作者：aqianglala
 * 链接：https://www.jianshu.com/p/c894492d79b9
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {

    private static final long CHECK_DELAY = 500;

    private static volatile ForegroundCallbacks sInstance;
    private boolean mForeground = true;
    private boolean mPaused = true;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mCheck;

    public static ForegroundCallbacks init(Application application) {
        if (sInstance == null) {
            synchronized (ForegroundCallbacks.class) {
                if (sInstance == null) {
                    sInstance = new ForegroundCallbacks();
                    application.registerActivityLifecycleCallbacks(sInstance);
                }
            }
        }
        return sInstance;
    }

    public static ForegroundCallbacks get() {
        if (sInstance == null) {
            throw new IllegalStateException("Foreground is not initialised - invoke " + "at least once with parameterised init/get");
        }
        return sInstance;
    }

    public boolean isForeground() {
        return mForeground;
    }

    public boolean isBackground() {
        return !mForeground;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mPaused = false;
        boolean wasBackground = !mForeground;
        mForeground = true;
        if (mCheck != null) {
            mHandler.removeCallbacks(mCheck);
        }
        if (wasBackground) {
        } else {
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mPaused = true;
        if (mCheck != null) {
            mHandler.removeCallbacks(mCheck);
        }
        mHandler.postDelayed(mCheck = () -> {
            if (mForeground && mPaused) {
                mForeground = false;
            } else {
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}


