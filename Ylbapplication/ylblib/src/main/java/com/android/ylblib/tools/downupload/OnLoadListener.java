package com.android.ylblib.tools.downupload;

/**
 * Auth：gaofei
 * Since: 2016/3/9 17:31
 */
public interface OnLoadListener {

    /**
     * up and down load fail
     */
    void loadFail();

    /**
     * up and down load success
     * @param response
     */
    void loadSuccess(String response);


    /**
     * up and down load progress
     * @param bytesRead
     * @param contentLength
     * @param done
     */
    void loadProgress(long bytesRead, long contentLength, boolean done);

}
