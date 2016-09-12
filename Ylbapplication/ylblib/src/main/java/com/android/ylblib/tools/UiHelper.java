package com.android.ylblib.tools;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.ylblib.R;
import com.android.ylblib.views.ErrorViewLayout;

public class UiHelper {

    /**
     * bottom to up animation
     *
     * @param viewBack
     * @param view
     * @param height
     */
    public static void doAnimationBottomToUp(View viewBack, View view, int height) {
        AnimatorSet set = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();

            int temHeight = 0;
            if (height == 0) {
                temHeight = (int) view.getResources().getDimension(R.dimen.dp_400);
            } else {
                temHeight = height;
            }
            ObjectAnimator animatorTrans = ObjectAnimator.ofFloat(view, "translationY", temHeight, 0.0f);
            ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(viewBack, "alpha", 0.0f, 1.0f);
            set.playTogether(animatorAlpha, animatorTrans);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.setDuration(300);
            set.start();
        }

    }

    /**
     * top ti bottom animation
     *
     * @param viewBack
     * @param view
     * @param height
     * @param popWindow
     */
    public static void doAnimationUpToBottom(View viewBack, View view, int height, final PopupWindow popWindow) {
        AnimatorSet set = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();

            int temHeight = 0;
            if (height == 0) {
                temHeight = (int) view.getResources().getDimension(R.dimen.dp_400);
            } else {
                temHeight = height;
            }
            ObjectAnimator animatorTrans = ObjectAnimator.ofFloat(view, "translationY", 0.0f, temHeight);
            ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(viewBack, "alpha", 1.0f, 0.0f);
            set.playTogether(animatorAlpha, animatorTrans);
            set.setInterpolator(new DecelerateInterpolator());
            set.setDuration(300);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    popWindow.dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            set.start();
        }
    }


    /**设置listView高度 控制了高度*/
    public static int setListViewheight(BaseAdapter adapter, ListView listView, String[] itemArray) {
        View listItem = adapter.getView(0, null, listView);
        listItem.measure(0, 0);

        int height = listItem.getMeasuredHeight() * 5;

        if(itemArray.length > 5) {//控制listView的高度
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = height;
            listView.setLayoutParams(params);
        }else {
        }
        return height;
    }

    public interface ChooicePopDismissListener {
        void doAfterDismiss();
    }


    /**
     * 根据child 设置listview 高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



    /**
     *  text animation
     * @param view
     * @param color1
     * @param color2
     */
    public static void animTextColor(View view ,String animString ,  int color1 , int color2 , boolean isTextView){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            if(isTextView) {
                ((TextView)view).setTextColor(color2);
            }else{
                view.setBackgroundColor(color2);
            }
        }else{
            if(color1 == color2){
                if(isTextView) {
                    ((TextView) view).setTextColor(color2);
                }else{
                    view.setBackgroundColor(color2);
                }
            }else{
                ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view,
                        animString,
                        new ArgbEvaluator(),
                        color1,
                        color2);
                backgroundColorAnimator.setDuration(150);
                backgroundColorAnimator.start();
            }

        }

    }

    /**
     * 显示请求出错的页面  ,   传进来的view 的父view必须是linearlayout  并且 android:orientation="vertical"
     * @param context
     * @param view
     * @param clickListener
     */
    public static ErrorViewLayout showErrorView(Context context , View view , OnClickListener clickListener){
        if(view != null){
            try {
                LinearLayout linearLayout = (LinearLayout) view.getParent();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                ErrorViewLayout exceptionView = new ErrorViewLayout(context);
//                exceptionView.setListener(clickListener);
                exceptionView.setTag("EXCEPTION_VIEW");
                View firstChild = linearLayout.getChildAt(0);
                if (firstChild.getTag() != null && "EXCEPTION_VIEW".equals(firstChild.getTag().toString())) {
                    // 已经添加过networkview
                    firstChild.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.addView(exceptionView, 0, params);
                }
                return exceptionView;
            }catch (Exception e){
                Log.e("uiHelper" , "view's parent is not LinearLayout");
                e.printStackTrace();
            }
        }
        return null;
    }

}
