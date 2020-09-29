package com.cool.taobaojava.base;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        // 设置全局灰色
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        View contentContainer = getWindow().getDecorView();
        contentContainer.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);

        initView();

        initEvent();

        initPresenter();
    }

    protected void initPresenter() {
    }

    protected  void initView(){

    }

    protected  void initEvent(){

    }

    public abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.release();
    }

    // 让子类释放资源
    protected void release(){

    };
}
