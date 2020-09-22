package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.presenter.ISelectedPagePresenter;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.ISelectedPageCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectedPagePresenterImpl implements ISelectedPagePresenter {

    private ISelectedPageCallback mViewCallback;
    private SelectedCategory.DataBean mCurrentCategoryItem;

    @Override
    public void getCategories() {
       Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<SelectedCategory> task = api.getSelectedPageCategory();
        task.enqueue(new Callback<SelectedCategory>() {
            @Override
            public void onResponse(Call<SelectedCategory> call, Response<SelectedCategory> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    if (mViewCallback != null) {
                        SelectedCategory category = response.body();
                        mViewCallback.onCategoriesLoaded(category);
                    }
                }else{
                    onLoadedError();
                }
            }

            @Override
            public void onFailure(Call<SelectedCategory> call, Throwable t) {
                onLoadedError();
            }
        });
    }

    private void onLoadedError(){
        if (mViewCallback == null) {
            mViewCallback.onError();
        }
    }

    @Override
    public void getContentByCategory(SelectedCategory.DataBean item) {
        mCurrentCategoryItem = item;
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);

        String url = UrlUtils.getSelectedPageContentUrl(item.getFavorites_id());
        Call<SelectedContent> task = api.getSelectedPageContent(url);
        task.enqueue(new Callback<SelectedContent>() {
            @Override
            public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    if (mViewCallback != null) {
                        mViewCallback.onContentLoad(response.body());
                    }
                }else{
                    onLoadedError();
                }
            }

            @Override
            public void onFailure(Call<SelectedContent> call, Throwable t) {

            }
        });
    }

    @Override
    public void reloadContent() {
        if (mCurrentCategoryItem !=null){
            this.getContentByCategory(mCurrentCategoryItem);
        }
    }

    @Override
    public void registerViewCallback(ISelectedPageCallback callBack) {
        mViewCallback = callBack;
    }

    @Override
    public void unregisterViewCallback(ISelectedPageCallback callBack) {
        mViewCallback = null;
    }
}
