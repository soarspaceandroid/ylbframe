package com.android.ylblib.tools;

import com.android.ylblib.base.BaseApplication;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by gaofei on 2016/9/12.
 */
public class PicassoHelper  {

    private static Picasso picasso;

    /**
     * get instance
     * @return
     */
    public static Picasso getInstance(){
        if(picasso == null){
            OkHttpClient client = new OkHttpClient();
            picasso = new Picasso.Builder(BaseApplication.getContext()).downloader(new OkHttp3Downloader(client)).build();
        }

        return picasso;
    }
}
