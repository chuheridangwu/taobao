package com.cool.taobaojava.presenter;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.base.IBasePresenter;
import com.cool.taobaojava.view.ITicketPagerCallback;

public interface ITicketPresenter extends IBasePresenter<ITicketPagerCallback> {
    void  getTicket(String title, String url, String cover);
}
