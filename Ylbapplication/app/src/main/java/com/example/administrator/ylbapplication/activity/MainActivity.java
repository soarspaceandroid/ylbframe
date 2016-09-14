package com.example.administrator.ylbapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.ylblib.base.presenter.BasePresenter;
import com.android.ylblib.base.views.BaseActivity;
import com.example.administrator.ylbapplication.R;
import com.example.administrator.ylbapplication.model.input.PicInfoInput;
import com.example.administrator.ylbapplication.model.output.PicInfoOutput;

public class MainActivity extends BaseActivity{
    TextView test;


    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        test = (TextView)findViewById(R.id.test);
    }

    @Override
    protected void requestData(BasePresenter basePresenter) {
        basePresenter.setBaseViewInterface(this).setInput(new PicInfoInput(2)).setOutputClass(PicInfoOutput.class).load(true);
    }

    @Override
    protected String currActivityName() {
        return "test";
    }

    @Override
    public void updateView(Object o) {
      if(o instanceof PicInfoOutput){
          test.setText("请求成功----- "+((PicInfoOutput) o).tngou.get(1).title);
          dataManager.insertObject((PicInfoOutput)o);
          Log.e("soar","get 0---- "+dataManager.getObject(PicInfoOutput.class).tngou.get(5).title);
      }
    }

    @Override
    public void showError(String msg) {
        Log.e("soar" , "test error --- "+msg);
    }


    @Override
    public String[] getNeedCancelRequest() {
        return new String[]{PicInfoInput.class.getSimpleName()};
    }
}
