package com.android.ylblib.base;

import android.app.Application;
import android.content.Context;

import com.android.ylblib.net.NetConfig;
import com.android.ylblib.tools.database.DataCacheManager;


/**
 * Created by gaofei on 2016/4/26.
 */
public abstract class BaseApplication extends Application{
    private static DataCacheManager dataCacheManager;
    private static Context mContext;
    private static BaseApplication instanse;


    @Override
    public void onCreate() {
        super.onCreate();
        instanse = this;
        NetConfig.CACHAE_DIR=getCacheDir();
        mContext = getApplicationContext();
    }

    /**
     * get data cache manager
     * @return
     */
    public static DataCacheManager getDataCacheManager(){
        if(dataCacheManager == null){
            dataCacheManager = new DataCacheManager(mContext);
        }
        return dataCacheManager;
    }

    /**
     * get context
     * @return
     */
    public static Context getContext(){
        return mContext;
    }


    /**
     * get application instance
     * @return
     */
    public static BaseApplication getInstanse(){
        return instanse;
    }


    /**
     * get request enity class
     * @return
     */
    public final  Class getRequestEnityClass(){
        return getEnityClass();
    }


    /**
     * 重写获取enity class
     * @return
     */
    public abstract Class getEnityClass();

    /**
     * 重写获取base url
     * @return
     */
    public abstract String getBaseUrl();
}
