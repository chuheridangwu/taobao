package com.cool.taobaojava.view;

import com.cool.taobaojava.model.domain.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallback {

    void onContentLoaded(List<HomePagerContent.DataBean> contents);

    void onLoading(int categoryId);

    void onLError(int categoryId);

    void onEmpty(int categoryId);

    void onLoadMoreError(int categoryId);

    void onLoadMoreEmpty(int categoryId);

    void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents);

    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);
}
