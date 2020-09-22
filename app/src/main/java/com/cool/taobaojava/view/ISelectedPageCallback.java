package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.model.domain.SelectedContent;

public interface ISelectedPageCallback extends IBaseCallback {
    // 获取精选分类
    void onCategoriesLoaded(SelectedCategory result);

    void onContentLoad(SelectedContent content);
}
