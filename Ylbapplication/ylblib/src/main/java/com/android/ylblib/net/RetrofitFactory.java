package com.android.ylblib.net;


import com.android.ylblib.base.BaseApplication;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaofei on 16/1/19.
 */
public class RetrofitFactory {

    private static Retrofit retrofit;

    /**
     * 创建API实例
     *
     * @param cls Api定义类的类型
     * @param <T> 范型
     * @return API实例
     */
    public static <T> T getInstance(Class<T> cls) {


        return restAdapterInstance().create(cls);
    }

    private static Retrofit restAdapterInstance() {


        if (retrofit == null) synchronized (RetrofitFactory.class) {

            return retrofit = new Retrofit.Builder()
                    .baseUrl(BaseApplication.getInstanse().getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(OkHttpFactory.getInstance())
                    .validateEagerly(true)
                    .build();
        }
        else {
            return retrofit;
        }
    }
}
