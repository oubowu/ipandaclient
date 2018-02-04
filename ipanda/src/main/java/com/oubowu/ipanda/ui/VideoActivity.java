package com.oubowu.ipanda.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.base.RecordVideo;
import com.oubowu.ipanda.databinding.ActivityVideoBinding;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import javax.inject.Inject;

public class VideoActivity extends AppCompatActivity {

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

        //        Pair pair = new Pair<>(view, VideoActivity.IMG_TRANSITION);
        //        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
        //        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());

        activity.startActivity(intent);
    }

    public static void start(Activity activity, View view, String id, String title) {
        Intent intent = new Intent(activity, VideoActivity.class).putExtra(LIVE_ID, id).putExtra(LIVE_TITLE, title);

        //        Pair pair = new Pair<>(view, VideoActivity.IMG_TRANSITION);
        //        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
        //        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.setStatusBarColor(this, R.color.black);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);

        String pid = getIntent().getStringExtra(PID);
        String liveId = getIntent().getStringExtra(LIVE_ID);
        String liveTitle = getIntent().getStringExtra(LIVE_TITLE);

        boolean isLiveVideo = liveTitle != null;

        VideoViewModel videoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);

        if (isLiveVideo) {
            videoViewModel.getLiveVideo(liveId).observe(this, liveVideoResource -> {
                if (liveVideoResource != null) {
                    switch (liveVideoResource.status) {
                        case SUCCESS:
                            LiveVideo liveVideo = liveVideoResource.data;

                            if (liveVideo != null) {

                                mBinding.setVideoName("");
                                mBinding.setVideoPath(liveVideo.hls_url.hls1);


                                // Log.e("VideoActivity", "53行-onCreate(): " + recordVideo.hls_url);
                                mBinding.videoView.setUp(liveVideo.hls_url.hls1, false, liveTitle);

                                //增加title
                                mBinding.videoView.getTitleTextView().setVisibility(View.VISIBLE);
                                //videoPlayer.setShowPauseCover(false);

                                //videoPlayer.setSpeed(2f);

                                //设置返回键
                                mBinding.videoView.getBackButton().setVisibility(View.VISIBLE);

                                //设置旋转
                                mOrientationUtils = new OrientationUtils(this, mBinding.videoView);

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

                            break;
                        case ERROR:

                            break;
                        case LOADING:

                            break;
                        default:
                            break;
                    }
                }
            });
        } else {
            videoViewModel.getRecordVideo(pid).observe(this, recordVideoResource -> {
                if (recordVideoResource != null) {
                    switch (recordVideoResource.status) {
                        case SUCCESS:
                            RecordVideo recordVideo = recordVideoResource.data;

                            if (recordVideo != null) {
                                mBinding.setVideoName(recordVideo.title);
                                mBinding.setVideoPath(recordVideo.hls_url);


                                // Log.e("VideoActivity", "53行-onCreate(): " + recordVideo.hls_url);
                                mBinding.videoView.setUp(recordVideo.video.chapters.get(0).url, false, recordVideo.title);

                                //增加title
                                mBinding.videoView.getTitleTextView().setVisibility(View.VISIBLE);
                                //videoPlayer.setShowPauseCover(false);

                                //videoPlayer.setSpeed(2f);

                                //设置返回键
                                mBinding.videoView.getBackButton().setVisibility(View.VISIBLE);

                                //设置旋转
                                mOrientationUtils = new OrientationUtils(this, mBinding.videoView);

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

                            break;
                        case LOADING:

                            break;
                        case ERROR:

                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //过渡动画
        initTransition();

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


    private void initTransition() {
        //        postponeEnterTransition();
        //        ViewCompat.setTransitionName(mBinding.videoView, IMG_TRANSITION);
        //        addTransitionListener();
        //        startPostponedEnterTransition();
    }

    private boolean addTransitionListener() {
        mTransition = getWindow().getSharedElementEnterTransition();
        if (mTransition != null) {
            Log.e("VideoActivity", "178行-addTransitionListener(): " + " ");
            mTransition.addListener(new OnTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    Log.e("VideoActivity", "183行-onTransitionEnd(): " + " ");
                    mBinding.videoView.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

    public class OnTransitionListener implements Transition.TransitionListener {


        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    }

}
