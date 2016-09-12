package com.android.ylblib.jsbridge.core;

import android.os.Build;
import android.webkit.WebView;

import com.android.ylblib.jsbridge.async.AsyncTaskExecutor;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * native结果数据返回格式:
 * var resultData = {
 * status: {
 * code: 0,//0成功，1失败
 * msg: '请求超时'//失败时候的提示，成功可为空
 * },
 * data: {}//数据
 * };
 * <p/>
 *
 */
public class JsCallback {
    private static final String CALLBACK_JS_FORMAT = "javascript:RainbowBridge.onComplete(%s,%s);";
    private WeakReference<WebView> mWebViewWeakRef;
    private String mPort;

    private JsCallback(WebView webView, String port) {
        this.mWebViewWeakRef = new WeakReference<>(webView);
        this.mPort = port;
    }

    public static JsCallback newInstance(WebView webView, String port) {
        return new JsCallback(webView, port);
    }

    public void call(boolean isInvokeSuccess, String resultData, String statusMsg) throws JsCallbackException {
        final WebView webView = mWebViewWeakRef.get();
        if (webView == null)
            throw new JsCallbackException("The WebView related to the JsCallback has been recycled!");
//        JSONObject resultObj = new JSONObject();
//        JSONObject status = new JSONObject();
//        try {
//            status.put("code", isInvokeSuccess ? 0 : 1);
//            if (!TextUtils.isEmpty(statusMsg)) {
//                status.put("msg", statusMsg);
//            } else {
//                status.put("msg", "");
//            }
//            resultObj.put("status", status);
//            if (resultData != null) {
//                resultObj.put("data", resultData);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        final String callbackJs = String.format(Locale.getDefault(), CALLBACK_JS_FORMAT, mPort, resultData);
        if (AsyncTaskExecutor.isMainThread()) {
            //webView.loadUrl(callbackJs);
            callJS(webView,callbackJs);
        } else {
            AsyncTaskExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                  //  webView.loadUrl(callbackJs);
                    callJS(webView,callbackJs);
                }
            });
        }
    }
    //java调用JS
    public static void callJS(final WebView aWebView, final String aJS)
    {
        if (aWebView == null) return;

        /*
        Use webview to load a webpage which includes a JS function urlAdded(url);

        Call webview.loadUrl("javascript:urlAdded(\"http://redir.xxxxx.com/click.php?id=12345&originalUrlhttp%3A%2F%2Fm.ctrip.com%2Fhtml5%2F%3Fallianceid%3D1000%26sid%3D454555%26sourceid%3D1111\"");

        On android 4.4 device:
        urlAdded(url) got a parameter
        http://redir.xxxxx.com/click.php?id=12345&originalUrl=http://m.ctrip.com/html5/?allianceid=1000&sid=454555&sourceid=1111

        originalUrl is miss unescaped.

        pre-4.4 device: expected
        urlAdded(url) got a parameter
        http://redir.xxxxx.com/click.php?id=12345&originalUrlhttp%3A%2F%2Fm.ctrip.com%2Fhtml5%2F%3Fallianceid%3D1000%26sid%3D454555%26sourceid%3D1111
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            aWebView.evaluateJavascript(aJS, null);
        }
        else
        {
            aWebView.loadUrl("javascript:" + aJS);
        }
    }


    public static void invokeJsCallback(JsCallback callback, boolean isInvokeSuccess, String resultData, String statusMsg) {
        if (callback == null)
            return;
        try {
            callback.call(isInvokeSuccess, resultData, statusMsg);
        } catch (JsCallbackException e) {
            e.printStackTrace();
        }
    }

    private static class JsCallbackException extends Exception {
        public JsCallbackException(String detailMessage) {
            super(detailMessage);
        }
    }

}
