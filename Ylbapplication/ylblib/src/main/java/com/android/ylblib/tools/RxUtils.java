package com.android.ylblib.tools;

import com.android.ylblib.interfaces.AsyncLisenter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gaofei on 2016/9/13.
 */
public class RxUtils {

    public static <T> void asyncTask(T t ,final AsyncLisenter<T> asyncLisenter){
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T t = asyncLisenter.runIOThread();
                subscriber.onNext(t);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {
                        asyncLisenter.runMainThread(t);
                    }
                });
    }

}

