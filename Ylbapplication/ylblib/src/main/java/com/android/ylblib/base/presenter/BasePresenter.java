package com.android.ylblib.base.presenter;


import android.util.Log;

import com.android.ylblib.base.BaseApplication;
import com.android.ylblib.base.RxCenter;
import com.android.ylblib.base.model.bean.input.BaseBeanInput;
import com.android.ylblib.base.model.bean.output.BaseBeanOutput;
import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.net.RequestListener;
import com.android.ylblib.net.RetrofitFactory;
import com.android.ylblib.tools.Logs;
import com.google.gson.Gson;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
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

    private Class outputClass;

    private RequestListener requestListener;


    /**
     * set reqeust class
     * @param sClass
     * @return
     */
    public BasePresenter setRquestEnity(Class<S> sClass) {
        if (enity == null) {
            enity = RetrofitFactory.getInstance(sClass);
        }
        return this;
    }


    /**
     * set view interface
     * @param baseViewInterface
     * @return
     */
    public BasePresenter setBaseViewInterface(BaseViewInterface baseViewInterface) {
        this.baseViewInterface = baseViewInterface;
        return this;
    }


    /**
     * set request params
     * @param input
     * @return
     */
    public BasePresenter setInput(BaseBeanInput input) {
        this.input = input;
        return this;
    }

    /**
     * set request response
     * @param outputClass
     * @return
     */
    public BasePresenter setOutputClass(Class outputClass) {
        this.outputClass = outputClass;
        return this;
    }

    /**
     * set request lisenter
     * @param requestListener
     * @return
     */
    public BasePresenter setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    /**
     *  request
     */
    public void load(boolean fromCache) {
        showDialog();
        requestListener.errorHide();
        getDataFromLocal(fromCache);

        Observable<T> observable = input.getData(enity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        Subscription subscription =observable.subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        BasePresenterHelper.removeRequest(input.getClass().getSimpleName());
                        hideDialog();
                        Log.e("soar" , "oncomple");
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        BasePresenterHelper.removeRequest(input.getClass().getSimpleName());
                        baseViewInterface.showError(e.getLocalizedMessage());
                        requestListener.errorDisplay(e.getLocalizedMessage());
                        hideDialog();
                    }
                    @Override
                    public void onNext(T t) {
                        Log.e("soar" , "on next");
                        baseViewInterface.updateView(t);
                        //此处存储的数据全部置为true 然后存储
                        t.setCache(true);
                        BaseApplication.getDataManager().insertObject(t);
                    }
                });

//       BasePresenterHelper.addRequest(input.getClass().getSimpleName() , subscription);

        RxCenter.INSTANCE.getCompositeSubscription(input.getClass().getSimpleName().hashCode()).add(subscription);

    }


    /**
     * cancel request
     * @param key
     */
    public void cancel(String... key){
//        BasePresenterHelper.cancelRequest(key);
        for(String k : key) {
            RxCenter.INSTANCE.removeCompositeSubscription(k.hashCode());
        }
    }


    /**
     * get data from local
     */
    private void getDataFromLocal(boolean fromCache){
        if(fromCache) {
            Object object = BaseApplication.getDataManager().getObject(outputClass);
            if(object == null){
                return ;
            }
            Logs.d("cache" , new Gson().toJson(object));
            baseViewInterface.updateView(object);
        }
    }


    /**
     * show dialog
     */
    private void showDialog(){
        if (input.isShowDialog()&&this.requestListener!=null) {
            this.requestListener.showProgressDialog();
        }
    }


    /**
     *   hide dialog
     */
    private void hideDialog(){
        if (input.isShowDialog()&&requestListener!=null) {
            requestListener.hideProgressDialog();
        }
    }

}
