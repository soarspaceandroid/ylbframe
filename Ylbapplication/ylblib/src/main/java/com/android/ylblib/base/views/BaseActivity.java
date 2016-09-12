package com.android.ylblib.base.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.ylblib.R;
import com.android.ylblib.base.BaseApplication;
import com.android.ylblib.base.model.viewinterface.BaseViewInterface;
import com.android.ylblib.base.presenter.BasePresenter;
import com.android.ylblib.net.RequestListener;
import com.android.ylblib.tools.StatusBarCompat;
import com.android.ylblib.tools.database.DataCacheManager;
import com.android.ylblib.views.AppBar;
import com.android.ylblib.views.ErrorViewLayout;


public abstract class BaseActivity extends AppCompatActivity implements RequestListener , BaseViewInterface{


    private ErrorViewLayout content;
    public DataCacheManager dataCacheManager;
    private AppBar appBar;
    private BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_root);
        initBaseView();
        initBaseData();
    }

    /**set title*/
    protected abstract String currActivityName();


    /**
     * 控制back按钮的显示和隐藏  或是自定义左边的相关内容的隐藏
     * @param enable
     */
    public void controlBack(boolean enable){
        appBar.getLeftRoot().setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 控制menu按钮的显示和隐藏  或是自定义右边的相关内容的隐藏
     * @param enable
     */
    public void controlMenu(boolean enable){
        appBar.getRightRoot().setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 请求数据
     * @return
     */
    protected abstract void requestData(BasePresenter basePresenter);


    private void initBaseData() {
        presenter = new BasePresenter().setRquestEnity(BaseApplication.getInstanse().getRequestEnityClass()).setRequestListener(this);
        dataCacheManager  = BaseApplication.getDataCacheManager();
    }

    private void initBaseView() {
        content = (ErrorViewLayout) findViewById(R.id.root_container);
        appBar = (AppBar)findViewById(R.id.app_bar);
        controlAppBar(appBar);
        setAppBarHeight();
        appBar.setSupportActionBar(this);
        StatusBarCompat.compat(this);
//        appBar.setBackImage(R.mipmap.back);
        appBar.setImageBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appBar.setTitle(currActivityName());
//        appBar.setRightImage(R.mipmap.menu);
        appBar.setImageRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    public void setTitle(String title){
        appBar.setTitle(title);
    }

    /**
     * set app bar height
     */
    private void setAppBarHeight(){
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        ViewGroup.LayoutParams params = appBar.getLayoutParams();
        params.height  = StatusBarCompat.getStatusBarHeight(this) + actionBarHeight;
        appBar.setLayoutParams(params);
    }

    /**
     *  control appbar ,  如果需要修改appbar 请重写该方法
     * @param appbar
     */
    public void controlAppBar(AppBar appbar){

    }

    /**
     * get appbar
     * @return
     */
    public AppBar getAppBar(){
        return appBar;
    }


    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, content);
    }


    @Override
    public void setContentView(View view) {
        if (view == null) {
            return;
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        content.addView(view, layoutParams);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        content.addView(view, params);
    }

    @Override
    protected void onStart() {
        requestData(presenter);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    /**
     * dialog tag
     */
    private static String mDialogTag = "dialog";

    /**
     * 描述：显示加载框.
     *
     * @param context               the context
     */
    public static void showLoadDialog(Context context) {
    }

    /**
     * 描述：移除Fragment.
     *
     * @param context the context
     */
    public static void removeDialog(Context context) {
    }

    @Override
    public void showProgressDialog() {
        showLoadDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        removeDialog(this);
    }



    /**
     * 请求错误信息显示
     * @param meg
     */
    public void errorDisplay(String meg) {
        content.displayError(true);
        content.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData(presenter);
            }
        });
        content.getTextView().setText("Fuck the error : "+meg +" , you can click to load data again");
    }

    @Override
    public void errorHide() {
        content.displayError(false);
    }

}
