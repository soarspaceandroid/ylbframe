package com.example.administrator.ylbapplication;

import com.android.ylblib.base.BaseApplication;
import com.android.ylblib.interfaces.BaseApplicationHelper;
import com.example.administrator.ylbapplication.net.RequestEntry;

/**
 * Created by gaofei on 2016/9/8.
 */
public class Application extends BaseApplication{

    private final BaseApplicationHelper applicationHelper = new ApplicationHelper();

    @Override
    public Class getEnityClass() {
        return RequestEntry.class;
    }


    @Override
    public String getBaseUrl() {
        return applicationHelper.getBaseUrl();
    }
}