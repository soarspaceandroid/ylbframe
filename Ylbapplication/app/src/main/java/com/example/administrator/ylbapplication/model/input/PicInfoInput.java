package com.example.administrator.ylbapplication.model.input;


import com.example.administrator.ylbapplication.model.BaseInput;
import com.example.administrator.ylbapplication.net.RequestEntry;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by gaofei on 2016/2/26.
 */

public class PicInfoInput extends BaseInput {

    private int id;

    public PicInfoInput(int id) {
        this.id = id;
    }


    @Override
    public Map<String, Object> createParams() {
        Map<String  , Object>  map = new HashMap<>();
        map.put("id" , id);
        return map;
    }

    @Override
    public Observable getData(RequestEntry enity) {
        return enity.getPicInfo(createFormBody());
    }
}
