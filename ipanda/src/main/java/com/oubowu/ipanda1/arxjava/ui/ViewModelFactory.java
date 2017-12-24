package com.oubowu.ipanda1.arxjava.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.oubowu.ipanda1.arxjava.UserDataSource;

/**
 * Created by Oubowu on 2017/12/17 20:58.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserDataSource mDataSource;


    public ViewModelFactory(UserDataSource dataSource) {
        mDataSource = dataSource;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if (aClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknow ViewModel Class");
    }
}
