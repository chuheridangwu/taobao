package com.cool.taobaojava.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.Categories;
import com.cool.taobaojava.presenter.IHomePresenter;
import com.cool.taobaojava.presenter.impl.HomePresenterImpl;
import com.cool.taobaojava.view.IHomeCallBack;

public class HomeFragment extends BaseFragment  implements IHomeCallBack {

    private HomePresenterImpl mHomePresenter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    // 加载数据
    @Override
    protected void loadData() {

    }

    // 创建Presenter
    @Override
    protected void initPresenter() {
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerCallback(this);
    }

    // 返回的数据
    @Override
    public void onCategoriesLoaded(Categories categories) {
        mHomePresenter.getCategories();
    }

    // 取消回调注册
    @Override
    protected void release() {
        if (mHomePresenter!=null) {
            mHomePresenter.unregisterCallback(this);
        }
    }


}
