package com.example.ipanda1.arxjava.persistence;


import com.example.ipanda1.arxjava.UserDataSource;

import io.reactivex.Flowable;

/**
 * Created by Oubowu on 2017/12/17 20:24.
 */
public class LocalUserDataSource implements UserDataSource {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Flowable<MyUser> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public void insertOrUpdateUser(MyUser user) {
        mUserDao.insertUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
}
