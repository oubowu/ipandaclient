package com.oubowu.ipanda.api.calladapter;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.oubowu.ipanda.api.response.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oubowu on 2017/12/27 11:48.
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R,LiveData<ApiResponse<R>>> {

    private final Type mResponseType;

    public LiveDataCallAdapter(Type responseType) {
        mResponseType = responseType;
    }

    // 直正数据的类型 如Call<T> 中的 T
    // 这个 T 会作为Converter.Factory.responseBodyConverter 的第一个参数
    @Override
    public Type responseType() {
        return mResponseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false,true)){
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                            postValue(new ApiResponse<R>(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                            postValue(new ApiResponse<R>(t));
                        }
                    });
                }
            }
        };
    }
}
