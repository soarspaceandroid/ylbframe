package com.android.ylblib.views;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ylblib.R;
import com.android.ylblib.tools.StatusBarCompat;


/**
 * app bar
 */
public class AppBar extends LinearLayout {

    private static final String TAG = "AppBar";
    private LinearLayout mLeftRoot , mMiddleRoot , mRightRoot , appBar , titleBar ;
    private ImageView mLeftImage ,mRightImage ;
    private TextView mTitleView  , mLeftText , mRightText , mStatusBar;



    public AppBar(Context context) {
        super(context);
    }

    public AppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.appbar_layout ,this);
        mLeftRoot = (LinearLayout)view.findViewById(R.id.left_root);
        mMiddleRoot = (LinearLayout)view.findViewById(R.id.middle_root);
        mRightRoot = (LinearLayout)view.findViewById(R.id.right_root);
        appBar = (LinearLayout) view.findViewById(R.id.appbar);

        titleBar = (LinearLayout)view.findViewById(R.id.titlebar);

        mStatusBar = (TextView)view.findViewById(R.id.statusbar);

        mLeftImage = (ImageView)view.findViewById(R.id.image_left);
        mRightImage = (ImageView)view.findViewById(R.id.image_right);

        mTitleView = (TextView)view.findViewById(R.id.title);
        mLeftText = (TextView)view.findViewById(R.id.text_left);
        mRightText = (TextView)view.findViewById(R.id.text_right);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mStatusBar.getLayoutParams();
        params.height = StatusBarCompat.getStatusBarHeight(context);
        mStatusBar.setLayoutParams(params);

    }


    /**
     * set title
     * @param title
     */
    public void setTitle(String title){
        mTitleView.setText(title);
    }

    /**
     * set title size
     * @param sizeResource
     */
    public void setTitleSize(int sizeResource){
        mTitleView.setTextSize(sizeResource);
    }

    /**
     * set title color
     * @param colorResource
     */
    public void setTitleColor(int colorResource){
        mTitleView.setTextColor(colorResource);
    }


    /**
     * set image back
     * @param drableResource
     */
    public void setLeftImage(int drableResource){
        mLeftImage.setImageResource(drableResource);
    }



    /**
     * set image back
     * @param drableResource
     */
    public void setRightImage(int drableResource){
        mRightImage.setImageResource(drableResource);
    }


    /**
     * set left text by id
     * @param textId
     */
    public void setLeftText(int textId){
        mLeftText.setText(textId);
    }

    /**
     * set left text by string
     * @param text
     */
    public void setLeftText(String text){
        mLeftText.setText(text);
    }


    /**
     * set Right text by id
     * @param textId
     */
    public void setRightText(int textId){
        mRightText.setText(textId);
    }

    /**
     * set Right text by string
     * @param text
     */
    public void setRightText(String text){
        mRightText.setText(text);
    }


    /**
     * custom the view left
     * @param view
     */
    public void setLeftCustom(View view){
        if(null != view){
            mLeftRoot.removeAllViews();
            mLeftRoot.addView(view);
        }
    }

    /**
     * set the veiw right
     * @param view
     */
    public void setRightCustom(View view){
        if(null != view){
            mRightRoot.removeAllViews();
            mRightRoot.addView(view);
        }
    }
    /**
     * set the veiw right
     * @param view
     */
    public void setMiddleCustom(View view){
        if(null != view){
            mMiddleRoot.removeAllViews();
            mMiddleRoot.addView(view);
        }
    }

    /**
     * set left view visible
     * @param visible
     */
    public void setLeftVisible(boolean visible){
        mLeftImage.setVisibility(visible ? View.VISIBLE : View.GONE);
        mLeftText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * set left view visible
     * @param visible
     */
    public void setMiddleVisible(boolean visible){
        mTitleView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     * set left view visible
     * @param visible
     */
    public void setRightVisible(boolean visible){
        mRightImage.setVisibility(visible ? View.VISIBLE : View.GONE);
        mRightText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * get left root
     * @return
     */
    public LinearLayout getLeftRoot(){
        return mLeftRoot;
    }

    /**
     * get left root
     * @return
     */
    public LinearLayout getMiddleRoot(){
        return mMiddleRoot;
    }


    /**
     * get left root
     * @return
     */
    public LinearLayout getRightRoot(){
        return mRightRoot;
    }


    /**
     * set back listener
     * @param listener
     */
    public void setLeftListener(OnClickListener listener){
        mLeftRoot.setOnClickListener(listener);
    }

    /**
     * set right listener
     * @param listener
     */
    public void setRightListener(OnClickListener listener){
        mRightRoot.setOnClickListener(listener);
    }


    /**
     *  set left text visible
     * @param visible
     */
    public void setLeftTextVisible(boolean visible){
        mLeftText.setVisibility(visible ? VISIBLE : GONE);

    }


    /**
     *  set left image visible
     * @param visible
     */
    public void setLeftImageVisible(boolean visible){
        mLeftImage.setVisibility(visible ? VISIBLE : GONE);

    }


    /**
     *  set Right text visible
     * @param visible
     */
    public void setRightTextVisible(boolean visible){
        mRightText.setVisibility(visible ? VISIBLE : GONE);

    }


    /**
     *  set Right image visible
     * @param visible
     */
    public void setRightImageVisible(boolean visible){
        mRightImage.setVisibility(visible ? VISIBLE : GONE);

    }


    /**
     * set statusbar color
     * @param color
     */
    public void setStatusBarColor(int color){
        mStatusBar.setBackgroundColor(color);
    }


    /**
     * set appbar color
     * @param color
     */
    public void setAppbarColor(int color){
        appBar.setBackgroundColor(color);
    }


    /**
     *  set parent bar color
     * @param color
     */
    public void setTitleBarColor(int color){
        titleBar.setBackgroundColor(color);
    }


    /**
     * set statusbar visible
     */
    public void setStatusbarVisible(boolean visible){
        mStatusBar.setVisibility(visible?VISIBLE:GONE);
    }


    /**
     * get left image
     * @return
     */
    public ImageView getLeftImage(){
        return mLeftImage;
    }


    /**
     * get right image
     * @return
     */
    public ImageView getRightImage(){
        return mRightImage;
    }

    /**
     * get left text
     * @return
     */
    public TextView getLeftText(){
        return mLeftText;
    }

    /**
     * get right text
     * @return
     */
    public TextView getRightText(){
        return mRightText;
    }



    /**
     * 20以上配置通知栏
     *
     * @return
     */
    public static boolean isConfigStatusbar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }




}