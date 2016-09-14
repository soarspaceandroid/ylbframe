package com.android.ylblib.base.presenter;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * Auth：yujunyao
 * Since: 2016/9/14 16:09
 * Email：yujunyao@yonglibao.com
 */
public enum RxCenter {

    INSTANCE;

    private SparseArray<CompositeSubscription> compositeSubscriptionSparseArray;
    private Map<String, CompositeSubscription> mCompositeSubscriptionMap;

    RxCenter() {
        compositeSubscriptionSparseArray = new SparseArray<>();
        mCompositeSubscriptionMap = new HashMap<>();
    }

    //TODO 接收String类型
    public void removeCompositeSubscription(String taskId) {
        CompositeSubscription compositeSubscription = mCompositeSubscriptionMap.get(taskId);
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            mCompositeSubscriptionMap.remove(taskId);
        }
    }

    public CompositeSubscription getCompositeSubscription(String taskId) {
        CompositeSubscription compositeSubscription = mCompositeSubscriptionMap.get(taskId);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubscriptionMap.put(taskId, compositeSubscription);
        }
        return compositeSubscription;
    }

    //TODO 接收int类型
    public void removeCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription = compositeSubscriptionSparseArray.get(taskId);
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscriptionSparseArray.remove(taskId);
        }
    }

    public CompositeSubscription getCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription = compositeSubscriptionSparseArray.get(taskId);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            compositeSubscriptionSparseArray.put(taskId, compositeSubscription);
        }
        return compositeSubscription;
    }

}
