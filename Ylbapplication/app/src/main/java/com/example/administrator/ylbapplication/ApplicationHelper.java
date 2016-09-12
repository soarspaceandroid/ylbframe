package com.example.administrator.ylbapplication;

import com.android.ylblib.interfaces.helpers.BaseApplicationHelper;
import com.example.administrator.ylbapplication.net.Config;
import com.example.administrator.ylbapplication.net.RequestEntry;

/**
 * Created by gaofei on 2016/9/12.
 */
public class ApplicationHelper implements BaseApplicationHelper{

    @Override
    public String getBaseUrl() {
        return Config.TIANGOU;
    }


    @Override
    public Class getEnityClass() {
        return RequestEntry.class;
    }
}
