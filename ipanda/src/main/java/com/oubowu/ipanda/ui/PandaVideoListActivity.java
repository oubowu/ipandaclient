package com.oubowu.ipanda.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.databinding.ActivityPandaVideoListBinding;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.viewmodel.PandaVideoListViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PandaVideoListActivity extends AppCompatActivity implements HasSupportFragmentInjector{

    private static final String ID = "id";

    private static final String TITLE = "title";

    @Inject
    ViewModelProvider.Factory mFactory;

    private PandaVideoListViewModel mPandaVideoListViewModel;
    private ActivityPandaVideoListBinding mVideoListBinding;

    public static void start(Activity activity, String id, String  title) {
        Intent intent = new Intent(activity, PandaVideoListActivity.class).putExtra(ID, id).putExtra(TITLE,  title);

        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
        //        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_panda_video_list);

        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBarColorLollipop);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark1);
            StatusBarUtil.StatusBarLightMode(this, 3);
        }

        String id = getIntent().getStringExtra(ID);
        String title = getIntent().getStringExtra(TITLE);

        mVideoListBinding = DataBindingUtil.setContentView(this, R.layout.activity_panda_video_list);

        mPandaVideoListViewModel = ViewModelProviders.of(this, mFactory).get(PandaVideoListViewModel.class);

        mPandaVideoListViewModel.getRecordTab(id, 10, 0).observe(this, new ObserverImpl<RecordTab>() {
            @Override
            protected void onSuccess(@NonNull RecordTab data) {
                super.onSuccess(data);
            }
        });

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
