package com.oubowu.daggerpractice.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.oubowu.daggerpractice.R;
import com.oubowu.daggerpractice.learn.bean.Cloth;
import com.oubowu.daggerpractice.learn.bean.Clothes;
import com.oubowu.daggerpractice.learn.bean.Shoe;
import com.oubowu.daggerpractice.learn.component.DaggerMain2Component;
import com.oubowu.daggerpractice.learn.module.Main2Module;
import com.oubowu.daggerpractice.learn.qualifier.BlackCloth;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";

    @Inject
    Cloth mCloth;

    @Inject
    @Named("red")
    Cloth mRedCloth;

    @Inject
    @Named("blue")
    Cloth mBlueCloth;


    @Inject
    @BlackCloth
    Cloth mBlackCloth;

    @Inject
    Clothes mClothes;

    @Inject
    Shoe mShoe;

    @Inject
    ClothHandler mClothHandler;

    @BindView(R.id.tv)
    /*private*/ TextView mTv;

    @Inject
    @Named("white")
    Lazy<Cloth> mWhiteCloth;

    @Inject
    @Named("providerShoe")
    Provider<Shoe> mProviderShoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        MyApplication application = (MyApplication) getApplication();
        // application.getBaseComponent().getSubMainComponent(new Main2Module()).inject(this);

        DaggerMain2Component.builder().baseComponent(application.getBaseComponent()).main2Module(new Main2Module()).build().inject(this);

        Log.e("Main2Activity", "50行-onCreate(): " + (mClothes.getCloth() == mBlueCloth));

        Log.e("Main2Activity", "红布料加工后变成了" + mClothHandler.handle(mRedCloth) + "\nclothHandler地址:" + mClothHandler);

        Log.e(TAG, "inject done ...");
        Log.e(TAG, "1 use whiteCloth instance ..");
        Log.e(TAG, "whiteCloth:" + mWhiteCloth.get());
        Log.e(TAG, "2 use whiteCloth instance ..");
        Log.e(TAG, "whiteCloth:" + mWhiteCloth.get());
        Log.e(TAG, "1 use providerShoe instance ..");
        Log.e(TAG, "providerShoe:" + mProviderShoe.get());
        Log.e(TAG, "2 use providerShoe instance ..");
        Log.e(TAG, "providerShoe:" + mProviderShoe.get());

//        mTv = (TextView) findViewById(R.id.tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Main3Activity.class);
                startActivity(intent);
            }
        });

    }
}
