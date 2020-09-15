package com.cool.taobaojava.presenter;

import com.cool.taobaojava.view.IHomeCallBack;

public  interface IHomePresenter {

    // 获取商品分类
    void getCategories();

    void registerCallback(IHomeCallBack callBack);

    void unregisterCallback(IHomeCallBack callBack);
}
