package com.android.ylblib.interfaces;

import android.webkit.WebView;

/**
 * Created by gaofei on 2016/8/8.
 */
public interface JsBridgeWebChromeListenner {


    /**
     * get title
     * @param title
     * @param webView
     */
    public void onTitleChange(String title, WebView webView);

}
