package com.example.administrator.ylbapplication.net;


import com.example.administrator.ylbapplication.model.output.PicInfoOutput;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by gaofei on 2016/4/26.
 */
public interface RequestEntry {

    /**
     * 获取picinfo
     * @return
     */
    @POST(RequestApi.PIC_API)
    public Observable<PicInfoOutput> getPicInfo(@Body FormBody formBody);

}
