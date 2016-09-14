package com.example.administrator.ylbapplication.hybrid.web;

import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.android.ylblib.base.presenter.BasePresenter;
import com.android.ylblib.base.views.BaseActivity;
import com.android.ylblib.interfaces.JsBridgeWebChromeListenner;
import com.android.ylblib.jsbridge.core.JsBridgeWebChromeClient;
import com.android.ylblib.views.pulltorefresh.library.PullToRefreshBase;
import com.android.ylblib.views.pulltorefresh.library.PullToRefreshWebView;


/**
 * Created by Godxj on 2016/5/17.
 */
public class WebActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2{

    private PullToRefreshWebView pullToRefreshWebView;
    private WebView mWebView;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.setWebChromeClient(new JsBridgeWebChromeClient(new JsBridgeWebChromeListenner() {
                @Override
                public void onTitleChange(String title, WebView webView) {
                }
            }));
        }

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    protected void requestData(BasePresenter basePresenter) {

    }

    @Override
    public void updateView(Object response) {

    }

    @Override
    public void showError(String msg) {

    }


    @Override
    protected String currActivityName() {
        return null;
    }

    private void reload() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.reload();
            mWebView.setWebChromeClient(new JsBridgeWebChromeClient(new JsBridgeWebChromeListenner() {
                @Override
                public void onTitleChange(String title, WebView webView) {
                }
            }));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        CookieSyncManager.getInstance().startSync();
        //该方法会清楚webview所有cookie
//        CookieManager.getInstance().removeSessionCookie();
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.destroy();
    }

}
