package com.oubowu.ipanda.util;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Oubowu on 2018/1/1 14:11.
 */
public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {

        // BottomNavigationView已经设置了app:labelVisibilityMode="labeled"
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            // 强制大文字尺寸跟小文字尺寸一样，避免字体被选中放大导致文字被截
            TextView largeLabel = item.findViewById(android.support.design.R.id.largeLabel);
            largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.getResources().getDimension(android.support.design.R.dimen.design_bottom_navigation_text_size));
        }
        // 重新设置间接强制计算缩放比例
        menuView.setItemTextAppearanceInactive(menuView.getItemTextAppearanceInactive());

        //        try {
        //            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
        //            shiftingMode.setAccessible(true);
        //            shiftingMode.setBoolean(menuView, false);
        //            shiftingMode.setAccessible(false);
        //            for (int i = 0; i < menuView.getChildCount(); i++) {
        //                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
        //                //noinspection RestrictedApi
        //                item.setShiftingMode(false);
        //                // set once again checked value, so view will be updated
        //                //noinspection RestrictedApi
        //                item.setChecked(item.getItemData().isChecked());
        //            }
        //        } catch (NoSuchFieldException e) {
        //            Log.e("BNVHelper", "Unable to get shift mode field", e);
        //        } catch (IllegalAccessException e) {
        //            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        //        }
    }
}