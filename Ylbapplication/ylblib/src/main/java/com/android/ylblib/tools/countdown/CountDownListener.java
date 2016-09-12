package com.android.ylblib.tools.countdown;

/**
 * Auth：gaofei
 * Since: 16/3/28 上午11:45
 */

public interface CountDownListener {

    void onTick(long millisUntilFinished);

    void onFinish();

}
