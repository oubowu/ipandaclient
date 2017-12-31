package com.oubowu.ipanda.util;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Oubowu on 2017/12/31 22:54.
 */
public class MapUtil {

    @Nullable
    public static <K, V> V getFirstElement(@Nullable Map<K, V> map) {
        if (map != null) {
            Collection<V> collection = map.values();
            if (!CommonUtil.isEmpty(collection)) {
                Iterator<V> iterator = collection.iterator();
                if (iterator.hasNext()) {
                    return iterator.next();
                }
            }
        }
        return null;
    }

}
