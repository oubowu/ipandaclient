package com.oubowu.ipanda.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastDetail;
import com.oubowu.ipanda.databinding.ActivityWebViewBinding;
import com.oubowu.ipanda.util.NetUtil;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.viewmodel.PandaBroadcastViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class WebViewActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String ID = "url";

    private ActivityWebViewBinding mWebViewBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, WebViewActivity.class).putExtra(ID, id);
        activity.startActivity(intent);
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

        setSupportActionBar(mWebViewBinding.toolbar);
        mWebViewBinding.toolbar.setContentInsetStartWithNavigation(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initSettings();

        initWebViewClient();

        initWebChromeClient();

        String id = getIntent().getStringExtra(ID);

        PandaBroadcastViewModel viewModel = ViewModelProviders.of(this, mFactory).get(PandaBroadcastViewModel.class);

        viewModel.getPandaBroadcastDetail(id).observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case SUCCESS:
                        PandaBroadcastDetail data = resource.data;
                        if (data != null) {
                            mWebViewBinding.webView.loadDataWithBaseURL(null, getHtmlData(
                                    data.content + "<div class=\"exp1\">" + data.source + "   " + data.pubtime + "</div>"),
                                    "text/html", "utf-8", null);
                            mWebViewBinding.toolbar.setTitle(data.title);
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

        // mWebViewBinding.webView.loadUrl(id);

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

    private void initWebChromeClient() {
        mWebViewBinding.webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("WebViewActivity", "66行-onProgressChanged(): " + newProgress);
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

            // 当一个url即将被webview加载时，给Application一个机会来接管处理这个url，方法返回true代表Application自己处理url；返回false代表Webview处理url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();

                String scheme = uri.getScheme();
                if (TextUtils.isEmpty(scheme)) {
                    return true;
                }
                if (scheme.equals("nativeapi")) {
                    //如定义nativeapi://showImg是用来查看大图，这里添加查看大图逻辑
                    return true;
                } else if (scheme.equals("http") || scheme.equals("https")) {
                    //处理http协议
                    if (uri.getHost().equals("www.example.com")) {
                        // 内部网址，不拦截，用自己的webview加载
                        return false;
                    } else {
                        //跳转外部浏览器
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        List<ResolveInfo> list = getPackageManager()
                                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (list.size() > 0) {
                            startActivity(intent);
                        }
                        return true;
                    }
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

        //默认是false 设置true允许和js交互
        // settings.setJavaScriptEnabled(true);


        // // 设置可以支持缩放
        // settings.setSupportZoom(true);
        // // 设置出现缩放工具
        // settings.setBuiltInZoomControls(true);
        // //扩大比例的缩放
        // settings.setUseWideViewPort(true);
        // //自适应屏幕
        // settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // settings.setLoadWithOverviewMode(true);

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
                    finish();
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
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
