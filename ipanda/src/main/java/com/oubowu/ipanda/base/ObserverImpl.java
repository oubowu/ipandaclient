package com.oubowu.ipanda.base;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.util.Resource;

/**
 * Created by Oubowu on 2018/6/6 12:01.
 */
public abstract class ObserverImpl<T> implements Observer<Resource<T>> {

    @Override
    public void onChanged(@Nullable Resource<T> resource) {
        if (resource != null) {
            switch (resource.status) {
                case ERROR:
                    if (resource.message != null) {
                        onError(resource.message);
                    } else {
                        onError("异常原因不明");
                    }
                    break;
                case LOADING:
                    onLoading();
                    break;
                case SUCCESS:
                    if (resource.data != null) {
                        onSuccess(resource.data);
                    } else {
                        onError("获取不到数据");
                    }
                    break;
            }
        } else {
            onError("数据异常");
        }
    }

    protected void onError(@NonNull String errorMsg) {
    }

    protected void onLoading() {
    }

    protected void onSuccess(@NonNull T data) {
    }

}
