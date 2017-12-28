package com.oubowu.ipanda.api.intercept;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.oubowu.ipanda.api.Hosts;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
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

        //        Request request = chain.request();
        //
        //        HttpUrl url = request.url();
        //
        //        String method = request.method();
        //
        //        Headers headers = request.headers();
        //
        //        RequestBody body = request.body();
        //
        //        Object tag = request.tag();
        //
        //        // String host = url.host();
        //
        //        URL realUrl = url.url();
        //
        //
        //        Logger.e(mDynamicHosts.get(mHost)+";"+ realUrl.getPath()+";"+realUrl.getQuery());
        //
        //        return chain.proceed(request.newBuilder().url(mDynamicHosts.get(mHost) + realUrl.getPath()+realUrl.getQuery()).method(method, body).headers(headers).tag(tag).build());

        Request oldRequest = chain.request();

        // Logger.e(oldRequest.url().host());

        Log.e("DynamicHostInterceptor","57行-intercept(): "+mHost);

        HttpUrl.Builder newHostBuilder = oldRequest.url().newBuilder().host(mDynamicHosts.get(mHost));

        Request request = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).url(newHostBuilder.build()).build();

        return chain.proceed(request);

    }

    public void setHost(@Hosts.HostsChecker int host) {
        mHost = host;
    }
}
