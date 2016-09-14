package com.android.ylblib.base.presenter;

import android.util.Log;

import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.net.RequestListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import rx.Subscription;

/**
 * Created by gaofei on 2016/9/12.
 */
public class BasePresenterHelper{

    private static HashMap<String , Subscription> allRequest = new HashMap<>();
    private static BasePresenter basePresenter;


    /**
     * 此处视为html 显示使用 jsbridge
     *
     * @param baseViewInterface
     * @param listener
     * @param enity
     * @return
     */
    public static synchronized BasePresenter getInstance(BaseViewInterface baseViewInterface , RequestListener listener , Class enity) {
            if(basePresenter == null){
            basePresenter = new BasePresenter().setBaseViewInterface(baseViewInterface).setRequestListener(listener).setRquestEnity(enity);
        }
        return basePresenter;
    }

    /**
     * Add request 使用input clas
     * @param key
     * @param subscription
     */
    public static  void addRequest(String key ,Subscription subscription){
        allRequest.put(key , subscription);
    }


    /**
     * cancel request 使用input clas
     * @param key
     */
    public static void removeRequest(String key){
        if(allRequest.containsKey(key)){
            allRequest.remove(key);
        }
    }

    /**
     * cancel request 使用input clas
     * @param key
     */
    public static void cancelRequest(String... key){
        for (int x = 0 ; x < key.length ; x++) {
            if (allRequest.containsKey(key)) {
                allRequest.get(key).unsubscribe();
                allRequest.remove(key);
            }
        }
    }

    /**
     *
     * clear request recorde 使用input clas
     */
    public static void clearAllRequest(){
        if(allRequest.size()!=0){
            Set keys = allRequest.keySet();
            Iterator iterator = keys.iterator();
           while (iterator.hasNext()){
               allRequest.get(iterator.next()).unsubscribe();
           }
        }
        allRequest.clear();
    }
}
