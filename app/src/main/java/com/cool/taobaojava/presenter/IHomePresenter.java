package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.view.IHomeCallBack;

public  interface IHomePresenter extends IBasePresenter<IHomeCallBack> {

    // 获取商品分类
    void getCategories();

}
