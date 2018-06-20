package com.oubowu.ipanda.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.base.RecordVideo;
import com.oubowu.ipanda.callback.VideoCallback;
import com.oubowu.ipanda.databinding.ActivityVideoBinding;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.util.ToastUtil;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class VideoActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    public static final String PID = "pid";

    public final static String IMG_TRANSITION = "imgTransition";
    private static final String IS_LIVE_VIDEO = "isLiveVideo";
    private static final String LIVE_ID = "liveId";
    private static final String LIVE_TITLE = "liveTitle";

    private ActivityVideoBinding mBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private OrientationUtils mOrientationUtils;

    private Transition mTransition;

    public static void start(Activity activity, View view, String pid) {
        Intent intent = new Intent(activity, VideoActivity.class).putExtra(PID, pid);

        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(view, "myButton4")).toBundle());

        // activity.startActivity(intent);
    }

    public static void start(Activity activity, View view, String id, String title) {
        Intent intent = new Intent(activity, VideoActivity.class).putExtra(LIVE_ID, id).putExtra(LIVE_TITLE, title);

        //        Pair pair = new Pair<>(view, VideoActivity.IMG_TRANSITION);
        //        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
        //        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());

        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(view, "myButton4")).toBundle());

        //        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.setStatusBarColor(this, R.color.black);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);

        //进入退出效果 注意这里 创建的效果对象是 Explode()
        getWindow().setEnterTransition(new Fade().setDuration(300));
        getWindow().setExitTransition(new Fade().setDuration(300));

        /*Glide.with(getApplicationContext()).setDefaultRequestOptions(new RequestOptions().centerCrop().error(R.drawable.ic_panda_loading).placeholder(R.drawable.ic_panda_loading)).load("http://p1.img.cctvpic.com/photoAlbum/page/performance/img/2018/4/8/1523162530651_241.jpg")
                .into(mBinding.thumbImage);*/

        String pid = getIntent().getStringExtra(PID);
        String liveId = getIntent().getStringExtra(LIVE_ID);
        String liveTitle = getIntent().getStringExtra(LIVE_TITLE);

        boolean isLiveVideo = liveTitle != null;

        VideoViewModel videoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);

        if (isLiveVideo) {

            mBinding.videoView.findViewById(com.shuyu.gsyvideoplayer.R.id.progress).setVisibility(View.INVISIBLE);
            mBinding.videoView.findViewById(com.shuyu.gsyvideoplayer.R.id.total).setVisibility(View.INVISIBLE);
            mBinding.videoView.findViewById(com.shuyu.gsyvideoplayer.R.id.bottom_progressbar).setAlpha(0);
            mBinding.videoView.setLive(true);

            getLiveVideo(liveId, liveTitle, videoViewModel);

        } else {

            getRecordVideo(pid, videoViewModel);

        }


    }

    private void getRecordVideo(String pid, VideoViewModel videoViewModel) {
        videoViewModel.getRecordVideo(pid).observe(this, new ObserverImpl<RecordVideo>() {
            @Override
            protected void onSuccess(@NonNull RecordVideo data) {

                mBinding.videoView.setStandardVideoAllCallBack(new VideoCallback() {
                    @Override
                    public void onPlayError(String url, Object... objects) {
                        super.onPlayError(url, objects);
                        Log.e("VideoActivity", "154行-onPlayError(): " + url);
                        Log.e("VideoActivity", "155行-onPlayError(): " + objects[0] + ";" + objects[1]);
                        ToastUtil.showErrorMsg("播放视频异常");
                    }
                });

                mBinding.setVideoName(data.title);
                mBinding.setVideoPath(data.hls_url);


                // Log.e("VideoActivity", "53行-onCreate(): " + recordVideo.hls_url);
                mBinding.videoView.setUp(data.video.chapters.get(0).url, true, data.title);

                //增加title
                mBinding.videoView.getTitleTextView().setVisibility(View.VISIBLE);
                //videoPlayer.setShowPauseCover(false);

                //videoPlayer.setSpeed(2f);

                //设置返回键
                mBinding.videoView.getBackButton().setVisibility(View.VISIBLE);

                //设置旋转
                mOrientationUtils = new OrientationUtils(VideoActivity.this, mBinding.videoView);

                //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
                mBinding.videoView.getFullscreenButton().setOnClickListener(v -> mOrientationUtils.resolveByClick());

                //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
                //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
                //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
                //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
                //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
                //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

                //是否可以滑动调整
                mBinding.videoView.setIsTouchWiget(true);

                //设置返回按键功能
                mBinding.videoView.getBackButton().setOnClickListener(v -> onBackPressed());

                mBinding.videoView.startPlayLogic();

            }
        });
    }

    private void getLiveVideo(String liveId, String liveTitle, VideoViewModel videoViewModel) {
        videoViewModel.getLiveVideo(liveId).observe(this, new ObserverImpl<LiveVideo>() {
            @Override
            protected void onSuccess(@NonNull LiveVideo data) {

                mBinding.videoView.setStandardVideoAllCallBack(new VideoCallback() {
                    @Override
                    public void onPlayError(String url, Object... objects) {
                        super.onPlayError(url, objects);
                        Log.e("VideoActivity", "154行-onPlayError(): " + url);
                        Log.e("VideoActivity", "155行-onPlayError(): " + objects[0] + ";" + objects[1]);
                        ToastUtil.showErrorMsg("播放视频异常");
                    }
                });

                mBinding.setVideoName("");
                mBinding.setVideoPath(data.hls_url.hls1);

                // Log.e("VideoActivity", "53行-onCreate(): " + recordVideo.hls_url);
                mBinding.videoView.setUp(data.hls_url.hls1, false, liveTitle);

                //增加title
                mBinding.videoView.getTitleTextView().setVisibility(View.VISIBLE);
                //videoPlayer.setShowPauseCover(false);

                //videoPlayer.setSpeed(2f);

                //设置返回键
                mBinding.videoView.getBackButton().setVisibility(View.VISIBLE);

                //设置旋转
                mOrientationUtils = new OrientationUtils(VideoActivity.this, mBinding.videoView);

                //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
                mBinding.videoView.getFullscreenButton().setOnClickListener(v -> mOrientationUtils.resolveByClick());

                //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
                //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
                //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
                //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
                //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
                //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

                //是否可以滑动调整
                mBinding.videoView.setIsTouchWiget(true);

                //设置返回按键功能
                mBinding.videoView.getBackButton().setOnClickListener(v -> onBackPressed());

                mBinding.videoView.startPlayLogic();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.videoView.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.videoView.onVideoResume();
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
        //先返回正常状态
        if (mOrientationUtils != null && mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mBinding.videoView.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        mBinding.videoView.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        super.onBackPressed();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }


}
