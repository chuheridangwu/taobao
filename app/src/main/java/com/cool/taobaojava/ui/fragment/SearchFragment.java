package com.cool.taobaojava.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.SearchRecommend;
import com.cool.taobaojava.model.domain.SearchResult;
import com.cool.taobaojava.presenter.impl.SearchPresenter;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.view.ISearchViewCallback;

import java.util.List;

public class SearchFragment extends BaseFragment implements ISearchViewCallback {

    private SearchPresenter mSearchPresenter;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_layout,container,false);
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }

    @Override
    protected void release() {
        mSearchPresenter.unregisterViewCallback(this);
    }

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresentManager.getInstance().getmSearchPresenter();
        mSearchPresenter.registerViewCallback(this);
        mSearchPresenter.gerRecommendWords();
        mSearchPresenter.doSearch("毛衣");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onHistoriesLoaded(List<String> histories) {
        LogUtils.d(this,"保存的关键字" + histories);

    }

    @Override
    public void onHistoriesDeleted(List<String> histories) {

    }

    @Override
    public void onSearchSuccess(SearchResult result) {
        LogUtils.d(this,"搜索结果" + result.getData().getTbk_dg_material_optional_response().getResult_list());

    }

    @Override
    public void onMoreLoaded(SearchResult result) {

    }

    @Override
    public void onMoreLoadedError() {

    }

    @Override
    public void onMoreLoadedEmpty() {

    }

    @Override
    public void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommendWords) {
        LogUtils.d(this,"关键词的数据" + recommendWords.toString());
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
