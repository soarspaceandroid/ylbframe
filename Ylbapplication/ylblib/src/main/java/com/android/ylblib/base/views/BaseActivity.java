package com.android.ylblib.base.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
        //状态栏 底部导航栏
        if (AppBar.isConfigStatusbar()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_root);
        initBaseView();
        initBaseData();
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


    /**set title*/
    protected abstract String currActivityName();



    private void initBaseData() {
        presenter = new BasePresenter().setRquestEnity(BaseApplication.getInstanse().getRequestEnityClass()).setRequestListener(this);
        dataCacheManager  = BaseApplication.getDataCacheManager();
    }




    private void initBaseView() {
        content = (ErrorViewLayout) findViewById(R.id.root_container);
        appBar = (AppBar)findViewById(R.id.app_bar);
        controlAppBar(appBar);
        setAppBarHeight();
        appBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appBar.setTitle(currActivityName());
    }


    /**
     * 请求数据
     * @return
     */
    protected abstract void requestData(BasePresenter basePresenter);


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
        if (AppBar.isConfigStatusbar()) {
            //statusbar height
            appBar.setStatusbarVisible(true);
            appBar.setStatusBarColor(getResources().getColor(R.color.status_bar_color));
        } else {
            appBar.setStatusbarVisible(false);
        }
    }

    /**
     * get appbar
     * @return
     */
    public AppBar getAppBar(){
        return appBar;
    }





    /**
     * display  left text
     * @param text
     */
    public void showLeftText(String text){
        appBar.setLeftText(text);
    }

    /**
     * dislplay right text
     * @param text
     */
    public void showRightText(String text){
        appBar.setRightText(text);
    }

    /**
     * display right image
     * @param resId
     */
    public void showRightImage(int resId){
        appBar.setRightImage(resId);
    }

    /**
     * display left image
     * @param resId
     */
    public void showLeftImage(int resId){
        appBar.setLeftImage(resId);
    }


    /**
     *  设置statusbar color
     * @param statusbarColor
     */
    public void setStatusbarColor(int statusbarColor){
        appBar.setStatusBarColor(statusbarColor);
    }

    /**
     * 设置整个bar 颜色
     * @param color
     */
    public void setAppBarColor(int color){
        appBar.setAppbarColor(color);
    }

    /**
     * 设置appbar中除了statusbar的部分
     * @param color
     */
    public void setTitleBarColor(int color){
        appBar.setTitleBarColor(color);
    }



}
