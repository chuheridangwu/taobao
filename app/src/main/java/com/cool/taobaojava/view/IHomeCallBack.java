package com.cool.taobaojava.view;

import com.cool.taobaojava.base.BaseCallback;
import com.cool.taobaojava.model.domain.Categories;

public interface IHomeCallBack extends BaseCallback {
    void onCategoriesLoaded(Categories categories);

}
