package com.android.ylblib.tools.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ViewPagerAdapter<T> extends BaseViewPagerAdapter<T, PagerAdapterHelper> {

    public ViewPagerAdapter(Context context) {
        super(context);
    }

    public ViewPagerAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public ViewPagerAdapter(Context context, List<T> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected PagerAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
        if (layoutResId != 0) {
            return PagerAdapterHelper.get(context, convertView, parent, layoutResId, position);
        } else {
            if (convertView == null) {
                return PagerAdapterHelper.get(context, createView(parent, position), parent, position, true);
            } else {
                return PagerAdapterHelper.get(context, convertView, parent, position, true);
            }
        }
    }
}
