package com.oubowu.ipanda1.arxjava.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Oubowu on 2017/12/17 20:05.
 */
@Database(entities = {MyUser.class},
        version = 1,
        exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase sInstance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (UserDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "Sample.db").build();
                }
            }
        }
        return sInstance;
    }

}
