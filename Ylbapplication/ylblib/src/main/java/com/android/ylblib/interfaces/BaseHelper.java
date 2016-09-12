package com.android.ylblib.interfaces;

/**
 * Created by gaofei on 2016/9/12.
 */
public interface BaseHelper<T> {

    /**
     * 处理与UI无关的data数据
     * @param t
     * @return
     */
    public T dowithDataWithoutUI(T t);

}
