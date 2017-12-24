package com.oubowu.ipanda1.arxjava;

import android.content.Context;

import com.oubowu.ipanda1.arxjava.persistence.LocalUserDataSource;
import com.oubowu.ipanda1.arxjava.persistence.UserDatabase;
import com.oubowu.ipanda1.arxjava.ui.ViewModelFactory;

/**
 * Created by Oubowu on 2017/12/17 20:04.
 */
public class Injection {

    public static UserDataSource provideUserDataSource(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }

}
