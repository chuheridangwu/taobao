package com.cool.taobaojava.utils;

import com.cool.taobaojava.presenter.ICategoryPagerPresenter;
import com.cool.taobaojava.presenter.impl.CategoryPagePresenterImpl;
import com.cool.taobaojava.presenter.impl.HomePresenterImpl;
import com.cool.taobaojava.presenter.impl.OnSellPagePresenterImpl;
import com.cool.taobaojava.presenter.impl.SelectedPagePresenterImpl;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;

public class PresentManager {

    private static final PresentManager ourInstance = new PresentManager();
    private final ICategoryPagerPresenter mCategoryPagerPresenter;
    private final HomePresenterImpl mHomePresenter;
    private final TicketPresenterImpl mTicketPresenter;
    private final SelectedPagePresenterImpl mSelectedPresenter;
    private final OnSellPagePresenterImpl mOnSellPresenter;


    public static PresentManager getInstance(){
        return ourInstance;
    }

    private PresentManager(){
        mCategoryPagerPresenter = new CategoryPagePresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
        mSelectedPresenter = new SelectedPagePresenterImpl();
        mOnSellPresenter = new OnSellPagePresenterImpl();
    }

    public ICategoryPagerPresenter getmCategoryPagerPresenter() {
        return mCategoryPagerPresenter;
    }

    public HomePresenterImpl getmHomePresenter() {
        return mHomePresenter;
    }

    public TicketPresenterImpl getmTicketPresenter() {
        return mTicketPresenter;
    }

    public SelectedPagePresenterImpl getmSelectedPresenter() {
        return mSelectedPresenter;
    }

    public OnSellPagePresenterImpl getmOnSellPresenter() {
        return mOnSellPresenter;
    }
}
