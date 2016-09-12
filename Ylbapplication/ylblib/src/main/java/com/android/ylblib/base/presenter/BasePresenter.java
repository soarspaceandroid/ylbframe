package com.android.ylblib.base.presenter;


import com.android.ylblib.base.model.bean.input.BaseBeanInput;
import com.android.ylblib.base.model.bean.output.BaseBeanOutput;
import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.net.RequestListener;
import com.android.ylblib.net.RetrofitFactory;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gaofei on 16/2/23.
 */
public class BasePresenter<T extends BaseBeanOutput, S> {
    private final static String TAG = "BasePresenter";

    private BaseViewInterface baseViewInterface;


    private S enity = null;

    private BaseBeanInput input;

    private RequestListener requestListener;

    public BasePresenter setRquestEnity(Class<S> sClass) {
        if (enity == null) {
            enity = RetrofitFactory.getInstance(sClass);
        }
        return this;
    }

    public BasePresenter setBaseViewInterface(BaseViewInterface baseViewInterface) {
        this.baseViewInterface = baseViewInterface;
        return this;
    }


    public BasePresenter setInput(BaseBeanInput input) {
        this.input = input;
        return this;
    }

    public BasePresenter setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public void load() {
        if (input.isShowDialog()&&this.requestListener!=null) {
            this.requestListener.showProgressDialog();
        }
        requestListener.errorHide();
        input.getData(enity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        if (input.isShowDialog()&&requestListener!=null) {
                            requestListener.hideProgressDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (input.isShowDialog()&&requestListener!=null) {
                            requestListener.hideProgressDialog();
                        }
                        baseViewInterface.showError(e.getLocalizedMessage());
                        requestListener.errorDisplay(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(T t) {
                        baseViewInterface.updateView(t);
                        if (input.isShowDialog()&&requestListener!=null) {
                            requestListener.hideProgressDialog();
                        }
                    }
                });


    }

}
