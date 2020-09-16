package com.cool.taobaojava.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  loadRootView(inflater,container,savedInstanceState);
        initView(rootView);
        initPresenter();
        loadData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
    }

    // 释放资源
    protected void release() {
    }

    // 初始化view
    protected void initView(View rootView){

    }

    // 创建Presenter
    protected void initPresenter() {

    }

    // 加载数据
    protected void loadData(){

    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // 获取资源类
        int resId = getRootViewResId();
        return inflater.inflate(resId,container,false);
    }

    protected abstract int getRootViewResId();
}
