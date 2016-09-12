package com.android.ylblib.net;

/**
 * Created by gaofei on 2016/2/23.
 */
public interface RequestListener {
    void showProgressDialog();

    void hideProgressDialog();

    void errorDisplay(String errorMsg);

    void errorHide();

}
