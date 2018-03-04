package com.oubowu.ipanda.ui.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaBroadcastBinding;
import com.oubowu.ipanda.databinding.ItemFragmentPandaBroadcastHeadBinding;
import com.oubowu.ipanda.ui.VideoActivity;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/28 16:24.
 */
public class PandaBroadcastAdapter extends DataBoundListAdapter<PandaBroadcastList.ListBean, ViewDataBinding> {

    private static final int TYPE_BIG_IMG = 1;

    private android.databinding.DataBindingComponent mComponent;

    public PandaBroadcastAdapter(android.databinding.DataBindingComponent component) {
        mComponent = component;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BIG_IMG) {
            ItemFragmentPandaBroadcastHeadBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_broadcast_head, parent, false, mComponent);
            return mBinding;
        } else {
            ItemFragmentPandaBroadcastBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_broadcast, parent, false, mComponent);
            return mBinding;
        }
    }

    @Override
    protected void bind(ViewDataBinding binding, int position, PandaBroadcastList.ListBean listBean, int itemViewType) {
        if (itemViewType == TYPE_BIG_IMG) {
            ItemFragmentPandaBroadcastHeadBinding videoHeadBinding = (ItemFragmentPandaBroadcastHeadBinding) binding;
            videoHeadBinding.setVideoBean(listBean);
            videoHeadBinding.setEvent(new EventListenerAdapter() {
                @Override
                public void clickBigImg(View v, String id) {
                    Log.e("PandaBroadcastAdapter","53行-clickBigImg(): "+" ");
                    VideoActivity.start((Activity) v.getContext(), v, id);
                }
            });
        } else {
            ItemFragmentPandaBroadcastBinding pandaVideoBinding = (ItemFragmentPandaBroadcastBinding) binding;
            pandaVideoBinding.setVideoBean(listBean);
            pandaVideoBinding.setEvent(new EventListenerAdapter() {
                @Override
                public void clickItem(View v, String id) {
                    // TODO: 2018/3/4 跳转网页
                    Log.e("PandaBroadcastAdapter","64行-clickItem(): "+" ");
                }
            });
        }
    }

    @Override
    protected boolean areItemsTheSame(PandaBroadcastList.ListBean oldItem, PandaBroadcastList.ListBean newItem) {
        return CommonUtil.equals(oldItem.id, newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(PandaBroadcastList.ListBean oldItem, PandaBroadcastList.ListBean newItem) {
        return CommonUtil.equals(oldItem.id, newItem.id);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BIG_IMG;
        }
        return super.getItemViewType(position);
    }
}
