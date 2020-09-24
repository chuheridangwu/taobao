package com.cool.taobaojava.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.utils.LogUtils;

import java.util.List;

public class TextFlowLayout extends ViewGroup {

    public static final  int DEFAULT_SPACE = 10;
    private float mItemHorizontalSpace = DEFAULT_SPACE;
    private float mItemVerticalSpace = DEFAULT_SPACE;

    public float getmItemHorizontalSpace() {
        return mItemHorizontalSpace;
    }

    public void setmItemHorizontalSpace(float mItemHorizontalSpace) {
        this.mItemHorizontalSpace = mItemHorizontalSpace;
    }

    public float getmItemVerticalSpace() {
        return mItemVerticalSpace;
    }

    public void setmItemVerticalSpace(float mItemVerticalSpace) {
        this.mItemVerticalSpace = mItemVerticalSpace;
    }

    public TextFlowLayout(Context context) {
        this(context,null);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // 获取到相关属性
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.FlowTextStyle);
        mItemHorizontalSpace = ta.getDimension(R.styleable.FlowTextStyle_horizontalSpace,DEFAULT_SPACE);
        mItemVerticalSpace = ta.getDimension(R.styleable.FlowTextStyle_verticalSpace,DEFAULT_SPACE);
        ta.recycle();
    }

    public void setTextList(List<String> textList){
        for (String text : textList) {
         TextView view =  (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view,this,false);
         view.setText(text);
         addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 测量

        LogUtils.d(this,"onMeasure" + getChildCount());
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
            // 摆放子View
    }
}
