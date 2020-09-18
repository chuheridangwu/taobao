package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.Categories;

public interface IHomeCallBack extends IBaseCallback {
    void onCategoriesLoaded(Categories categories);

}
