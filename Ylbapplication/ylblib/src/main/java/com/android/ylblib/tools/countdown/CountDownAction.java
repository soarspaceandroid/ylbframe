package com.android.ylblib.tools.countdown;

/**
 * Auth：gaofei
 * Since: 16/3/31 下午7:52
 */
public class CountDownAction {

    public TimeCount mTimeCount;

    public CountDownAction(long millisInFuture, long countDownInterval, CountDownListener listener) {
        mTimeCount = new TimeCount(millisInFuture, countDownInterval, listener);
    }

    public void onStart() {
        if(mTimeCount != null) {
            mTimeCount.start();
        }
    }

    public void onCancel() {
        if(mTimeCount != null) {
            mTimeCount.cancel();
            mTimeCount = null;
        }
    }


}
