package com.android.ylblib.tools;

import android.util.Log;

/**
 * Auth：gaofei on 2015/11/4 17:07
 */
public class Logs {

    private final static boolean NEEDLOG = true;

    /**Log.i的输出为绿色，一般提示性的消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信息*/
    public static void i(String TAG , String content){
        if(NEEDLOG){
            Log.i(TAG , content);
        }
    }

    /**Log.v 的调试颜色为黑色的，任何消息都会输出，这里的v代表verbose啰嗦的意思，平时使用就是Log.v("","")*/
    public static void v(String TAG ,String content){
        if(NEEDLOG){
            Log.v(TAG , content);
        }
    }

    /**Log.d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择*/
    public static void d(String TAG ,String content){
        if(NEEDLOG){
            Log.d(TAG , content);
        }
    }

    /**Log.w的意思为橙色，可以看作为warning警告，一般需要我们注意优化Android代码，同时选择它后还会输出Log.e的信息*/
    public static void w(String TAG ,String content){
        if(NEEDLOG){
            Log.w(TAG , content);
        }
    }

    /**Log.e为红色，可以想到error错误，这里仅显示红色的错误信息，这些错误就需要我们认真的分析，查看栈的信息了*/
    public static void e(String TAG ,String content){
        if(NEEDLOG){
            Log.e(TAG , content);
        }
    }


}
