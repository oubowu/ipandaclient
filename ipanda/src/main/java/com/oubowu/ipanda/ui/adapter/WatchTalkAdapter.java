package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.WatchTalk;
import com.oubowu.ipanda.databinding.ItemFragmentPandaliveSubWatchTalkBinding;
import com.oubowu.ipanda.util.CommonUtil;

import java.util.List;

/**
 * Created by Oubowu on 2018/2/23 16:54.
 */
public class WatchTalkAdapter extends DataBoundListAdapter<WatchTalk.DataBean.ContentBean, ItemFragmentPandaliveSubWatchTalkBinding> {

    private long mTotal;

    public void replace(List<WatchTalk.DataBean.ContentBean> newItems, long total) {
        super.replace(newItems);
        mTotal = total;
    }

    @Override
    protected ItemFragmentPandaliveSubWatchTalkBinding createBinding(ViewGroup parent, int viewType) {
        ItemFragmentPandaliveSubWatchTalkBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_pandalive_sub_watch_talk, parent, false);
        return binding;
    }

    @Override
    protected void bind(ItemFragmentPandaliveSubWatchTalkBinding binding, int position, WatchTalk.DataBean.ContentBean contentBean, int itemViewType) {
        binding.setContent(contentBean);
        binding.setFloorCount(mTotal - position);
    }

    @Override
    protected boolean areItemsTheSame(WatchTalk.DataBean.ContentBean oldItem, WatchTalk.DataBean.ContentBean newItem) {
        return CommonUtil.equals(oldItem.pid, newItem.pid);
    }

    @Override
    protected boolean areContentsTheSame(WatchTalk.DataBean.ContentBean oldItem, WatchTalk.DataBean.ContentBean newItem) {
        return CommonUtil.equals(oldItem.pid, newItem.pid) && CommonUtil.equals(oldItem.message, newItem.message);
    }

}
