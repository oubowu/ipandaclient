package com.oubowu.daggerpractice.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oubowu.daggerpractice.R;
import com.oubowu.daggerpractice.component.DaggerMainComponent;
import com.oubowu.daggerpractice.contract.MainContract;
import com.oubowu.daggerpractice.learn.bean.Shoe;
import com.oubowu.daggerpractice.model.MainModel;
import com.oubowu.daggerpractice.presenter.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "嘻嘻嘻";
    @Inject
    MainPresenter mainPresenter;

    @Inject
    Shoe mShoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModel(new MainModel(this))
                .build()
                .inject(this);

        //调用Presenter方法加载数据
        mainPresenter.loadData();


    }

    @Override
    public void updateUI() {

    }

}
