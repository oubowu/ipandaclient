package com.oubowu.ipanda.callback;

import android.view.View;

/**
 * Created by Oubowu on 2018/2/23 10:26.
 */
public interface EventListener {
    void clickArrow(View v);

    void clickBigImg(View v, String id);

    void clickItem(View v, String id);

    void clickItemWithTitle(View v, String id,String title);

}
