package com.oubowu.daggerpractice.weatherdemo.presenter;

import com.oubowu.daggerpractice.learn.scope.PerActivity;
import com.oubowu.daggerpractice.weatherdemo.bean.WeatherBean;
import com.oubowu.daggerpractice.weatherdemo.contract.WeatherContract;
import com.oubowu.daggerpractice.weatherdemo.model.WeatherModel;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2017/12/7 14:49.
 */
@PerActivity
public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View mView;

    private WeatherContract.Model mModel = new WeatherModel(this);

    @Inject
    public WeatherPresenter(WeatherContract.View view) {
        mView = view;
    }

    @Override
    public void loadData() {
        mView.showProgressBar();
        mModel.requestWeatherData();
    }

    @Override
    public void loadDataSuccess(WeatherBean bean) {
        mView.hideProgressBar();
        mView.showData(bean);
    }

    @Override
    public void loadDataFailure() {
        mView.hideProgressBar();
        mView.showData(null);
    }
}
