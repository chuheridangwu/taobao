package com.cool.taobaojava.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.cool.taobaojava.utils.LogUtils;

public class TbNestedScrollView extends NestedScrollView {

    private int mHeaderHeight = 584;
    private int originScroll = 0;

    public TbNestedScrollView(@NonNull Context context) {
        this(context,null);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setHeaderHeight(int headerHeight){
        this.mHeaderHeight = headerHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (originScroll < mHeaderHeight){ //如果移动的值小于头部的值
            scrollBy(dx,dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }

        super.onNestedPreScroll(target, dx, dy, consumed, type);
        Log.d("TAG", "onNestedScroll: 2");
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) { // 可以获取当前视图滚动的位置
        LogUtils.d(this,"----" + t);
        this.originScroll = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
