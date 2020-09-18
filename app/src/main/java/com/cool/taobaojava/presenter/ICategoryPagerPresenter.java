package com.cool.taobaojava.presenter;

interface ICategoryPagerPresenter {

    void getContentByCategoryId(int categoryId);

    void  loaderMore(int categoryId);

    void reload(int categoryId);
}
