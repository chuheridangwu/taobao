package com.cool.taobaojava.utils;

import android.content.Context;
import android.content.Intent;

import com.cool.taobaojava.model.domain.IBaseInfo;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;
import com.cool.taobaojava.ui.activity.TicketActivity;

public class TicketUtil {
    public static void toTicketPage(Context context, IBaseInfo info){
        String title = info.getTitle();
        String url = info.getUrl();
        String cover = info.getCover();

        TicketPresenterImpl ticketPresenter = PresentManager.getInstance().getmTicketPresenter();
        ticketPresenter.getTicket(title,url,cover);
        context.startActivity(new Intent(context, TicketActivity.class));
    }
}
