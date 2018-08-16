package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoListBinding;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/28 16:24.
 */
public class PandaVideoListAdapter extends DataBoundListAdapter<RecordTab.VideoBean, ViewDataBinding> {

    private DataBindingComponent mComponent;

    private static final int TYPE_VIDEO_HEAD = 1;

    public PandaVideoListAdapter(DataBindingComponent component) {
        mComponent = component;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        //        if (viewType == TYPE_VIDEO_HEAD) {
        //            ItemFragmentPandaVideoListHeadBinding mBinding = DataBindingUtil
        //                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_list_head, parent, false, mComponent);
        //            return mBinding;
        //        } else {
        ItemFragmentPandaVideoListBinding mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_list, parent, false, mComponent);
        return mBinding;
        //        }
    }

    @Override
    protected void bind(ViewDataBinding binding, int position, RecordTab.VideoBean videoBean, int itemViewType) {
        //        if (itemViewType == TYPE_VIDEO_HEAD) {
        //            ItemFragmentPandaVideoListHeadBinding headBinding = (ItemFragmentPandaVideoListHeadBinding) binding;
        //            headBinding.setVideoBean(videoBean);
        //            setArrowEvent(headBinding);
        //        } else {
        ItemFragmentPandaVideoListBinding pandaVideoBinding = (ItemFragmentPandaVideoListBinding) binding;
        pandaVideoBinding.setVideoBean(videoBean);
        pandaVideoBinding.simpleVideoImageView.setVideoLength(videoBean.len);
        pandaVideoBinding.setEvent(new EventListenerAdapter() {
            @Override
            public void clickItemWithTitle(View v, String id, String title) {
                // PandaVideoListActivity.start((Activity) v.getContext(), id, title);
            }
        });
        //        }
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
//        if (position == 0) {
//            return TYPE_VIDEO_HEAD;
//        }
        return super.getItemViewType(position);
    }

}
