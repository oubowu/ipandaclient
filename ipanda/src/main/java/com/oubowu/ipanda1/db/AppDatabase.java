package com.oubowu.ipanda1.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.oubowu.ipanda1.AppExecutors;
import com.oubowu.ipanda1.db.converter.DateConverter;
import com.oubowu.ipanda1.db.dao.CommentDao;
import com.oubowu.ipanda1.db.dao.ProductDao;
import com.oubowu.ipanda1.db.entity.CommentEntity;
import com.oubowu.ipanda1.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/14 10:33.
 */
@Database(entities = {ProductEntity.class, CommentEntity.class},
        version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample.db";

    public abstract ProductDao productDao();

    public abstract CommentDao commentDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    Log.e("AppDatabase","47行-getInstance(): "+" ");
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context applicationContext, final AppExecutors executors) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.disIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // 延时模拟长时间操作
                        addDelay();
                        // buildDatabase先创建了AppDatabase在回调再调用getInstance，不会造成getInstance调用buildDatabase，buildDatabase调getInstance无限循环
                        AppDatabase database = AppDatabase.getInstance(applicationContext, executors);
                        List<ProductEntity> products = DataGenerator.generateProducts();
                        List<CommentEntity> comments = DataGenerator.generateCommentsForProducts(products);
                        insertData(database, products, comments);
                        // 告知数据库已经被创建并且可以被使用了
                        database.setDatabaseCreated();
                    }
                });
            }
        }).build();
    }

    private void updateDatabaseCreated(Context applicationContext) {
        if (applicationContext.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getIsDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static void insertData(final AppDatabase database, final List<ProductEntity> products, final List<CommentEntity> comments) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.productDao().insertAll(products);
                database.commentDao().insertAll(comments);
            }
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

}
