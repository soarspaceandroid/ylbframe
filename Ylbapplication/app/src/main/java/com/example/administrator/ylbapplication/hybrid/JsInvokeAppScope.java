package com.example.administrator.ylbapplication.hybrid;


import android.content.Context;
import android.webkit.WebView;

import com.android.ylblib.base.BaseApplication;
import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.base.presenter.BasePresenterHelper;
import com.android.ylblib.jsbridge.core.JsCallback;
import com.android.ylblib.net.RequestListener;
import com.example.administrator.ylbapplication.model.input.PicInfoInput;
import com.example.administrator.ylbapplication.model.output.PicInfoOutput;

import org.json.JSONException;
import org.json.JSONObject;

public class JsInvokeAppScope implements BaseViewInterface , RequestListener {

    private Context context;


    @Override
    public void updateView(Object response) {
        if(response instanceof PicInfoOutput){

        }
    }

    @Override
    public void showError(String msg) {

    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void errorDisplay(String errorMsg) {

    }


    @Override
    public void errorHide() {

    }

    /**
     * 积分商城首页数据加载
     *
     * @param webView
     * @param data
     * @param callback
     */
    public void Integral_mall(WebView webView, JSONObject data, JsCallback callback) {
        //Toast.makeText(webView.getContext(), data.toString(), Toast.LENGTH_SHORT).show();
        context = webView.getContext();
        BasePresenterHelper
                .getInstance(this , this , BaseApplication.getInstanse().getRequestEnityClass())
                .setInput(new PicInfoInput(1).setShowDialog(false))
                .setOutputClass(PicInfoOutput.class)
                .load(false);
        String page = "";
        try {
            page = data.getString("p");
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }



    //    public void showLoading(Context context) {
//        if (context != null && context instanceof BaseAbstractActivity) {
//            YLBBaseActivity baseActivity = (YLBBaseActivity) context;
//            baseActivity.showLoading();
//        }
//    }
//
//    public void cancelLoading(Context context) {
//        if (context != null && context instanceof BaseAbstractActivity) {
//            YLBBaseActivity baseActivity = (YLBBaseActivity) context;
//            baseActivity.cancelLoading();
//        }
//    }
}
