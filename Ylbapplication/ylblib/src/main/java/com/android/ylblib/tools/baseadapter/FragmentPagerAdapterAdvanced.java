package com.android.ylblib.tools.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public abstract class FragmentPagerAdapterAdvanced extends FragmentPagerAdapter {
    protected int currentPosition = -1;
    protected Fragment currentFragment;

    public FragmentPagerAdapterAdvanced(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.currentPosition = position;
        if (object instanceof Fragment) {
            this.currentFragment = (Fragment) object;
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
