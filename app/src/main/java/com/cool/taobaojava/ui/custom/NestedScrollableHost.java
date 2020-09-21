package com.cool.taobaojava.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

public class NestedScrollableHost extends FrameLayout {


    public NestedScrollableHost(@NonNull Context context) {
        this(context,null);
    }

    public NestedScrollableHost(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestedScrollableHost(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public NestedScrollableHost(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int touchSlop = 0;
    private float initialX = 0;
    private float initialY = 0;
    private ViewPager2 parentViewPager;
    private View child;

    public ViewPager2 getParentViewPager() {
        View view = (View) getParent();
        while (view != null && view instanceof ViewPager2){
            view = (View) view.getParent();
        }

        return (ViewPager2)view;
    }

    public View getChild() {
        if (getChildCount() > 0){
            return getChildAt(0);
        }
        return child;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        handleInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    private void handleInterceptTouchEvent(MotionEvent e){
        int orientation = parentViewPager.getOrientation();
        if (!canChildScroll(orientation,-1) && !canChildScroll(orientation, 1f)){
            return;
        }

        if (e.getAction() == MotionEvent.ACTION_DOWN){
            initialX = e.getX();
            initialY = e.getY();
        }else if (e.getAction() == MotionEvent.ACTION_MOVE){
            float dx = e.getX() - initialX;
            float dy = e.getY() - initialY;
            boolean isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL;

            float scaledDX = (float) (Math.abs(dx) * (isVpHorizontal ? 0.5 : 1));
            float scaledDY = (float) (Math.abs(dy) * (isVpHorizontal ? 1 : 0.5));

            if (scaledDX > touchSlop || scaledDY > touchSlop){
                if (isVpHorizontal == (scaledDY > scaledDX)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    if (canChildScroll(orientation,isVpHorizontal ? dx : dy)){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }

        }

    }

    private boolean canChildScroll(int orientation, float delta){
        int direction = (int)-Math.abs(delta);
        if (orientation == 0){
           return child.canScrollHorizontally(direction) ? true : false;
        }else {
            return child.canScrollVertically(direction) ? true : false;
        }
    }
}
