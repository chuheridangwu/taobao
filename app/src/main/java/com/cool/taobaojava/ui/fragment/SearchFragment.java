package com.cool.taobaojava.ui.fragment;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.Histories;
import com.cool.taobaojava.model.domain.SearchRecommend;
import com.cool.taobaojava.model.domain.SearchResult;
import com.cool.taobaojava.presenter.impl.SearchPresenter;
import com.cool.taobaojava.ui.adapter.SearchResultAdapter;
import com.cool.taobaojava.ui.custom.TextFlowLayout;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.view.ISearchViewCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends BaseFragment implements ISearchViewCallback {

    private SearchPresenter mSearchPresenter;
    private TextFlowLayout mTextFlowLayout;
    private TextFlowLayout mRecommendLayout;
    private View mHistoryView;
    private View mRecommendView;
    private ImageView mHistoryDelete;
    private RecyclerView mSearchList;
    private SearchResultAdapter mSearchAdapter;


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
        mTextFlowLayout = rootView.findViewById(R.id.search_history_view);
        mRecommendLayout = rootView.findViewById(R.id.search_recommend_view);
        mRecommendView = rootView.findViewById(R.id.search_recommend_container);
        mHistoryView = rootView.findViewById(R.id.search_history_container);
        mHistoryDelete = rootView.findViewById(R.id.search_history_delete);
        mSearchList = rootView.findViewById(R.id.search_result_list);

        mSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchAdapter = new SearchResultAdapter();
        mSearchList.setAdapter(mSearchAdapter);
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
        mSearchPresenter.doSearch("电脑");
        mSearchPresenter.getHistories();
    }

    @Override
    protected void initListener() {
        mHistoryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchPresenter.delHistory();
            }
        });
    }

    @Override
    public void onHistoriesLoaded(Histories histories) {
        LogUtils.d(this,"保存的关键字" + histories);
        if (histories == null || histories.getHistories().size() == 0){
            mHistoryView.setVisibility(View.GONE);
        }else {
            mHistoryView.setVisibility(View.VISIBLE);
            mTextFlowLayout.setTextList(histories.getHistories());
        }
    }

    @Override
    public void onHistoriesDeleted() {
        if (mSearchPresenter != null) {
            mSearchPresenter.getHistories();
        }
    }

    @Override
    public void onSearchSuccess(SearchResult result) {
        setUpState(State.SUCCESS);

        mRecommendView.setVisibility(View.GONE);
        mHistoryView.setVisibility(View.GONE);

        mSearchList.setVisibility(View.VISIBLE);

        mSearchAdapter.setData(result);
        mSearchList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(),5);
                outRect.bottom = SizeUtils.dip2px(getContext(),8);
            }
        });

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
        List<String> recommendKeywords = new ArrayList<>();
        for (SearchRecommend.DataBean item : recommendWords) {
            recommendKeywords.add(item.getKeyword());
        }
        if (recommendKeywords.size() == 0){
            mRecommendView.setVisibility(View.GONE);
        }else {
            mRecommendView.setVisibility(View.VISIBLE);
            mRecommendLayout.setTextList(recommendKeywords);
        }
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
