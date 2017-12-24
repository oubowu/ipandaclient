package com.oubowu.daggerpractice.weatherdemo.contract;


import com.oubowu.daggerpractice.weatherdemo.bean.WeatherBean;

/**
 * Created by Oubowu on 2017/12/7 14:49.
 */
public interface WeatherContract {
    interface Model {
        void requestWeatherData();
    }

    interface View {

        void initView();

        void showData(WeatherBean bean);
        void showProgressBar();
        void hideProgressBar();

    }

    interface Presenter {

        void loadData();

        void loadDataSuccess(WeatherBean bean);
        void loadDataFailure();
    }
}
