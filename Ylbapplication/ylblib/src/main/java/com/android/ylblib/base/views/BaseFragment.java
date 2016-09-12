package com.android.ylblib.base.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.android.ylblib.R;
import com.android.ylblib.base.BaseApplication;
import com.android.ylblib.base.presenter.BasePresenter;
import com.android.ylblib.net.RequestListener;
import com.android.ylblib.tools.StatusBarCompat;
import com.android.ylblib.views.AppBar;
import com.android.ylblib.views.ErrorViewLayout;


public abstract class BaseFragment extends Fragment implements RequestListener {

    private BasePresenter presenter;
    private View parentView;
    private ErrorViewLayout content;
    private AppBar appBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_root, null);
        initBaseView();
        View childView = onCreateContent(inflater , container , savedInstanceState);
        if (childView != null) {
            content = (ErrorViewLayout) parentView.findViewById(R.id.root_container);
            if(childView.getParent() != null){
                ((ViewGroup)childView.getParent()).removeView(childView);
            }
            content.addView(childView);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    public abstract View onCreateContent(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    /**
     * init base view
     */
    private void initBaseView(){
        appBar = (AppBar)parentView.findViewById(R.id.app_bar);
        controlAppBar(appBar);
        setAppBarHeight();
        appBar.setSupportActionBar((AppCompatActivity) getActivity());
        StatusBarCompat.compat(getActivity());
//        appBar.setBackImage(R.mipmap.back);
        appBar.setImageBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        appBar.setTitle(currentTitle());
//        appBar.setRightImage(R.mipmap.menu);
        appBar.setImageRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    /**
     * init base data
     */
    private void initBaseData(){
        presenter = new BasePresenter().setRquestEnity(BaseApplication.getInstanse().getRequestEnityClass()).setRequestListener(this);
    }

    /**
     * do the request for fragment
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBaseData();
        requestData(presenter);
    }

    /**
     * use to set fragment's title
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter && !TextUtils.isEmpty(currentTitle())){
            ((BaseActivity) getActivity()).setTitle(currentTitle());
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /**
     * 如果 当前的fragment不需要改变标题  ,返回 null 或是 "" ,默认重写返回 null  . 无需再管
     * @return
     */
    protected abstract String currentTitle();

    protected  abstract void  requestData(BasePresenter presenter);

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
        showLoadDialog(getActivity());
    }

    @Override
    public void hideProgressDialog() {
     removeDialog(getActivity());
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



    public void setTitle(String title){
        appBar.setTitle(title);
    }

    /**
     * set app bar height
     */
    private void setAppBarHeight(){
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        ViewGroup.LayoutParams params = appBar.getLayoutParams();
        params.height  = StatusBarCompat.getStatusBarHeight(getActivity()) + actionBarHeight;
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

}
