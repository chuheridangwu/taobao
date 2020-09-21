package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.TicketParams;
import com.cool.taobaojava.model.domain.TicketResult;
import com.cool.taobaojava.presenter.ITicketPresenter;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.ITicketPagerCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketPresenterImpl implements ITicketPresenter {
    @Override
    public void getTicket(String title, String url, String cover) {

        String targetUrl = UrlUtils.getTicketUrl(url);

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        TicketParams params = new TicketParams(targetUrl,title);
        Call<TicketResult> task = api.getTicket(params);
        task.enqueue(new Callback<TicketResult>() {
            @Override
            public void onResponse(Call<TicketResult> call, Response<TicketResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    TicketResult result = response.body();

                }else {

                }
            }

            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void registerViewCallback(ITicketPagerCallback callBack) {

    }

    @Override
    public void unregisterViewCallback(ITicketPagerCallback callBack) {

    }
}
