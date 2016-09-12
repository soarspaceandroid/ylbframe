package com.android.ylblib.net;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by gaofei on 16/2/23.
 */
public class OkHttpFactory {

    private static OkHttpClient singleton;

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpFactory.class) {
                if (singleton == null) {
                    // log拦截器
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // 参数拦截器
                    ParamsInterceptor ParamsInterceptor = new ParamsInterceptor();
                    File cacheDir = new File(NetConfig.CACHAE_DIR, NetConfig.RESPONSE_CACHE);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.addInterceptor(ParamsInterceptor);
                    builder.addNetworkInterceptor(interceptor)
                            .cache(new Cache(cacheDir, NetConfig.RESPONSE_CACHE_SIZE))
                            .connectTimeout(NetConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(NetConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                    singleton = builder.build();
                }
            }
        }
        return singleton;
    }
}
