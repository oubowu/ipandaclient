package com.oubowu.ipanda.util;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * Created by Oubowu on 2017/12/30 23:00.
 */
public class CommonUtil {

    public static boolean equals(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    public static boolean isEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty();
    }

}
