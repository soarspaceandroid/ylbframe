package com.android.ylblib.tools.database;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.android.ylblib.tools.Logs;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

/**
 * Created by gaofei on 2016/5/12.
 */
public class DataCacheManager {

    private final static String DBNAME = "appdatabase";

    private final static String PROCESS_NAME = "com.yonglibao.android"; // 主进程

    private String dir = "";

    private DB mSnappyDB;

    private Gson gson;

    private Context mContext;

    private ActivityManager mActivityManager;

    public DataCacheManager(Context context) {
        this.mContext = context;
        mActivityManager = (ActivityManager)mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * open db
     *
     * @return
     */
    public synchronized DataCacheManager openDB() {
        dir = "/data/data/" + mContext.getPackageName();
        try {
            mSnappyDB = new SnappyDB.Builder(mContext)
                    .directory(dir)
                    .name(DBNAME)
                    .build();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        gson = new Gson();
        return this;
    }

    /**
     * @param key
     * @param value
     * @param forever 清除数据是否清理该字段
     */
    public void insertString(String key, String value, boolean forever) {
        String realKey = key;
        if (forever) {
            realKey = "data_" + key;
        }
        insertString(realKey, value);
    }

    /**
     *
     * @param key
     * @param forever 数据是否为永久存储的
     * @return
     */
    public String getString(String key, boolean forever){
        String realKey = key;
        if(forever){
            realKey = "data_" + key;
        }
        return  getString(realKey);
    }

    /**
     * insert db
     *
     * @param key
     * @param value
     */
    public void insertString(String key, String value) {
        checkDataBase();
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        String vTem = getString(key);
                        if (TextUtils.isEmpty(value)) {
                            return;
                        } else if (value.equals(vTem)) {
                            return;
                        } else {
                            deleteValue(key);
                        }
                    }

                    mSnappyDB.put(key, value);
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                    Logs.d("db", "no this key - " + key);
                }
            }
        }
    }

    /**
     * delete by key
     *
     * @param key
     */

    public void deleteValue(String key) {
        if(!checkDataBase()){
            return;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        mSnappyDB.del(key);
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
    }


    /**
     * @param key
     * @return
     */
    public String getString(String key) {
        if(!checkDataBase()){
            return "";
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        return mSnappyDB.get(key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * get obj
     *
     * @param tClass
     * @param <T>
     * @return
     */

    public <T> T getObject(Class<T> tClass) {
        if(!checkDataBase()){
            return null;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(tClass.getSimpleName())) {
                        String value = mSnappyDB.get(tClass.getSimpleName());
                        if (null != gson) {
                            return gson.fromJson(value, tClass);
                        }
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                    return null;
                }
            }
        }
        return null;
    }


    /**
     * insert obj
     *
     * @param key
     * @param object
     */
    public void insertObject(String key, Object object) {
        if (object == null) {
            return;
        }
        if(!checkDataBase()){
            return;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    String value = "";
                    if (mSnappyDB.exists(key)) {
                        String vTem = getString(key);
                        if (gson != null) {
                            value = addCacheFlag(gson.toJson(object));
                            if (value.equals(vTem)) {
                                return;
                            } else {
                                deleteValue(key);
                            }
                        }

                    } else {

                        if (gson != null) {
                            value = addCacheFlag(gson.toJson(object)); //  只针对存储对象添加cache标示
                        }
                    }
                    mSnappyDB.put(key, value);
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
    }

    /**
     * 添加是否是cache字段
     *
     * @param value
     * @return
     */
    private String addCacheFlag(String value) {
        JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
        jsonObject.addProperty("isCache", true);
        return jsonObject.toString();
    }


    /**
     * insert boolean
     *
     * @param key
     * @param value
     */
    public void insertBoolean(String key, boolean value) {
        if(!checkDataBase()){
            return;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        boolean val = mSnappyDB.getBoolean(key);
                        if (val == value) {
                            return;
                        }
                    }
                    mSnappyDB.putBoolean(key, value);
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
    }

    /**
     * get boolean
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        if(!checkDataBase()){
            return false;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        return mSnappyDB.getBoolean(key);
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
        return false;
    }


    /**
     * insert int
     *
     * @param key
     * @param value
     */
    public void insertInteger(String key, int value) {
        if(!checkDataBase()){
            return;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        int val = mSnappyDB.getInt(key);
                        if (val == value) {
                            return;
                        }
                    }
                    mSnappyDB.putInt(key, value);
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
    }

    /**
     * get integer
     *
     * @param key
     * @return
     */
    public Integer getInteger(String key) {
        if(!checkDataBase()){
            return -1;
        }
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.exists(key)) {
                        return mSnappyDB.getInt(key);
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();//TODO handle Exception elegantly (throw Observable.error)
                }
            }
        }
        return -1;
    }


    public boolean checkOpen() {
        if (mSnappyDB != null) {
            try {
                return mSnappyDB.isOpen();
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 由于activity1 跳转到activity2的时候 , activity1的ondestroy在activity2的oncreate后面执行, 导致在oncreate中打开的db 又被distroy关闭, 所以使用时qingcheck该方法
     *
     * @return
     */
    public synchronized boolean checkDataBase() {
        if(isSafeControlDB()){
            if (mSnappyDB == null) {
                openDB();
            } else {
                if (!checkOpen()) {
                    openDB();
                }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * close db
     */
    public void closeDB() {
        if (null != mSnappyDB) {
            synchronized (mSnappyDB) {
                try {
                    if (mSnappyDB.isOpen()) {
                        mSnappyDB.close();
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 清除缓存用
     */
    public void destroyDB() {
        synchronized (mSnappyDB) {
            if (null != mSnappyDB) {
                try {
                    mSnappyDB.destroy();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 区分主进程和push进程的调用
     * @return
     */
    private boolean isSafeControlDB(){
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo process: mActivityManager.getRunningAppProcesses()) {
            if(process.pid == pid){
                if(PROCESS_NAME.equals(process.processName)){
                    return true;  // 主进程
                }else{
                    return false;  // push 进程
                }
            }
        }
        return false;
    }

}
