package com.oubowu.daggerpractice.aacdemo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.oubowu.daggerpractice.R;
import com.oubowu.daggerpractice.aacdemo.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Activity 继承的是一个 LifecycleActivity,实际上它是 LifecycleOwner 的实现,这个接口的主要作用是返回一个 Lifecycle,
// 这个类前面介绍过了,它主要是作用是用来表达 UI 的 状态和过程,而 Lifecycle 的主要实现是 LifecycleRegistry.
public class AacActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView mTextView;

    @OnClick(R.id.tv)
    void test() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac);

        ButterKnife.bind(this);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                mTextView.setText(user.getName() + " ; " + user.getPassword());
            }
        });

    }
}
