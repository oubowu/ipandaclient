package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oubowu.ipanda.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;

/**
 * 带封面
 * Created by guoshuyu on 2017/9/3.
 */

public class CoverVideoPlayer extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mCoverOriginUrl;

    int mDefaultRes;

    public CoverVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public CoverVideoPlayer(Context context) {
        super(context);
    }

    public CoverVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);

        if (mThumbImageViewLayout != null && (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_cover;
    }

    public void loadCoverImage(String url, int res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;
        Glide.with(getContext().getApplicationContext()).setDefaultRequestOptions(new RequestOptions().centerCrop().error(res).placeholder(res)).load(url)
                .into(mCoverImage);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        CoverVideoPlayer sampleCoverVideo = (CoverVideoPlayer) gsyBaseVideoPlayer;
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        return gsyBaseVideoPlayer;
    }

    @Override
    public void clickStartIcon() {
        super.clickStartIcon();
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        Log.e("CoverVideoPlayer","80行-onVideoPause(): "+" ");
    }

    @Override
    public void onVideoResume() {
        super.onVideoResume();
        Log.e("CoverVideoPlayer","87行-onVideoResume(): "+" ");
    }
}
