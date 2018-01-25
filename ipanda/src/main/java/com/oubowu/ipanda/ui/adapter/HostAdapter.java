package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.base.HomeVideoGrid;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.databinding.ItemFragmentHostPandaeyeBinding;
import com.oubowu.ipanda.databinding.ItemFragmentHostVideoGridBinding;
import com.oubowu.ipanda.ui.widget.VideoImageView;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil;

/**
 * Created by Oubowu on 2018/1/15 15:56.
 */
public class HostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DataBindingComponent mDataBindingComponent;

    private HomeIndex mHomeIndex;

    public static final int TYPE_PANDA_NEWS = 1;
    public static final int TYPE_VIDEO_GRID = 2;
    public static final int TYPE_VIDEO_LIST = 3;

    public HostAdapter(DataBindingComponent dataBindingComponent) {
        mDataBindingComponent = dataBindingComponent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_PANDA_NEWS:
                ItemFragmentHostPandaeyeBinding binding = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_host_pandaeye, parent, false, mDataBindingComponent);

                DataBoundViewHolder<ItemFragmentHostPandaeyeBinding> viewHolder = new DataBoundViewHolder<>(binding);

                return viewHolder;
            case TYPE_VIDEO_GRID:
                // Log.e("xxx","48行-onCreateViewHolder(): "+" ");
                ItemFragmentHostVideoGridBinding binding1 = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_host_video_grid, parent, false, mDataBindingComponent);
                DataBoundViewHolder<ItemFragmentHostVideoGridBinding> viewHolder1 = new DataBoundViewHolder<>(binding1);
                return viewHolder1;
            case TYPE_VIDEO_LIST:

                break;
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_PANDA_NEWS:
                ItemFragmentHostPandaeyeBinding hostPandaeyeBinding = ((DataBoundViewHolder<ItemFragmentHostPandaeyeBinding>) holder).binding;

                hostPandaeyeBinding.marqueeLayout.setAdapter(new MarqueeLayoutAdapter<HomeIndex.PandaeyeBean.ItemsBean>(mHomeIndex.pandaeye.items) {
                    @Override
                    protected int getItemLayoutId() {
                        return R.layout.item_panda_eye_news;
                    }

                    @Override
                    protected void initView(View view, int position, HomeIndex.PandaeyeBean.ItemsBean item) {
                        TextView tvBrief = view.findViewById(R.id.tv_brief);
                        TextView tvTitle = view.findViewById(R.id.tv_title);
                        tvBrief.setText(item.brief);
                        tvTitle.setText(item.title);
                    }
                });
                hostPandaeyeBinding.marqueeLayout.start();

                hostPandaeyeBinding.setPandaeye(mHomeIndex.pandaeye);
                hostPandaeyeBinding.executePendingBindings();

                break;
            case TYPE_VIDEO_GRID:

                ItemFragmentHostVideoGridBinding hostVideoGridBinding = ((DataBoundViewHolder<ItemFragmentHostVideoGridBinding>) holder).binding;

                HomeVideoGrid homeVideoGrid = null;

                if (position == 1) {
                    homeVideoGrid = mHomeIndex.pandalive;
                } else if (position == 2) {
                    homeVideoGrid = mHomeIndex.chinalive;
                }

                if (homeVideoGrid != null) {

                    hostVideoGridBinding.setTitle(homeVideoGrid.title);

                    hostVideoGridBinding.flexImageLayout.setAdapter(new FlexImageAdapter<VideoList>(homeVideoGrid.list) {
                        @Override
                        protected int getItemLayoutId() {
                            return R.layout.item_host_video_grid;
                        }

                        @Override
                        protected void initView(View view, int position, VideoList item) {
                            VideoImageView videoImageView = view.findViewById(R.id.video_image_view);
                            videoImageView.setInfo("Live", item.title);
                            Glide.with(view.getContext()).load(item.image).into(videoImageView);
                        }
                    });

                    hostVideoGridBinding.executePendingBindings();

                }

                break;
            case TYPE_VIDEO_LIST:

                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mHomeIndex == null) {
            return 0;
        }
        int count = 0;
        count += (mHomeIndex.pandaeye == null ? 0 : 1);
        count += (mHomeIndex.pandalive == null ? 0 : 1);
        count += (mHomeIndex.chinalive == null ? 0 : 1);
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (position) {
            case 0:
                type = TYPE_PANDA_NEWS;
                break;
            case 4:
                type = TYPE_VIDEO_LIST;
                break;
            case 1:
            case 2:
            case 3:
                type = TYPE_VIDEO_GRID;
                break;
            default:
                break;
        }
        return type;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, 0);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, 0);
    }

    public void replace(HomeIndex data) {
        mHomeIndex = data;
        notifyDataSetChanged();
    }

    public HomeIndex getHomeIndex() {
        return mHomeIndex;
    }
}
