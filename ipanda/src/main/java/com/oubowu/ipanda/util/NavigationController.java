package com.oubowu.ipanda.util;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.ui.HomeActivity;
import com.oubowu.ipanda.ui.HostFragment;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/1/8 15:26.
 */
public class NavigationController {

    private final int mContainerId;
    private final FragmentManager mFragmentManager;

    @Inject
    public NavigationController(HomeActivity homeActivity) {
        mContainerId = R.id.fl_container;
        mFragmentManager = homeActivity.getSupportFragmentManager();
    }

    public void navigateToHost() {
        Log.e("NavigationController","27行-navigateToHost(): "+" ");
        HostFragment hostFragment = HostFragment.newInstance("", "");
        mFragmentManager.beginTransaction().replace(mContainerId, hostFragment, HostFragment.class.getSimpleName()).commitAllowingStateLoss();
    }

}
