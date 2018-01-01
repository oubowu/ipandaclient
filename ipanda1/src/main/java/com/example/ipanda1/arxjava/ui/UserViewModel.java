package com.example.ipanda1.arxjava.ui;

import android.arch.lifecycle.ViewModel;

import com.example.ipanda1.arxjava.UserDataSource;
import com.example.ipanda1.arxjava.persistence.MyUser;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromAction;

/**
 * Created by Oubowu on 2017/12/17 20:28.
 */
public class UserViewModel extends ViewModel {

    private final UserDataSource mDataSource;

    private MyUser mUser;

    public UserViewModel(UserDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<String> getUserName() {
        return mDataSource.getUser().map(new Function<MyUser, String>() {
            @Override
            public String apply(MyUser user) throws Exception {
                mUser = user;
                return mUser.getUserName();
            }
        });
    }

    // 我们可以认为Completable对象是Observable的简化版本，他仅仅发射onError和onCompleted这两个终端事件。
    // 他看上去像Observable.empty()的一个具体类但是又不像empty(),Completable是一个活动的类。
    public Completable updateUserName(final String userName) {
        return new CompletableFromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUser = mUser == null ? new MyUser(userName) : new MyUser(mUser.getId(), userName);
                mDataSource.insertOrUpdateUser(mUser);
            }
        });
    }

}
