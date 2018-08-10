package com.oubowu.ipanda.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastDetail;
import com.oubowu.ipanda.databinding.ActivityWebViewBinding;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.NetUtil;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.util.ToastUtil;
import com.oubowu.ipanda.viewmodel.PandaBroadcastViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class WebViewActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String ID = "id";

    private static final String TITLE = "title";

    private static final String URL = "url";


    private ActivityWebViewBinding mWebViewBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private PandaBroadcastDetail mBroadcastDetail;

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, WebViewActivity.class).putExtra(ID, id);

        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
        //        activity.startActivity(intent);
    }

    public static void start(Activity activity, String title, String url) {
//        Intent intent = new Intent(activity, WebViewActivity.class).putExtra(TITLE, title).putExtra(URL, url);
//        ActivityCompat.startActivity(activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());

        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);//splitflowurl为分流地址
            intent.setData(content_url);
            if (!hasPreferredApplication(activity,intent)){
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            }
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showErrorMsg("跳转浏览器失败");
        }

    }

    //判断系统是否设置了默认浏览器
    public static boolean hasPreferredApplication(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        //如果info.activityInfo.packageName为android,则没有设置,否则,有默认的程序.
        return !"android".equals(info.activityInfo.packageName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBarColorLollipop);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark1);
            StatusBarUtil.StatusBarLightMode(this, 3);
        }

        mWebViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        //进入退出效果 注意这里 创建的效果对象是 Explode()
        getWindow().setEnterTransition(new Slide().setDuration(500));
        getWindow().setExitTransition(new Slide().setDuration(500));

        setSupportActionBar(mWebViewBinding.toolbar);
        mWebViewBinding.toolbar.setContentInsetStartWithNavigation(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initSettings();

        initWebViewClient();

        initWebChromeClient();

        String id = getIntent().getStringExtra(ID);
        String url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);

        if (CommonUtil.isEmpty(id)) {
            mWebViewBinding.webView.loadUrl(url);
            mWebViewBinding.toolbar.setTitle(title);
        } else {
            PandaBroadcastViewModel viewModel = ViewModelProviders.of(this, mFactory).get(PandaBroadcastViewModel.class);

            viewModel.getPandaBroadcastDetail(id).observe(this, resource -> {
                if (resource != null) {
                    switch (resource.status) {
                        case SUCCESS:
                            mBroadcastDetail = resource.data;
                            if (mBroadcastDetail != null) {
                                animateRevealShow(mWebViewBinding.webView);
                                // animateRevealShow(mWebViewBinding.toolbar);
                                mWebViewBinding.webView.loadDataWithBaseURL(null, getHtmlData(
                                        mBroadcastDetail.content + "<div class=\"exp1\">" + mBroadcastDetail.source + "   " + mBroadcastDetail.pubtime + "</div>"),
                                        "text/html", "utf-8", null);
                                mWebViewBinding.toolbar.setTitle(mBroadcastDetail.title);
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
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, finalRadius / 3, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(500);
        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style type=\"text/css\">img{max-width: 100%; width:auto; height: auto;}.exp1{font-size:12px;color:#999999;text-align:right;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    protected void onPause() {
        mWebViewBinding.webView.onPause();
        mWebViewBinding.webView.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mWebViewBinding.webView.onResume();
        mWebViewBinding.webView.resumeTimers();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initWebChromeClient() {
        mWebViewBinding.webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // Log.e("WebViewActivity", "66行-onProgressChanged(): " + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // mWebViewBinding.toolbar.setTitle(title);
            }
        });
    }

    private void initWebViewClient() {
        mWebViewBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.e("WebViewActivity","247行-shouldOverrideUrlLoading(): "+url);

                return super.shouldOverrideUrlLoading(view, url);
            }

            // 当一个url即将被webview加载时，给Application一个机会来接管处理这个url，方法返回true代表Application自己处理url；返回false代表Webview处理url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();

                Log.e("WebViewActivity","251行-shouldOverrideUrlLoading(): "+uri);

                String scheme = uri.getScheme();
                if (TextUtils.isEmpty(scheme)) {
                    return true;
                }
                if (scheme.equals("nativeapi")) {
                    //如定义nativeapi://showImg是用来查看大图，这里添加查看大图逻辑
                    return true;
                } else if (scheme.equals("http") || scheme.equals("https")) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
//                    //处理http协议
//                    if (uri.getHost().equals("www.example.com")) {
//                        // 内部网址，不拦截，用自己的webview加载
//                        return false;
//                    } else {
//                        //跳转外部浏览器
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                        if (list.size() > 0) {
//                            startActivity(intent);
//                        }
//                        return true;
//                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private void initSettings() {
        WebSettings settings = mWebViewBinding.webView.getSettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(settings.getMixedContentMode());
        }

        settings.setLoadWithOverviewMode(true);


        //默认是false 设置true允许和js交互
        // settings.setJavaScriptEnabled(true);


        // // 设置可以支持缩放
        // settings.setSupportZoom(true);
        // // 设置出现缩放工具
        // settings.setBuiltInZoomControls(true);
        // //扩大比例的缩放
        // settings.setUseWideViewPort(true);
        //  //自适应屏幕
        //  settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //  settings.setLoadWithOverviewMode(true);

        //  WebSettings.LOAD_DEFAULT 如果本地缓存可用且没有过期则使用本地缓存，否加载网络数据 默认值
        //  WebSettings.LOAD_CACHE_ELSE_NETWORK 优先加载本地缓存数据，无论缓存是否过期
        //  WebSettings.LOAD_NO_CACHE  只加载网络数据，不加载本地缓存
        //  WebSettings.LOAD_CACHE_ONLY 只加载缓存数据，不加载网络数据
        //Tips:有网络可以使用LOAD_DEFAULT 没有网时用LOAD_CACHE_ELSE_NETWORK
        if (NetUtil.isConnected(this)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        //开启 DOM storage API 功能 较大存储空间，使用简单
        settings.setDomStorageEnabled(true);

        //设置数据库缓存路径 存储管理复杂数据 方便对数据进行增加、删除、修改、查询 不推荐使用
        // settings.setDatabaseEnabled(true);
        // final String dbPath = getApplicationContext().getDir("db", Context.MODE_PRIVATE).getPath();
        // settings.setDatabasePath(dbPath);

        //开启 Application Caches 功能 方便构建离线APP 不推荐使用
        // settings.setAppCacheEnabled(true);
        // final String cachePath = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        // settings.setAppCachePath(cachePath);
        // settings.setAppCacheMaxSize(5 * 1024 * 1024);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mWebViewBinding.webView.canGoBack()) {
                    mWebViewBinding.webView.goBack();
                } else {
                    mWebViewBinding.webView.animate().translationY(mWebViewBinding.webView.getHeight()).setDuration(800).start();
                    onBackPressed();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebViewBinding.webView.canGoBack()) {
            mWebViewBinding.webView.goBack();
            return true;
        }
        // mWebViewBinding.webView.animate().alpha(0).setDuration(250).start();
        mWebViewBinding.webView.animate().translationY(mWebViewBinding.webView.getHeight()).setDuration(800).start();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
