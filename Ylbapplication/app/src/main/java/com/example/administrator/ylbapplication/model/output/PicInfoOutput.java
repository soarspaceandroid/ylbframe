package com.example.administrator.ylbapplication.model.output;


import com.android.ylblib.base.model.bean.output.BaseBeanOutput;

import java.util.List;

/**
 * 图片Bean
 * Created by gaofei on 16/1/20.
 */
public class PicInfoOutput extends BaseBeanOutput {

    public boolean status;
    public int total;
    public List<TngouEntity> tngou;

    public static class TngouEntity {
        public int id;
        public String title;
        public int count;
        public int fcount;
        public int galleryclass;
        public String img;
        public int size;
        public int rcount;
        public long time;

    }
}
