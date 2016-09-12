package com.android.ylblib.tools.downupload;

/**
 * Created by gaofei on 2016/3/18.
 */

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 进度回调辅助类
 */
public class ProgressHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     * @param client 待包装的OkHttpClient
     * @param loadListener 进度回调接口
     * @return 包装后的OkHttpClient，使用clone方法返回
     */
    public static OkHttpClient addProgressResponseListener(OkHttpClient client,final OnLoadListener loadListener){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), loadListener))
                        .build();
            }
        };
        return client.newBuilder()
                .addInterceptor(interceptor)
                .build();
    }

    /**
     * 包装请求体用于上传文件的回调
     * @param requestBody 请求体RequestBody
     * @param loadListener 进度回调接口
     * @return 包装后的进度回调请求体
     */
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody,OnLoadListener loadListener){
        //包装请求体
        return new ProgressRequestBody(requestBody,loadListener);
    }
}