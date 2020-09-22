package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.model.domain.OnSellContent;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.presenter.IOnSellPagePresenter;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.IOnSellPageCallback;

import java.net.HttpURLConnection;
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnSellPagePresenterImpl implements IOnSellPagePresenter {

    public static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    private IOnSellPageCallback mViewCallback;
    private final Api mApi;
    private boolean mIsLoading = false;

    private OnSellPagePresenterImpl(){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(Api.class);
    }

    @Override
    public void getOnSellContent() {
        if (mIsLoading) {
            return;
        }
        if (mViewCallback != null) {
            mViewCallback.onLoading();
        }

        mIsLoading = true;

        String url = UrlUtils.creatSellPageUrl(mCurrentPage);
        Call<OnSellContent> task = mApi.getOnSellPageContent(url);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    OnSellContent content = response.body();
                    onSuccess(content);
                }else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                onLoadError();
            }
        });

    }

    private void onSuccess(OnSellContent content) {
        mIsLoading = false;

        if (mViewCallback != null) {
            try {
                if (isEmpty(content)){
                    onEmpty();
                }else {
                    mViewCallback.onContentLoadSuccess(content);
                }
            }catch (Exception e){
                e.printStackTrace();
                onEmpty();
            }
        }
    }

    private boolean isEmpty(OnSellContent content) {
        int size  = content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
        return size == 0;
    }

    private void onEmpty() {
        if (mViewCallback != null) {
            mViewCallback.onEmpty();
        }
    }

    private void onLoadError(){
        mIsLoading = false;

        if (mViewCallback!=null) {
            mViewCallback.onError();
        }
    }

    @Override
    public void reLoad() {
        this.getOnSellContent();
    }

    @Override
    public void loaderMore() {
        mCurrentPage += 1;
        mIsLoading = true;

        String url = UrlUtils.creatSellPageUrl(mCurrentPage);

        Call<OnSellContent> task = mApi.getOnSellPageContent(url);
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    OnSellContent content = response.body();
                    onLoaderMore(content);
                }else {
                    onMoreLoadError();
                }
            }

            @Override
            public void onFailure(Call<OnSellContent> call, Throwable t) {
                onMoreLoadError();
            }
        });
    }

    private void onMoreLoadError() {
        mCurrentPage -= 1;
        mIsLoading = false;

        if (mViewCallback != null) {
            mViewCallback.onMoreLoadedError();
        }
    }

    private void onLoaderMore(OnSellContent content) {
        mIsLoading = false;

        if (mViewCallback != null) {
            if (isEmpty(content)){
                mViewCallback.onMoreLoadedEmpty();
            }else {
                mViewCallback.onMoreLoaded(content);
            }
        }
    }

    @Override
    public void registerViewCallback(IOnSellPageCallback callBack) {
            mViewCallback = callBack;
    }

    @Override
    public void unregisterViewCallback(IOnSellPageCallback callBack) {
        mViewCallback = null;
    }
}
