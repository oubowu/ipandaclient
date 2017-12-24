package com.oubowu.daggerpractice.aacdemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.oubowu.daggerpractice.aacdemo.ExecutorServiceManager;
import com.oubowu.daggerpractice.aacdemo.bean.User;

import java.util.concurrent.TimeUnit;

/**
 * Created by Oubowu on 2017/12/8 10:26.
 */
public class UserViewModel extends ViewModel {

    private String mId = "";

    private LiveData<User> mUserLiveData;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public LiveData<User> getUserLiveData() {
        final MutableLiveData<User> data = new MutableLiveData<>();
        ExecutorServiceManager.getInstance().execute(new Runnable() {

            int mInt = 5;

            @Override
            public void run() {
                while (mInt != 0) {
                    mInt--;
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    User user = new User();
                    user.setName("oubowu"+mInt);
                    user.setPassword("123456"+mInt);
                    Log.e("oubowu", "child thread...");
                    // LiveData 在 "data.postValue(user)" 后,获取到了 user 这个数据,分发到它的监听器,因为 Activity 监听了,所以 button 的 text 就自动改变了
                    data.postValue(user);
                }
            }
        });

        return mUserLiveData = data;
    }

    public void setUserLiveData(LiveData<User> userLiveData) {
        mUserLiveData = userLiveData;
    }
}
