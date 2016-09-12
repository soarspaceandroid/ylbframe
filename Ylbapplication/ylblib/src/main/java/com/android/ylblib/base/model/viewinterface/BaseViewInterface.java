package com.android.ylblib.base.model.viewinterface;

/**
 * Created by gaofei on 2016/2/24.
 */
public interface BaseViewInterface<T>{

    void updateView(T response);

    void showError(String msg);
}
