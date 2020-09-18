package com.cool.taobaojava.base;

import com.cool.taobaojava.view.ICategoryPagerCallback;

public interface IBasePresenter<T> {
    void registerViewCallback(T callBack);

    void unregisterViewCallback(T callBack);
}
