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
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.presenter.impl.SelectedPagePresenterImpl;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.view.ISelectedPageCallback;

public class SelectedFragment extends BaseFragment implements ISelectedPageCallback {

    private SelectedPagePresenterImpl mSelectedPagePresenter;
    private SelectedCategory.DataBean mItem;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected void initPresenter() {
        mSelectedPagePresenter = PresentManager.getInstance().getmSelectedPresenter();
        mSelectedPagePresenter.registerViewCallback(this);
        mSelectedPagePresenter.getCategories();
    }

    @Override
    protected void release() {
        if (mSelectedPagePresenter == null) {
            mSelectedPagePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    public void onCategoriesLoaded(SelectedCategory result) {
        setUpState(State.SUCCESS);
        mItem = result.getData().get(0);
        mSelectedPagePresenter.getContentByCategory(mItem);
    }

    @Override
    public void onContentLoad(SelectedContent content) {

    }

    @Override
    public void onError() {
        setUpState(State.ERROR);

    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);

    }
}
