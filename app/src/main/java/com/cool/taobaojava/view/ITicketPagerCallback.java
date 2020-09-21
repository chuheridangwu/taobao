package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.TicketResult;

public interface ITicketPagerCallback extends IBaseCallback{

    void  onTicketLoaded(String cover, TicketResult result);
}
