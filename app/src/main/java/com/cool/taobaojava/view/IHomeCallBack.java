package com.cool.taobaojava.view;

import com.cool.taobaojava.model.Categories;

public interface IHomeCallBack {
    void onCategoriesLoaded(Categories categories);

    void onNetworkError();

    void onLoading();

    void onEmpty();
}
