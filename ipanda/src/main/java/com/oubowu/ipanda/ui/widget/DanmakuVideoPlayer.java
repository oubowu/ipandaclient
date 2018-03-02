package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.ui.adapter.BiliDanmukuParser;
import com.oubowu.ipanda.ui.adapter.DanamakuAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.InputStream;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;

/**
 * Created by guoshuyu on 2017/2/16.
 * <p>
 * 配置弹幕使用的播放器，目前使用的是本地模拟数据。
 * <p>
 * 模拟数据的弹幕时常比较短，后面的时长点是没有数据的。
 * <p>
 * 注意：这只是一个例子，演示如何集合弹幕，需要完善如弹出输入弹幕等的，可以自行完善。
 * 注意：b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤。
 */

public class DanmakuVideoPlayer extends StandardGSYVideoPlayer {

    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;

    private TextView mSwitchDanmuku;

    private long mDanmakuStartSeekPosition = -1;

    private boolean mDanmaKuShow = true;
    private String mMyUrl;
    private boolean mMyCacheWithPlay;
    private String mMyTitle;

    public DanmakuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmakuVideoPlayer(Context context) {
        super(context);
    }

    public DanmakuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.danmaku_layout;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        //        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);

        //初始化弹幕显示
        //        initDanmaku();

        mSwitchDanmuku = findViewById(R.id.switch_danmuku);
        mSwitchDanmuku.setOnClickListener(this);

        mProgressBar.setVisibility(INVISIBLE);
        mTotalTimeTextView.setVisibility(INVISIBLE);

    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        // onPrepareDanmaku(this);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        onDanmukuPause();
    }

    @Override
    public void onVideoResume() {
        //        super.onVideoResume();
        if (GSYVideoManager.instance().getMediaPlayer() != null) {
            if (mMyUrl != null) {
                setStateAndUi(CURRENT_STATE_PLAYING);
                startPlayLogic();
            }
        }
        onDanmukuResume();
    }

    @Override
    public boolean setUp(String url, boolean cacheWithPlay, String title) {
        mMyUrl = url;
        mMyCacheWithPlay = cacheWithPlay;
        mMyTitle = title;
        return super.setUp(url, cacheWithPlay, title);
    }

    @Override
    public void onCompletion() {
        Log.e("DanmakuVideoPlayer", "137行-onCompletion(): " + " ");
        // releaseDanmaku(this);
    }

    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        Log.e("DanmakuVideoPlayer", "145行-onSeekComplete(): " + " ");
        int time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
            Log.e("DanmakuVideoPlayer", "150行-onSeekComplete(): " + " ");
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
            Log.e("DanmakuVideoPlayer", "154行-onSeekComplete(): " + " ");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.switch_danmuku:
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
                break;
        }
    }

    /**
     * 处理播放器在全屏切换时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            onPrepareDanmaku(gsyVideoPlayer);
        }
        return gsyBaseVideoPlayer;
    }

    /**
     * 处理播放器在退出全屏时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            DanmakuVideoPlayer gsyDanmaVideoPlayer = (DanmakuVideoPlayer) gsyVideoPlayer;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null && gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
        }
    }


    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        // 右往左动
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)//
                .setDuplicateMergingEnabled(false)//是否启用合并重复弹幕
                .setScrollSpeedFactor(2.5f)//设置弹幕滚动速度系数,只对滚动弹幕有效
                .setScaleTextSize(1.0f)//
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer，设置缓存绘制填充器
                // 默认使用{@link SimpleTextCacheStuffer}只支持纯文字显示,
                // 如果需要图文混排请设置{@link SpannedCacheStuffer}
                // 如果需要定制其他样式请扩展{@link SimpleTextCacheStuffer}|{@link SpannedCacheStuffer}
                .setMaximumLines(maxLinesPair)//设置最大显示行数
                .preventOverlapping(overlappingEnablePair);//设置防弹幕重叠，null为允许重叠

        if (mDanmakuView != null) {
            //todo 替换成你的数据流
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));//创建解析器对象，从raw资源目录下解析comments.xml文本
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        Log.e("DanmakuVideoPlayer", "244行-prepared(): " + " ");
                        getDanmakuView().start();
                        if (getDanmakuStartSeekPosition() != -1) {
                            Log.e("DanmakuVideoPlayer", "247行-prepared(): " + " ");
                            resolveDanmakuSeek(DanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }

        onPrepareDanmaku(this);

    }

    /**
     * 弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(() -> {
            if (mDanmaKuShow) {
                if (!getDanmakuView().isShown()) {
                    getDanmakuView().show();
                }
                //                    mToogleDanmaku.setText("弹幕关");
                mSwitchDanmuku.setText("关弹幕");
            } else {
                if (getDanmakuView().isShown()) {
                    getDanmakuView().hide();
                }
                //                    mToogleDanmaku.setText("弹幕开");
                mSwitchDanmuku.setText("开弹幕");
            }
        });
    }

    /**
     * 开始播放弹幕
     */
    private void onPrepareDanmaku(DanmakuVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(), gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     * 弹幕偏移
     */
    private void resolveDanmakuSeek(DanmakuVideoPlayer gsyVideoPlayer, long time) {
        if (GSYVideoManager.instance().getMediaPlayer() != null && mHadPlay && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     * 创建解析器对象，解析输入流
     *
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        // DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI) //xml解析
        // DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_ACFUN) //json文件格式解析
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    /**
     * 释放弹幕控件
     */
    private void releaseDanmaku(DanmakuVideoPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    int mTextColor = com.oubowu.ipanda.util.CommonUtil.getThemeAttrColor(getContext(), R.attr.colorAccent);

    public void addDanmaku(boolean islive, CharSequence text) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = text;
        danmaku.padding = 5;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = 20f * (mParser.getDisplayer().getDensity() - 0.6f);

        danmaku.textColor = mTextColor;
        danmaku.textShadowColor = Color.TRANSPARENT;
        danmaku.borderColor = Color.TRANSPARENT;
        mDanmakuView.addDanmaku(danmaku);
    }


    private void onDanmukuPause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    private void onDanmukuResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    public void setDanmakuView(IDanmakuView danmakuView) {
        mDanmakuView = danmakuView;
        initDanmaku();
    }
}
