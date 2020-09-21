package com.cool.taobaojava.utils;

import com.cool.taobaojava.presenter.ICategoryPagerPresenter;
import com.cool.taobaojava.presenter.impl.CategoryPagePresenterImpl;
import com.cool.taobaojava.presenter.impl.HomePresenterImpl;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;

public class PresentManager {

    private static final PresentManager ourInstance = new PresentManager();
    private final ICategoryPagerPresenter mCategoryPagerPresenter;
    private final HomePresenterImpl mHomePresenter;
    private final TicketPresenterImpl mTicketPresenter;


    public static PresentManager getInstance(){
        return ourInstance;
    }

    private PresentManager(){
        mCategoryPagerPresenter = new CategoryPagePresenterImpl();
        mHomePresenter = new HomePresenterImpl();
        mTicketPresenter = new TicketPresenterImpl();
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
}
