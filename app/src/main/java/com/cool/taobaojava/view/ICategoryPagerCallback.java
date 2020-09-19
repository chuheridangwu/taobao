package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallback extends IBaseCallback {

    void onContentLoaded(List<HomePagerContent.DataBean> contents);

    void onLoadMoreError();

    void onLoadMoreEmpty();

    void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents);

    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);

    int getCategoryId();
}
