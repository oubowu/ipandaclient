package com.oubowu.ipanda.api.calladapter;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.oubowu.ipanda.api.response.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Oubowu on 2017/12/27 10:34.
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    public static LiveDataCallAdapterFactory create(){
        return new LiveDataCallAdapterFactory();
    }

    // 在这个方法中判断是否是我们支持的类型，returnType 即Call<Responsebody>和`Observable<Responsebody>`
    // RxJavaCallAdapterFactory 就是判断returnType是不是Observable<?> 类型，不支持时返回null
    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {

        //  LiveData<ApiResponse<List<Repo>>> getRepos(@Path("login") String login);

        // 用于获取泛型的原始类型，对应 LiveData
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }

        // 用于获取泛型的参数，对应 ApiResponse<List<Repo>>
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        // 用于获取泛型的原始类型，对应 ApiResponse
        Class<?> rawObservableType = getRawType(observableType);

        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }

        // ParameterizedType是一个接口,这个类可以用来检验泛型是否被参数化
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }

        // 用于获取泛型的参数，对应 List<Repo>
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);

        return new LiveDataCallAdapter<>(bodyType);
    }
}
