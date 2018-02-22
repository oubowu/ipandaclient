package com.oubowu.ipanda.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.ui.HomeActivity;
import com.oubowu.ipanda.ui.HostFragment;
import com.oubowu.ipanda.ui.PandaLiveFragment;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/1/8 15:26.
 */
public class NavigationController extends FragmentManager.FragmentLifecycleCallbacks {

    private final int mContainerId;
    private final FragmentManager mFragmentManager;

    private final ArrayMap<String, String> mTagMap;

    @Inject
    public NavigationController(HomeActivity homeActivity) {
        mContainerId = R.id.fl_container;
        mFragmentManager = homeActivity.getSupportFragmentManager();
        mTagMap = new ArrayMap<>();
    }

    public void navigateToHost(String name, String url) {
        Log.e("NavigationController", "跳转首页碎片");
        changeFragment(HostFragment.class, () -> HostFragment.newInstance(name, url));

    }

    public void navigateToPandaLive(String name, String url) {
        Log.e("NavigationController", "跳转熊猫直播碎片");
        changeFragment(PandaLiveFragment.class, () -> PandaLiveFragment.newInstance(name, url));
    }

    private void changeFragment(Class clz, Closure closure) {

        String tag = clz.getSimpleName();

        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (fragment == null) {
            mTagMap.put(tag, tag);
            fragment = closure.createFragment();
            ft.add(mContainerId, fragment, tag);
        }

        for (Fragment f : mFragmentManager.getFragments()) {
            if (mTagMap.indexOfKey(f.getTag()) == -1) {
                continue;
            }
            if (f == fragment && f.isHidden()) {
                ft.show(f);
            } else if (f != fragment && !f.isHidden()) {
                ft.hide(f);
            }
        }

        ft.commitAllowingStateLoss();

    }

    public interface Closure {
        @NonNull
        Fragment createFragment();
    }

}
