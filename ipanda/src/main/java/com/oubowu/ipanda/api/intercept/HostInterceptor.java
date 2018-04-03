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
public class HostInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {


        Request oldRequest = chain.request();

        String realHost = oldRequest.header("realHost");

        // Log.e("xxx",realHost);

        if (!TextUtils.isEmpty(realHost)) {

            HttpUrl.Builder newHttpUrlBuilder = oldRequest.url().newBuilder().host(realHost);

            Request.Builder newRequestBuilder = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body()).url(newHttpUrlBuilder.build());

            // newRequestBuilder.addHeader("Accept","application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            // newRequestBuilder.addHeader("Host","www.ipanda.com");
            // newRequestBuilder.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36 QQBrowser/4.3.4986.400");
            // newRequestBuilder.addHeader("Upgrade-Insecure-Requests","1");

            Request request = newRequestBuilder.build();

            return chain.proceed(request);
        } else {
            return chain.proceed(oldRequest);
        }

    }
}
