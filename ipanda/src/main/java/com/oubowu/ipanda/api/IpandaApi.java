package com.oubowu.ipanda.api;

import com.oubowu.ipanda.api.calladapter.LiveDataCallAdapterFactory;
import com.oubowu.ipanda.api.converter.IpandaGsonConverterFactory;
import com.oubowu.ipanda.api.intercept.DynamicHostInterceptor;
import com.oubowu.ipanda.api.intercept.LogInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Oubowu on 2017/12/21 16:13.
 */
@Singleton
public class IpandaApi {

    private static final long CONNECT_TIME_OUT = 30L;
    private static final long READ_TIME_OUT = 30L;
    private static final long WRITE_TIME_OUT = 30L;

    private final Retrofit mRetrofit;

    @Inject
    public IpandaApi() {
        mRetrofit = new Retrofit.Builder().baseUrl("http://www.ipanda.com/").addConverterFactory(IpandaGsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create()).client(setupClient()).build();

    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private OkHttpClient setupClient() {
        //            HttpLoggingInterceptor loggingInterceptor = getHttpLoggingInterceptor();
        //            Interceptor layoutInterceptor = getLayoutInterceptor();
        //            Interceptor hmacInterceptor = getHMACInterceptor();
        //            Interceptor cookieInterceptor = getCookieInterceptor();

        //            Authenticator authenticator = new TokenAuthenticator(context);

        return new OkHttpClient.Builder()
                //                    .dispatcher(new Dispatcher(GlobalConfig.HTTP_EXECUTOR))
                //                    .cache(getHttpCache())
                //                    .authenticator(authenticator)
                //                    .addInterceptor(loggingInterceptor)
                .addInterceptor(new DynamicHostInterceptor())
                //                    .addInterceptor(getCacheInterceptor())
                //                    .addNetworkInterceptor(getCacheInterceptor())
                //                    .addNetworkInterceptor(layoutInterceptor)
                //                    .addNetworkInterceptor(hmacInterceptor)
                //                    .addNetworkInterceptor(cookieInterceptor)
                .addInterceptor(new LogInterceptor()).connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
    }

    // 有1.addInterceptor ,和2.addNetworkInterceptor这两种。他们的区别简单的说下，不知道也没关系，addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
    // addinterceptor添加的是aplication拦截器，他只会在response被调用一次。


}
