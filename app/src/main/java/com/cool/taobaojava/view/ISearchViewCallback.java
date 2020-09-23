package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.SearchRecommend;
import com.cool.taobaojava.model.domain.SearchResult;

import java.util.List;

public interface ISearchViewCallback extends IBaseCallback {

    void onHistoriesLoaded(List<String> histories);

    void onHistoriesDeleted(List<String> histories);

    void onSearchSuccess(SearchResult result);

    void onMoreLoaded(SearchResult result);

    void onMoreLoadedError();

    void onMoreLoadedEmpty();

    void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommendWords);

}
