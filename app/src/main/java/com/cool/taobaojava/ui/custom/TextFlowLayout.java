package com.cool.taobaojava.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextFlowLayout extends ViewGroup {

    public static final  int DEFAULT_SPACE = 10;
    private float mItemHorizontalSpace = DEFAULT_SPACE;
    private float mItemVerticalSpace = DEFAULT_SPACE;
    private int mSelfWith;
    private int mItemHeight;
    private OnFlowTextItemClickListener mItemClickListener;
    private List<String> mTextList = new ArrayList<>();

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

    public int getContentSize(){
        return mTextList.size();
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
        removeAllViews();

        mTextList.clear();
        mTextList.addAll(textList);

        // 倒转
        Collections.reverse(textList);

        for (String text : textList) {
         TextView view =  (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view,this,false);
         view.setText(text);

         view.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (mItemClickListener != null) {
                     mItemClickListener.onFlowItemClick(text);
                 }
             }
         });

         addView(view);
        }
    }



    // 总行
    private List<List<View>> lines = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            return;
        }
        // 清空数据
        lines.clear();
        // 单行
        List<View> line = null;

        // 当前自身的宽度
        mSelfWith = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        // 测量
        LogUtils.d(this,"onMeasure" + getChildCount());
        // 测量孩子
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);

            // 如果view不可见，不需要测量
            if (itemView.getVisibility()!=VISIBLE) {
                continue;
            }

            // 测量前
            LogUtils.d(this,"before height --> " + itemView.getMeasuredHeight());

            measureChild(itemView,widthMeasureSpec,heightMeasureSpec);

            if (line==null) {
                line = createNewLine(itemView);
            }else {
                if (canBrAdd(itemView,line)){
                    line.add(itemView);
                }else {
                    line = createNewLine(itemView);
                }
            }

            // 测量后
            LogUtils.d(this,"after height --> " + itemView.getMeasuredHeight());

        }
        //测量自己,要赋值自己的宽和高, +0.5f是为了四舍五入
        mItemHeight = getChildAt(0).getMeasuredHeight();
        int mSelfHeight =(int) (lines.size() * mItemHeight  + (lines.size() + 1) * mItemHorizontalSpace + 0.5f);
        setMeasuredDimension(mSelfWith,mSelfHeight);
    }

    private List<View> createNewLine(View itemView) {
        List<View> line = new ArrayList<>();
        line.add(itemView);
        lines.add(line);
        return line;
    }

    // 判断当前行是否可以接续添加数据
    private boolean canBrAdd(View itemView, List<View> line) {
        // 行内子所有View的宽度 +（line.size + 1） * mItemHorizontalSpace + itemView.getMeasuredWidth()
        // 如果 小于、等于 当前控件的宽度，则可以添加，否则不能添加
        int totalWith = itemView.getMeasuredWidth();
        for (View view : line){
            totalWith += view.getMeasuredWidth();
        }
        totalWith += mItemHorizontalSpace * ( line.size() + 1 );

        return totalWith <= mSelfWith;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        // 摆放子View
        int topOffset = (int)mItemHorizontalSpace;
        for (List<View> views : lines){
            int leftOffset = (int)mItemHorizontalSpace;
            for (View view : views){
                view.layout(leftOffset,topOffset,leftOffset + view.getMeasuredWidth(),topOffset + view.getMeasuredHeight());
                leftOffset += view.getMeasuredWidth() + mItemHorizontalSpace;
            }
            topOffset += mItemHeight + mItemVerticalSpace;
        }
    }

    public void setOnFlowTextItemClickListener(OnFlowTextItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface OnFlowTextItemClickListener{
        void onFlowItemClick(String text);
    }
}
