package com.oubowu.daggerpractice.weatherdemo.model;

import com.oubowu.daggerpractice.weatherdemo.bean.WeatherBean;
import com.oubowu.daggerpractice.weatherdemo.contract.WeatherContract;
import com.oubowu.daggerpractice.weatherdemo.presenter.WeatherPresenter;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Oubowu on 2017/12/7 14:49.
 */
public class WeatherModel implements WeatherContract.Model {

    WeatherPresenter mWeatherPresenter;

    public WeatherModel(WeatherPresenter weatherPresenter) {
        mWeatherPresenter = weatherPresenter;
    }

    @Override
    public void requestWeatherData() {
        String mBaseUrl = "http://www.weather.com.cn/adat/sk/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        weatherService.getWeatherBean("101010100").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WeatherBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mWeatherPresenter.loadDataFailure();
            }

            @Override
            public void onNext(WeatherBean weatherBean) {
                mWeatherPresenter.loadDataSuccess(weatherBean);
            }
        });
    }

    interface WeatherService {
        @GET("{cityId}" + ".html")
        Observable<WeatherBean> getWeatherBean(@Path("cityId") String cityId);
    }

}
