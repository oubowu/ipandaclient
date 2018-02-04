package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.base.HomeVideo;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.databinding.ItemFragmentHostPandaeyeBinding;
import com.oubowu.ipanda.databinding.ItemFragmentHostVideoGridBinding;
import com.oubowu.ipanda.databinding.ItemFragmentHostVideoListBinding;
import com.oubowu.ipanda.ui.widget.SimpleVideoImageView;
import com.oubowu.ipanda.ui.widget.VideoImageView;
import com.oubowu.ipanda.util.CommonUtil;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;

/**
 * Created by Oubowu on 2018/1/15 15:56.
 */
public class HostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DataBindingComponent mDataBindingComponent;

    private HomeIndex mHomeIndex;

    public static final int TYPE_PANDA_NEWS = 2;
    public static final int TYPE_VIDEO_GRID = 3;
    public static final int TYPE_VIDEO_LIST = 4;

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
                ItemFragmentHostVideoListBinding binding2 = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_host_video_list, parent, false, mDataBindingComponent);
                DataBoundViewHolder<ItemFragmentHostVideoListBinding> viewHolder2 = new DataBoundViewHolder<>(binding2);
                return viewHolder2;
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
                        tvBrief.setTextColor(Color.parseColor(item.bgcolor));
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

                HomeVideo homeVideo = null;


                if (position == 1) {
                    homeVideo = mHomeIndex.pandalive;
                } else if (position == 2) {
                    homeVideo = mHomeIndex.chinalive;
                } else if (position == 3) {
                    homeVideo = mHomeIndex.cctv;
                }

                if (homeVideo != null) {

                    hostVideoGridBinding.setTitle(homeVideo.title);

                    hostVideoGridBinding.flexImageLayout.setAdapter(new FlexImageAdapter<VideoList>(homeVideo.list) {
                        @Override
                        protected int getItemLayoutId() {
                            return R.layout.item_host_video_grid;
                        }

                        @Override
                        protected void initView(View view, int p, VideoList item) {
                            VideoImageView videoImageView = view.findViewById(R.id.video_image_view);
                            if (holder.getAdapterPosition() == 3) {
                                videoImageView.setInfo(item.videoLength, item.title, item.daytime);
                            } else {
                                videoImageView.setInfo("Live", item.title, item.daytime);
                            }
                            Glide.with(view.getContext()).load(item.image).into(videoImageView);
                        }
                    });

                    hostVideoGridBinding.executePendingBindings();

                }

                break;
            case TYPE_VIDEO_LIST:
                homeVideo = mHomeIndex.list.get(0);

                ItemFragmentHostVideoListBinding hostVideoListBinding = ((DataBoundViewHolder<ItemFragmentHostVideoListBinding>) holder).binding;

                hostVideoListBinding.setTitle(homeVideo.title);

                hostVideoListBinding.verticalLayout.setAdapter(new VerticalAdapter<VideoList>(homeVideo.list) {
                    @Override
                    protected int getItemLayoutId() {
                        return R.layout.item_host_video_list;
                    }

                    @Override
                    protected void initView(View view, int position, VideoList item) {
                        SimpleVideoImageView simpleVideoImageView = view.findViewById(R.id.simpleVideoImageView);
                        simpleVideoImageView.setVideoLength(item.videoLength);
                        Glide.with(view.getContext()).load(item.image).into(simpleVideoImageView);

                        TextView title = view.findViewById(R.id.tv_title);
                        TextView date = view.findViewById(R.id.tv_date);
                        title.setText(item.title);
                        date.setText(item.daytime);
                    }
                });

                hostVideoListBinding.executePendingBindings();

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
        count += (mHomeIndex.pandaeye != null ? 1 : 0);
        count += (mHomeIndex.pandalive != null ? 1 : 0);
        count += (mHomeIndex.chinalive != null ? 1 : 0);
        count += (mHomeIndex.cctv != null && CommonUtil.isNotEmpty(mHomeIndex.cctv.list) ? 1 : 0);
        count += (CommonUtil.isNotEmpty(mHomeIndex.list) && CommonUtil.isNotEmpty(mHomeIndex.list.get(0).list) ? 1 : 0);
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (position) {
            case 0:
                type = TYPE_PANDA_NEWS;
                break;
            case 1:
            case 2:
            case 3:
                type = TYPE_VIDEO_GRID;
                break;
            case 4:
                type = TYPE_VIDEO_LIST;
                break;
            default:
                break;
        }
        return type;
    }

    public void replace(HomeIndex data) {
        mHomeIndex = data;
        notifyDataSetChanged();
    }

    public HomeIndex getHomeIndex() {
        return mHomeIndex;
    }
}
