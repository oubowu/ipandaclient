package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.callback.EventListener;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoListBinding;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/28 16:24.
 */
public class PandaVideoListAdapter extends DataBoundListAdapter<RecordTab.VideoBean, ItemFragmentPandaVideoListBinding> {

    private DataBindingComponent mComponent;

    private EventListener mEventListener;

    public PandaVideoListAdapter(DataBindingComponent component) {
        mComponent = component;
    }

    @Override
    protected ItemFragmentPandaVideoListBinding createBinding(ViewGroup parent, int viewType) {
        ItemFragmentPandaVideoListBinding mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_list, parent, false, mComponent);
        return mBinding;
    }

    @Override
    protected void bind(ItemFragmentPandaVideoListBinding binding, int position, RecordTab.VideoBean videoBean, int itemViewType) {
        binding.setVideoBean(videoBean);
        binding.simpleVideoImageView.setVideoLength(videoBean.len);
        binding.setEvent(new EventListenerAdapter() {
            @Override
            public void clickItemWithTitle(View v, String id, String title) {
                // PandaVideoListActivity.start((Activity) v.getContext(), id, title);
                if (mEventListener!=null){
                    mEventListener.clickItemWithTitle(v, id, title);
                }
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(RecordTab.VideoBean oldItem, RecordTab.VideoBean newItem) {
        return CommonUtil.equals(oldItem.vid, newItem.vid);
    }

    @Override
    protected boolean areContentsTheSame(RecordTab.VideoBean oldItem, RecordTab.VideoBean newItem) {
        return CommonUtil.equals(oldItem.vid, newItem.vid);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }
}
