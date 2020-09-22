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

    private ITicketPagerCallback mViewCallBack;
    private String mCover;

    enum LoadState{
        LOADING,SUCCESS,ERROR,NONE
    }

    private LoadState currentState = LoadState.NONE;
    private TicketResult mTicketResult;


    @Override
    public void getTicket(String title, String url, String cover) {
        currentState = LoadState.LOADING;
        onTicketLoading();
        String targetUrl = UrlUtils.getTicketUrl(url);
        mCover = UrlUtils.getTicketUrl(cover);


        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        TicketParams params = new TicketParams(targetUrl,title);
        Call<TicketResult> task = api.getTicket(params);
        task.enqueue(new Callback<TicketResult>() {

            @Override
            public void onResponse(Call<TicketResult> call, Response<TicketResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    mTicketResult = response.body();
                    onTicketLoadedSuccess();
                }else {
                    onLoadedTicketError();
                }
            }

            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {
                onLoadedTicketError();
            }
        });
    }

    private void onLoadedTicketError() {
        if (mViewCallBack!=null) {
            mViewCallBack.onError();
        }else {
            currentState = LoadState.ERROR;
        }
    }

    private void onTicketLoadedSuccess(){
        if (mViewCallBack!=null) {
            mViewCallBack.onTicketLoaded(mCover,mTicketResult);
        }else {
            currentState = LoadState.SUCCESS;
        }
    }

    @Override
    public void registerViewCallback(ITicketPagerCallback callBack) {
        if (currentState != LoadState.NONE){
           if (currentState == LoadState.SUCCESS){
               onTicketLoadedSuccess();
           }
            if (currentState == LoadState.ERROR){
                onLoadedTicketError();
            }
            if (currentState == LoadState.SUCCESS){
                onTicketLoading();
            }
       }
        mViewCallBack = callBack;
    }

    private void onTicketLoading() {
        if (mViewCallBack!=null) {
            mViewCallBack.onLoading();
        }
    }

    @Override
    public void unregisterViewCallback(ITicketPagerCallback callBack) {
        mViewCallBack = null;
    }
}
