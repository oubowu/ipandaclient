package com.oubowu.ipanda.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.oubowu.ipanda.bean.TabIndex;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/31 12:49.
 */
@Dao
public interface TabIndexDao {

    @Query("select * from tab_index")
    LiveData<List<TabIndex>> queryTabIndexes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTabIndexes(List<TabIndex> tabIndexes);

}
