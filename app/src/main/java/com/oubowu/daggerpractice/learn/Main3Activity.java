package com.oubowu.daggerpractice.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.oubowu.daggerpractice.R;
import com.oubowu.daggerpractice.learn.bean.Cloth;
import com.oubowu.daggerpractice.learn.component.DaggerMain3Component;
import com.oubowu.daggerpractice.learn.module.Main3Module;

import javax.inject.Inject;

public class Main3Activity extends AppCompatActivity {

    @Inject
    Cloth mBlueCloth;

    @Inject
    ClothHandler mClothHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        DaggerMain3Component.builder().baseComponent(((MyApplication)getApplication()).getBaseComponent()).main3Module(new Main3Module()).build().inject(this);

        Log.e("Main3Activity","蓝布料加工后变成了" + mClothHandler.handle(mBlueCloth) + "\nclothHandler地址:" + mClothHandler);

    }
}
