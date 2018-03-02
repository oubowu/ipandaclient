package com.oubowu.ipanda.ui.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoBinding;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoHeadBinding;
import com.oubowu.ipanda.ui.VideoActivity;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/28 16:24.
 */
public class PandaVideoAdapter extends DataBoundListAdapter<PandaVideoIndex.ListBean, ViewDataBinding> {

    private static final int TYPE_BIG_IMG = 1;

    private android.databinding.DataBindingComponent mComponent;

    public PandaVideoAdapter(android.databinding.DataBindingComponent component) {
        mComponent = component;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BIG_IMG) {
            ItemFragmentPandaVideoHeadBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_head, parent, false, mComponent);
            return mBinding;
        } else {
            ItemFragmentPandaVideoBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video, parent, false, mComponent);
            return mBinding;
        }
    }

    @Override
    protected void bind(ViewDataBinding binding, int position, PandaVideoIndex.ListBean listBean, int itemViewType) {
        if (itemViewType == TYPE_BIG_IMG) {
            ItemFragmentPandaVideoHeadBinding videoHeadBinding = (ItemFragmentPandaVideoHeadBinding) binding;
            videoHeadBinding.setVideoBean(listBean);
            videoHeadBinding.setEvent(new EventListenerAdapter(){
                @Override
                public void clickBigImg(View v, String id) {
                    VideoActivity.start((Activity) v.getContext(), v, id);
                }
            });
        } else {
            ItemFragmentPandaVideoBinding pandaVideoBinding = (ItemFragmentPandaVideoBinding) binding;
            pandaVideoBinding.setVideoBean(listBean);
            pandaVideoBinding.setEvent(new EventListenerAdapter(){
                @Override
                public void clickItem(View v, String id) {

                }
            });
        }
    }

    @Override
    protected boolean areItemsTheSame(PandaVideoIndex.ListBean oldItem, PandaVideoIndex.ListBean newItem) {
        return CommonUtil.equals(oldItem.id, newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(PandaVideoIndex.ListBean oldItem, PandaVideoIndex.ListBean newItem) {
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
