package com.oubowu.ipanda.ui.adapter;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoBinding;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoHeadBinding;
import com.oubowu.ipanda.ui.PandaVideoListActivity;
import com.oubowu.ipanda.ui.VideoActivity;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/28 16:24.
 */
public class PandaVideoAdapter extends DataBoundListAdapter<PandaVideoIndex.ListBean, ViewDataBinding> {

    private static final int TYPE_BIG_IMG = 1;

    private android.databinding.DataBindingComponent mComponent;

    public PandaVideoAdapter(DataBindingComponent component) {
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
            videoHeadBinding.setEvent(new EventListenerAdapter() {
                @Override
                public void clickBigImg(View v, String id) {
                    Log.e("PandaVideoAdapter", "53行-clickBigImg(): " + " ");
                    VideoActivity.start((Activity) v.getContext(), v, id);
                }
            });
        } else {
            ItemFragmentPandaVideoBinding pandaVideoBinding = (ItemFragmentPandaVideoBinding) binding;
            pandaVideoBinding.setVideoBean(listBean);
            pandaVideoBinding.simpleVideoImageView.setVideoLength(listBean.videoLength);
            pandaVideoBinding.setEvent(new EventListenerAdapter() {
//                @Override
//                public void clickItem(View v, String id) {
//                    // TODO: 2018/3/4 跳转另外一个列表
//                    Log.e("PandaVideoAdapter", "63行-clickItem(): " + " ");
//                    //                    if (mComponent instanceof FragmentDataBindingComponent){
//                    //                        ((FragmentDataBindingComponent) mComponent).getFragment().getChildFragmentManager()
//                    //                                .beginTransaction()
//                    //                                .replace(R.id.cccc, PandaVideoListFragment.newInstance(id, ""))
//                    //                                .commitAllowingStateLoss();
//                    //                    }
//                    PandaVideoListActivity.start((Activity) v.getContext(), id, "视频列表");
//                }

                @Override
                public void clickItemWithTitle(View v, String id, String title) {
                    PandaVideoListActivity.start((Activity) v.getContext(), id, title);
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
