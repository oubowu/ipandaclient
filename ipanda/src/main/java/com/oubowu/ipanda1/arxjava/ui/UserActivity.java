package com.oubowu.ipanda1.arxjava.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda1.arxjava.Injection;
import com.oubowu.ipanda.databinding.ActivityUserBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    private ActivityUserBinding mBinding;

    private ViewModelFactory mViewModelFactory;

    private UserViewModel mUserViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_user);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);

        mViewModelFactory = Injection.provideViewModelFactory(this);

        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        mBinding.updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserName();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposable.add(mUserViewModel.getUserName().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mBinding.userName.setText(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("UserActivity", "Unable to get username", throwable);
                Toast.makeText(UserActivity.this, "Unable to get username", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void updateUserName() {
        String name = mBinding.userNameInput.getText().toString();
        mBinding.updateUser.setEnabled(false);
        mDisposable.add(mUserViewModel.updateUserName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                mBinding.updateUser.setEnabled(true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("UserActivity","Unable to update username",throwable);
                Toast.makeText(UserActivity.this, "Unable to update username", Toast.LENGTH_SHORT).show();
            }
        }));
    }


}
