package com.example.ipanda1.test;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ipanda1.BR;


/**
 * Created by Oubowu on 2017/12/12 14:13.
 */
public class Obs extends BaseObservable{

    private String title;
    private String subTitle;

    public Obs(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        notifyPropertyChanged(BR.subTitle);
    }
}
