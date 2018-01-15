package com.oubowu.ipanda.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oubowu on 2018/1/15 16:00.
 */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<DataBoundViewHolder<V>> {

    @Nullable
    private List<T> mItems;

    private int mDataVersion;

    @Override
    public DataBoundViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        V binding = createBinding(parent);
        return new DataBoundViewHolder<>(binding);
    }

    protected abstract V createBinding(ViewGroup parent);

    @Override
    public void onBindViewHolder(DataBoundViewHolder<V> holder, int position) {
        bind(holder.binding, mItems.get(position));
        holder.binding.executePendingBindings();
    }

    protected abstract void bind(V binding, T t);

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @MainThread
    public void replace(List<T> newItems) {
        mDataVersion++;
        if (mItems == null) {
            if (newItems == null) {
                return;
            }
            mItems = newItems;
            notifyDataSetChanged();
        } else if (newItems == null) {
            int oldSize = mItems.size();
            mItems = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            int startVersion = mDataVersion;
            List<T> oldItems = mItems;
            Observable //
                    .create((ObservableOnSubscribe<DiffUtil.DiffResult>) e -> {
                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                            @Override
                            public int getOldListSize() {
                                return oldItems.size();
                            }

                            @Override
                            public int getNewListSize() {
                                return newItems.size();
                            }

                            @Override
                            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                                T oldItem = oldItems.get(oldItemPosition);
                                T newItem = newItems.get(newItemPosition);
                                return DataBoundListAdapter.this.areItemsTheSame(oldItem, newItem);
                            }

                            @Override
                            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                                T oldItem = oldItems.get(oldItemPosition);
                                T newItem = newItems.get(newItemPosition);
                                return DataBoundListAdapter.this.areContentsTheSame(oldItem, newItem);
                            }
                        });
                        e.onNext(diffResult);
                    })//
                    .subscribeOn(Schedulers.io())//
                    .observeOn(AndroidSchedulers.mainThread())//
                    .subscribe(diffResult -> {
                        if (startVersion != mDataVersion) {
                            return;
                        }
                        mItems = newItems;
                        diffResult.dispatchUpdatesTo(DataBoundListAdapter.this);
                    }, throwable -> {

                    });
        }
    }

    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

}
