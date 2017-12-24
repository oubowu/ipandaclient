package com.oubowu.ipanda.http.intercept;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.http.Hosts;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Oubowu on 2017/12/22 12:02.
 */
public class DynamicHostInterceptor implements Interceptor {

    private SparseArray<String> mDynamicHosts;

    @Hosts.HostsChecker
    private int mHost;

    public DynamicHostInterceptor(SparseArray<String> dynamicHosts) {
        mDynamicHosts = dynamicHosts;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url();

        String method = request.method();

        Headers headers = request.headers();

        RequestBody body = request.body();

        Object tag = request.tag();

         String host = url.host();

        Logger.e(mDynamicHosts.get(mHost)+";"+url.url().getPath());

        return chain.proceed(request.newBuilder().url(mDynamicHosts.get(mHost) + url.url().getPath()).method(method, body).headers(headers).tag(tag).build());

    }

    public void setHost(@Hosts.HostsChecker int host) {
        mHost = host;
    }
}
