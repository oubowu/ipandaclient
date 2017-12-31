package com.oubowu.ipanda.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.db.dao.TabIndexDao;

/**
 * Created by Oubowu on 2017/12/31 12:26.
 */
@Database(entities = {TabIndex.class},
        version = 1)
public abstract class IpandaDb extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "ipanda.db";

    public static IpandaDb buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), IpandaDb.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Logger.e("数据库创建了");
            }
        }).build();
    }

    public abstract TabIndexDao tabIndexDao();

}
