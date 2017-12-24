package com.oubowu.ipanda1.arxjava.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;

/**
 * Created by Oubowu on 2017/12/17 20:11.
 */
@Dao
public interface UserDao {

    @Query("select * from users limit 1")
    Flowable<MyUser> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(MyUser user);

    @Query("delete from users")
    void deleteAllUsers();

}
