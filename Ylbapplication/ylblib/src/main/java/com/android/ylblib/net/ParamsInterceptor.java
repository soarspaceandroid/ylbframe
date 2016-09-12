package com.android.ylblib.net;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gaofei on 2016/9/8.
 */
public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        //统一添加请求头
        builder.header("X-Wap-Proxy-Cookie" , "none");


        //统一添加body
        if(request.body() instanceof FormBody){
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) request.body();
            for (int i = 0;i<oidFormBody.size();i++){
                newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
            }
            newFormBody.add("auth_timestamp", System.currentTimeMillis() / 1000 + "")
                    .add("auth_version", "1.0.0")
                    .add("client", "android");
            builder.method(request.method(),newFormBody.build());
        }


        return chain.proceed(builder.build());
    }
}
