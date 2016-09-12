package com.android.ylblib.tools.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ExpandableAdapter<T, V> extends BaseExpandableAdapter<T, V, ExpandableAdapterHelper> {

    public ExpandableAdapter(Context context, int groupLayoutResId, int childLayoutResId) {
        super(context, groupLayoutResId, childLayoutResId);
    }

    public ExpandableAdapter(Context context, int groupLayoutResId, int childLayoutResId, List<T> data) {
        super(context, groupLayoutResId, childLayoutResId, data);
    }

    @Override
    protected ExpandableAdapterHelper getAdapterHelper(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        if (childPosition == -1) {
            return ExpandableAdapterHelper.get(context, convertView, parent, groupLayoutResId, groupPosition, childPosition);
        }
        return ExpandableAdapterHelper.get(context, convertView, parent, childLayoutResId, groupPosition, childPosition);
    }
}
