package com.example.ipanda1.arxjava.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Oubowu on 2017/12/17 20:05.
 */
@Entity(tableName = "users")
public class MyUser {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userId")
    private String id;

    @ColumnInfo(name = "userName")
    private String userName;

    @NonNull
    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Ignore
    public MyUser(String userName) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
    }

    public MyUser(@NonNull String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

}
