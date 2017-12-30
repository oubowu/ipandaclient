package com.oubowu.ipanda.bean;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.util.LoggerInfoUtils;

/**
 * Created by Oubowu on 2017/12/30 22:21.
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    @Override
    public String toString() {
        return "Resource{\n" + "status=" + status + ",\n message='" + message + '\'' + ",\n data=" + LoggerInfoUtils.toString(data) + "\n}";
    }
}
