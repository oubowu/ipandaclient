package com.oubowu.ipanda.api.intercept;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Oubowu on 2017/12/21 22:06.
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();


        //        Request.Builder requestBuilder = request.newBuilder();
        //        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36 QQBrowser/4.2.4976.400");
        //        requestBuilder.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //        requestBuilder.addHeader("Accept-Encoding","gzip, deflate, sdch");
        //        requestBuilder.addHeader("Accept-Language","zh-CN,zh;q=0.8");
        //        requestBuilder.addHeader("Cache-Control","max-age=0");
        //        requestBuilder.addHeader("Connection","keep-alive");
        //        requestBuilder.addHeader("Upgrade-Insecure-Requests","1");
        //        request = requestBuilder.build();

        final Response response = chain.proceed(request);

        Log.e("Oubowu",
                "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

        Logger.e("请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "响应头部信息：\n" + response.headers());

        final ResponseBody responseBody = response.body();

        long contentLength;

        if (responseBody != null) {

            contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    Logger.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0 && charset != null) {
                Logger.json(buffer.clone().readString(charset));
            }

            Log.e("Oubowu",
                    "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

        }

        return response;
    }
}
