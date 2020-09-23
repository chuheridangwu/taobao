package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.view.ISearchViewCallback;

public interface ISearchPresenter extends IBasePresenter<ISearchViewCallback> {

    void getHistories();

    void delHistory();

    void doSearch(String keyword);

    void  research();

    void loaderMore();

    void gerRecommendWords();
}
