package com.oubowu.ipanda.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.oubowu.ipanda.callback.HandleBackInterface;

import java.util.List;

/**
 * Created by Oubowu on 2018/3/6 16:54.
 */
public class HandleBackUtil {

    public static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {
            return false;
        }
        for (Fragment f : fragments) {
            if (isFragmentBackHandled(f)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFragmentBackHandled(Fragment f) {
        return f != null //
                && f.isVisible() //
                && f.getUserVisibleHint() // for ViewPager
                && f instanceof HandleBackInterface //
                && ((HandleBackInterface) f).onBackPressed();
    }

    public static boolean handleBackPress(Fragment fragment) {
        return handleBackPress(fragment.getChildFragmentManager());
    }

    public static boolean handleBackPress(FragmentActivity fragmentActivity) {
        return handleBackPress(fragmentActivity.getSupportFragmentManager());
    }

}
