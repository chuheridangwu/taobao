package com.cool.taobaojava.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.cool.taobaojava.R;

class LoadingView extends AppCompatImageView {

    private float mDegrees = 0;
    private boolean mNeedRotates = true;

    public LoadingView(@NonNull Context context) {
        this(context,null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    // 刚出现在视图
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startRotate();
    }

    private void startRotate() {
        mNeedRotates = false;

        post(new Runnable() {
            @Override
            public void run() {
                mDegrees += 10;
                if (mDegrees >= 360){
                    mDegrees = 0;
                }
                invalidate();
                if (getVisibility() == VISIBLE || mNeedRotates){
                    removeCallbacks(this);
                }else {
                    postDelayed(this,100);
                }
            }
        });
    }

    // 从视图上移除
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    private void stopRotate() {
        mNeedRotates = true;
    }

    // 转动view
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(10,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }
}
