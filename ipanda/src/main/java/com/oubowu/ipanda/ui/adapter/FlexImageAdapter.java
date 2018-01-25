package com.oubowu.ipanda.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Oubowu on 2018/1/24 14:10.
 */
public abstract class FlexImageAdapter<T> extends BaseAdapter {

    private List<T> mList;

    public FlexImageAdapter(List<T> list) {
        mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
        initView(view, position, getItem(position));
        return view;
    }

    protected abstract int getItemLayoutId();

    protected abstract void initView(View view, int position, T item);

}
