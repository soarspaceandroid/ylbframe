package com.android.ylblib.tools.database;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import io.supercharge.rxsnappy.RxSnappy;
import io.supercharge.rxsnappy.RxSnappyClient;

/**
 * Created by gaofei on 2016/9/13.
 */
public class DataManager  {

    private static RxSnappyClient client ;

    /**
     * open db
     * @param context
     */
    public void  openDB(Context context){
        RxSnappy.init(context);
        client = new RxSnappyClient();
    }


    /**
     * insert object
     * @param object
     * @param <T>
     */
    public <T> void insertObject(T object){
        if(object == null){
            return ;
        }
        client.setObject(object.getClass().getSimpleName() , object).toBlocking().first();
    }

    /**
     * get object
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> tClass){
        return client.getObject(tClass.getSimpleName() , tClass).toBlocking().first();
    }


    /**
     * insert int
     * @param key
     * @param value
     */
    public void insertInteger(String key , int value){
        if(TextUtils.isEmpty(key)){
            return;
        }
        client.setInteger(key , value).toBlocking().first();
    }

    /**
     * get int
     * @param key
     * @return
     */
    public int getInteger(String key){
        if(!client.exists(key).toBlocking().first()){
            return -1;
        }
        return client.getInteger(key).toBlocking().first();
    }

    /**
     * insert string
     * @param key
     * @param value
     */
    public void insertString(String key , String value){
        if(TextUtils.isEmpty(value)){
            return;
        }
        client.setString(key , value).toBlocking().first();
    }

    /**
     * get string
     * @param key
     * @return
     */
    public String getString(String key){
        if(!client.exists(key).toBlocking().first()){
            return "";
        }
        return client.getString(key).toBlocking().first();
    }


    /**
     * insert boolean
     * @param key
     * @param value
     */
    public void insertBoolean(String key , boolean value){
        if(TextUtils.isEmpty(key)){
            return;
        }
        client.setBoolean(key , value).toBlocking().first();
    }


    /**
     * get boolean
     * @param key
     * @return
     */
    public boolean getBoolean(String key){
        if(!client.exists(key).toBlocking().first()){
            return false;
        }
        return client.getBoolean(key).toBlocking().first();
    }


    /**
     * insert list
     * @param key
     * @param list
     */
    public void insertList(String key , List list){
        if(list == null || (list !=null && list.size() == 0) || TextUtils.isEmpty(key)){
            return ;
        }
        client.setList(key , list).toBlocking().first();
    }


    /**
     * get list
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key , Class<T> tClass){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        return client.getList(key , tClass).toBlocking().first();
    }


    /**
     * insert string list
     *
     * @param key
     * @param list
     */
    public void insertStringList(String key , List<String> list){
        if(list == null || (list !=null && list.size() == 0) || TextUtils.isEmpty(key)){
            return ;
        }
        client.setStringList(key , list).toBlocking().first();
    }


    /**
     * get string list
     * @param key
     * @return
     */

    public  List<String> getStringList(String key){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        return client.getStringList(key).toBlocking().first();
    }



    /**
     *  close  db
     */
    public void closeDB(){
        RxSnappy.closeDatabase();
    }

}
