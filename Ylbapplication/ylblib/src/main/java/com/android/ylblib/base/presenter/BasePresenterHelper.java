package com.android.ylblib.base.presenter;

import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.net.RequestListener;

/**
 * Created by gaofei on 2016/9/12.
 */
public class BasePresenterHelper{

    private static BasePresenter basePresenter;

    public static synchronized BasePresenter getInstance(BaseViewInterface baseViewInterface , RequestListener listener , Class enity) {
            if(basePresenter == null){
            basePresenter = new BasePresenter().setBaseViewInterface(baseViewInterface).setRequestListener(listener).setRquestEnity(enity);
        }
        return basePresenter;
    }
}
