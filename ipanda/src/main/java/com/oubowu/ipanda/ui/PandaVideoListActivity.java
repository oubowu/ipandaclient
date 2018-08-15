package com.oubowu.ipanda.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.base.RecordVideo;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.databinding.ActivityPandaVideoListBinding;
import com.oubowu.ipanda.ui.adapter.ActivityDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.PandaVideoListAdapter;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.viewmodel.PandaVideoListViewModel;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PandaVideoListActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String ID = "id";

    private static final String TITLE = "title";

    @Inject
    ViewModelProvider.Factory mFactory;

    private PandaVideoListViewModel mPandaVideoListViewModel;
    private VideoViewModel mVideoViewModel;

    private ActivityPandaVideoListBinding mBinding;
    private OrientationUtils mOrientationUtils;
    private PandaVideoListAdapter mPandaVideoListAdapter;

    public static void start(Activity activity, String id, String title) {
        Intent intent = new Intent(activity, PandaVideoListActivity.class).putExtra(ID, id).putExtra(TITLE, title);

        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
        //        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_panda_video_list);

        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBarColorLollipop);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark1);
            StatusBarUtil.StatusBarLightMode(this, 3);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_panda_video_list);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setContentInsetStartWithNavigation(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String id = getIntent().getStringExtra(ID);
        String title = getIntent().getStringExtra(TITLE);

        mBinding.setTitle(title.replace("《", "").replace("》", ""));

//        mBinding.liveDescArrow.setTag(-1, false);

        initRecyclerView();

        mPandaVideoListViewModel = ViewModelProviders.of(this, mFactory).get(PandaVideoListViewModel.class);
        mVideoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);

        getRecordTab(id);

    }

    private void getRecordTab(String id) {
        mPandaVideoListViewModel.getRecordTab(id, 10, 0).observe(this, new ObserverImpl<RecordTab>() {
            @Override
            protected void onSuccess(@NonNull RecordTab data) {

                getRecordVideo(data);

                RecordTab.VideosetBean videoset = data.videoset;
                RecordTab.VideoBean videoBean = new RecordTab.VideoBean();
                videoBean.img = videoset._$0.img;
                videoBean.t = videoset._$0.desc;
                data.video.add(0, videoBean);

                mPandaVideoListAdapter.replace(data.video);

            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPandaVideoListAdapter = new PandaVideoListAdapter(new ActivityDataBindingComponent());

        mBinding.recyclerView.setAdapter(mPandaVideoListAdapter);

        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            Drawable drawable = ContextCompat.getDrawable(PandaVideoListActivity.this, R.drawable.divider_panda_live_fragment);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.top = drawable.getIntrinsicHeight();
                outRect.left = drawable.getIntrinsicHeight();
                outRect.right = drawable.getIntrinsicHeight();
                if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = drawable.getIntrinsicHeight();
                }
            }
        });
    }

    private void getRecordVideo(@NonNull RecordTab data) {
        if (CommonUtil.isNotEmpty(data.video)) {
            mVideoViewModel.getRecordVideo(data.video.get(0).vid).observe(PandaVideoListActivity.this, new ObserverImpl<RecordVideo>() {
                @Override
                protected void onSuccess(@NonNull RecordVideo data) {
//                    mBinding.coverVideoPlayer.setUp(data.video.chapters.get(0).url, true, data.title);
//
//                    //增加title
//                    mBinding.coverVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//
//                    //设置返回键
//                    mBinding.coverVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
//
//                    //设置旋转
//                    mOrientationUtils = new OrientationUtils(PandaVideoListActivity.this, mBinding.coverVideoPlayer);
//
//                    //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//                    mBinding.coverVideoPlayer.getFullscreenButton().setOnClickListener(v -> mOrientationUtils.resolveByClick());
//
//                    //是否可以滑动调整
//                    mBinding.coverVideoPlayer.setIsTouchWiget(false);
//
//                    //设置返回按键功能
//                    mBinding.coverVideoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
//
//                    mBinding.coverVideoPlayer.startPlayLogic();
//
//                    mBinding.coverVideoPlayer.setStandardVideoAllCallBack(new VideoCallback() {
//                        @Override
//                        public void onPlayError(String url, Object... objects) {
//                            ToastUtil.showErrorMsg("播放视频异常");
//                        }
//                    });
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mBinding.coverVideoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mBinding.coverVideoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
//        //先返回正常状态
//        if (mOrientationUtils != null && mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            mBinding.coverVideoPlayer.getFullscreenButton().performClick();
//            return;
//        }
//        //释放所有
//        mBinding.coverVideoPlayer.setStandardVideoAllCallBack(null);
//        GSYVideoPlayer.releaseAllVideos();
        super.onBackPressed();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
