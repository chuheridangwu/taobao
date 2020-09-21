package com.cool.taobaojava.ui.activity;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseActivity;
import com.cool.taobaojava.model.domain.TicketResult;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.view.ITicketPagerCallback;

public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private TicketPresenterImpl mTicketPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresentManager.getInstance().getmTicketPresenter();
        mTicketPresenter.registerViewCallback(this);
    }

    @Override
    protected void release() {
        if (mTicketPresenter!=null) {
            mTicketPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
