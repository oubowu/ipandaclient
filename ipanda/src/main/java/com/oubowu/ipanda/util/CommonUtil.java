package com.oubowu.ipanda.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

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

    public static boolean isNotEmpty(@Nullable Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(@Nullable CharSequence c) {
        return TextUtils.isEmpty(c);
    }

    public static boolean isNotEmpty(@Nullable CharSequence c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(@Nullable Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Map map) {
        return !isEmpty(map);
    }

}
