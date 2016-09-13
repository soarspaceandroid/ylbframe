package com.android.ylblib.interfaces;

/**
 * Created by gaofei on 2016/9/13.
 */
public interface AsyncLisenter<T> {

    public T runIOThread();


    public void runMainThread(T t);

}
