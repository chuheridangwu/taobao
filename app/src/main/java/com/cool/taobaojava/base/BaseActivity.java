package com.cool.taobaojava.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        initView();

        initEvent();
    }

    protected  void initView(){

    }

    protected  void initEvent(){

    }

    public abstract int getLayoutResId();
}
