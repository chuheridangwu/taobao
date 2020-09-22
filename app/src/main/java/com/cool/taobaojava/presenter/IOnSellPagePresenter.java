package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.view.IOnSellPageCallback;

public interface IOnSellPagePresenter extends IBasePresenter<IOnSellPageCallback> {
        void getOnSellContent();

        void reLoad();

        void loaderMore();
}
