package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.view.ISelectedPageCallback;

public interface ISelectedPagePresenter extends IBasePresenter<ISelectedPageCallback> {
    // 获取分类
    void getCategories();

    // 获取分类商品
    void getContentByCategory(SelectedCategory.DataBean item);

    // 重新加载内容
    void reloadContent();
}
