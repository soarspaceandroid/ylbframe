package com.android.ylblib.base.model.bean.input;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import rx.Observable;

/**
 * Created by gaofei on 2016/2/24.
 */
public abstract class BaseBeanInput<T>{


    /**
     * 是否显示dialog
     */
    private boolean showDialog = true;


    /**
     * 重写获取请求对象
     * @param enity
     * @return
     */
    public abstract Observable getData(T enity);

    /**
     *  重写组合请求参数
     * @return
     */
    public abstract Map<String , Object> createParams();

    /**
     * create params 请求参数
     * @return
     */
    public final FormBody createFormBody(){
        Map<String , Object> params = createParams();
        Set keys = params.keySet();
        Iterator iterator = keys.iterator();
        FormBody.Builder builder = new FormBody.Builder();

        while (iterator.hasNext()){
            String key = String.valueOf(iterator.next());
            builder.add(key , String.valueOf(params.get(key)));
        }
        return builder.build();
    }


    /**
     * 是否显示dialog
     * @return
     */
    public boolean isShowDialog() {
        return showDialog;
    }

    /**
     * 设置是否显示dialog
     * @param showDialog
     */
    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}
