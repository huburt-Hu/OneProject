package com.app.julie.oneproject.business.zhihudetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.mvp.RespObserver;
import com.app.julie.common.mvp.RetryWhenNetException;
import com.app.julie.common.mvp.SchedulersTransformer;
import com.app.julie.oneproject.R;
import com.app.julie.oneproject.api.ApiManager;
import com.app.julie.oneproject.bean.ZhihuDetailEntity;

public class ZhihuDetailActivity extends BaseActivity {

    public static final String STORY_ID = "story_id";
    public static final String TITLE = "title";
    private String id;
    private String title;
    private WebView wvZhihu;
    private ProgressBar progressBar;

    public static void start(Context context, String title, String id) {
        Intent intent = new Intent(context, ZhihuDetailActivity.class);
        intent.putExtra(STORY_ID, id);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString(STORY_ID);
            title = extras.getString(TITLE);
        }

        initView();
        getData();
    }

    private void getData() {
        ApiManager.getZhihuService()
                .getZhihuStory(id)
                .retryWhen(new RetryWhenNetException())
                .compose(SchedulersTransformer.<ZhihuDetailEntity>io_ui())
                .compose(this.<ZhihuDetailEntity>bindToLifecycle())
                .subscribe(new RespObserver<ZhihuDetailEntity>() {
                    @Override
                    public void success(ZhihuDetailEntity entity) {
                        wvZhihu.loadUrl(entity.getShareUrl());
                    }
                });
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.pb);
        wvZhihu = (WebView) findViewById(R.id.wv);
        WebSettings settings = wvZhihu.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvZhihu.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        wvZhihu.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvZhihu.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //webview内存泄露
        if (wvZhihu != null) {
            ((ViewGroup) wvZhihu.getParent()).removeView(wvZhihu);
            wvZhihu.destroy();
            wvZhihu = null;
        }
    }
}
