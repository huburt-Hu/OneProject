package com.app.julie.oneproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.app.julie.oneproject.R;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/13.
 */

public abstract class UpDialog extends Dialog {

    //屏幕宽度
    protected int mWidth;
    //屏幕高度
    protected int mHeight;
    protected View mView;

    public UpDialog(@NonNull Context context) {
        //传入style设置window参数
        super(context, R.style.UpDialogStyle);

        //获取屏幕参数
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;


        mView = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        mView.setMinimumWidth(mWidth);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(mView, param);

        onViewCreated(mView);
    }

    /**
     * 子类复写初始化布局控件
     *
     * @param view
     */
    protected void onViewCreated(View view) {

    }

    public abstract int getLayoutId();

    protected View getRootView() {
        return mView;
    }
}
