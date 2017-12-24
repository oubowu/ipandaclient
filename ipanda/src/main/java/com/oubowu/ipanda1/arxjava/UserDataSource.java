package com.oubowu.ipanda1.arxjava;

import com.oubowu.ipanda1.arxjava.persistence.MyUser;

import io.reactivex.Flowable;

/**
 * Created by Oubowu on 2017/12/17 20:24.
 */
public interface UserDataSource {

    Flowable<MyUser> getUser();

    void insertOrUpdateUser(MyUser user);

    void deleteAllUsers();

}
