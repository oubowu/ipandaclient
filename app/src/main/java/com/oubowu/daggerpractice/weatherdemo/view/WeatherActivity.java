package com.oubowu.daggerpractice.weatherdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oubowu.daggerpractice.R;
import com.oubowu.daggerpractice.weatherdemo.bean.WeatherBean;
import com.oubowu.daggerpractice.weatherdemo.contract.WeatherContract;
import com.oubowu.daggerpractice.weatherdemo.di.component.DaggerWeatherPresenterComponent;
import com.oubowu.daggerpractice.weatherdemo.di.module.WeatherPresenterModule;
import com.oubowu.daggerpractice.weatherdemo.presenter.WeatherPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {

    @BindView(R.id.tv)
    TextView mTextView;

    @BindView(R.id.pb)
    ProgressBar mProgressBar;

    @OnClick(R.id.tv)
    void load() {
        mWeatherPresenter.loadData();
    }

    @Inject
    WeatherPresenter mWeatherPresenter;

    @Inject
    WeatherPresenter mWeatherPresenter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        // mWeatherPresenter = new WeatherPresenter(this);

        DaggerWeatherPresenterComponent.builder().weatherPresenterModule(new WeatherPresenterModule(this)).build().inject(this);

        Log.e("WeatherActivity","50行-onCreate(): "+(mWeatherPresenter1==mWeatherPresenter));

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void showData(WeatherBean bean) {
        if (bean == null) {
            mTextView.setText("请求失败");
        } else {
            WeatherBean.WeatherinfoEntity weatherinfo = bean.getWeatherinfo();
            String city = weatherinfo.getCity();
            String wd = weatherinfo.getWD();
            String ws = weatherinfo.getWS();
            String time = weatherinfo.getTime();
            String data = "城市:" + city + "\n风向:" + wd + "\n风级:" + ws + "\n发布时间:" + time;
            mTextView.setText(data);
        }
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
