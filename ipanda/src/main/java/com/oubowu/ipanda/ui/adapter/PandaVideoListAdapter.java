package com.oubowu.ipanda.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoListBinding;
import com.oubowu.ipanda.databinding.ItemFragmentPandaVideoListHeadBinding;
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
        if (viewType == TYPE_VIDEO_HEAD) {
            ItemFragmentPandaVideoListHeadBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_list_head, parent, false, mComponent);
            return mBinding;
        } else {
            ItemFragmentPandaVideoListBinding mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_panda_video_list, parent, false, mComponent);
            return mBinding;
        }
    }

    @Override
    protected void bind(ViewDataBinding binding, int position, RecordTab.VideoBean videoBean, int itemViewType) {
        if (itemViewType == TYPE_VIDEO_HEAD) {
            ItemFragmentPandaVideoListHeadBinding headBinding = (ItemFragmentPandaVideoListHeadBinding) binding;
            headBinding.setVideoBean(videoBean);
            setArrowEvent(headBinding);
        } else {
            ItemFragmentPandaVideoListBinding pandaVideoBinding = (ItemFragmentPandaVideoListBinding) binding;
            pandaVideoBinding.setVideoBean(videoBean);
            pandaVideoBinding.simpleVideoImageView.setVideoLength(videoBean.len);
            pandaVideoBinding.setEvent(new EventListenerAdapter() {
                @Override
                public void clickItemWithTitle(View v, String id, String title) {
                    // PandaVideoListActivity.start((Activity) v.getContext(), id, title);
                }
            });
        }
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
        if (position == 0) {
            return TYPE_VIDEO_HEAD;
        }
        return super.getItemViewType(position);
    }


    private void setArrowEvent(ItemFragmentPandaVideoListHeadBinding mBinding) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mBinding.liveDescArrow.getRotation(), mBinding.liveDescArrow.getRotation() + 180).setDuration(500);

        mBinding.setEvent(new EventListenerAdapter() {
            @Override
            public void clickArrow(View v) {
                super.clickArrow(v);
                boolean extend = (boolean) mBinding.liveDescArrow.getTag(-1);

                mBinding.liveDescArrow.setClickable(false);

                valueAnimator.setFloatValues(mBinding.liveDescArrow.getRotation(), mBinding.liveDescArrow.getRotation() + 180);

                valueAnimator.addUpdateListener(animation -> {
                    mBinding.liveDescArrow.setRotation((Float) animation.getAnimatedValue());
                    float fraction = animation.getAnimatedFraction();

                    if (!extend) {
                        mBinding.liveDesc.setHeight((int) (fraction * mBinding.liveDescFake.getHeight()));
                    } else {
                        mBinding.liveDesc.setHeight((int) ((1 - fraction) * mBinding.liveDescFake.getHeight()));
                    }
                });

                valueAnimator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        View viewRoot = mBinding.liveDesc;

                        int cx = 0;
                        int cy = 0;
                        int finalRadius = mBinding.summary.getWidth();
                        Animator anim;
                        if (!extend) {
                            anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
                        } else {
                            anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, finalRadius, 0);
                        }
                        viewRoot.setVisibility(View.VISIBLE);
                        anim.setDuration(500);
                        anim.start();

                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (!extend) {
                                    viewRoot.setVisibility(View.VISIBLE);
                                } else {
                                    viewRoot.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mBinding.liveDescArrow.setClickable(true);
                        valueAnimator.removeAllListeners();
                        valueAnimator.removeAllUpdateListeners();
                        mBinding.liveDescArrow.setTag(-1, !extend);
                    }
                });

                valueAnimator.start();
            }
        });
    }

}
