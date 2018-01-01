package com.oubowu.ipanda.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by Oubowu on 2018/1/1 1:10.
 */
public class NullValueLiveData extends LiveData {

    @SuppressWarnings("unchecked")
    private NullValueLiveData() {
        postValue(null);
    }

    public static NullValueLiveData create() {
        return new NullValueLiveData();
    }

}
