package com.android.ylblib.jsbridge.core;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.ylblib.interfaces.JsBridgeWebChromeListenner;


/**
 * Created by zhengxiaoyong on 16/4/19.
 */
public class JsBridgeWebChromeClient extends WebChromeClient {

    private JsBridgeWebChromeListenner jsBridgeWebChromeListenner;

    public JsBridgeWebChromeClient(JsBridgeWebChromeListenner jsBridgeWebChromeListenner) {
        this.jsBridgeWebChromeListenner = jsBridgeWebChromeListenner;
    }

    @Override
    public final boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm();
        JsCallJava.newInstance().call(view,message);
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(jsBridgeWebChromeListenner != null){
            jsBridgeWebChromeListenner.onTitleChange(title , view);
        }
        super.onReceivedTitle(view, title);
    }
}
