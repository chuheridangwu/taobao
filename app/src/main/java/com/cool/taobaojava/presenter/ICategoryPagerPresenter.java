package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.view.ICategoryPagerCallback;
import com.cool.taobaojava.view.IHomeCallBack;

public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {

    void getContentByCategoryId(int categoryId);

    void loaderMore(int categoryId);

    void reload(int categoryId);
}
