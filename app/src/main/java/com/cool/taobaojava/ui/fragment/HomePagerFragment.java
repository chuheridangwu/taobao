package com.cool.taobaojava.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.presenter.ICategoryPagerPresenter;
import com.cool.taobaojava.presenter.impl.CategoryPagePresenterImpl;
import com.cool.taobaojava.utils.Constants;
import com.cool.taobaojava.view.ICategoryPagerCallback;

import java.util.List;

public class HomePagerFragment extends BaseFragment  implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int materialId;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = CategoryPagePresenterImpl.getsInstance();
        mCategoryPagerPresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        Log.d("TAG", "loadData: " + title + materialId);
        if (mCategoryPagerPresenter!=null) {
            mCategoryPagerPresenter.getContentByCategoryId(materialId);
        }
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter!=null) {
            mCategoryPagerPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {
        setUpState(State.SUCCESS);
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onError(){
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public int getCategoryId() {
        return materialId;
    }
}
