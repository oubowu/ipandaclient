package com.oubowu.ipanda.api.intercept;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Oubowu on 2017/12/22 12:02.
 */
public class DynamicHostInterceptor implements Interceptor {

    public DynamicHostInterceptor() {
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

        String realHost = oldRequest.header("realHost");

        // Log.e("xxx",realHost);

        if (!TextUtils.isEmpty(realHost)) {
            HttpUrl.Builder newHostBuilder = oldRequest.url().newBuilder().host(realHost);
            Request request = oldRequest.newBuilder().method(oldRequest.method(), oldRequest.body()).url(newHostBuilder.build()).build();
            return chain.proceed(request);
        } else {
            return chain.proceed(oldRequest);
        }

    }
}
