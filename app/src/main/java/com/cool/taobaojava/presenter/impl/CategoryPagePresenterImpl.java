package com.cool.taobaojava.presenter.impl;

import android.content.Intent;
import android.util.Log;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.presenter.ICategoryPagerPresenter;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagePresenterImpl implements ICategoryPagerPresenter {

    private Map<Integer,Integer> pagesInfo = new HashMap<>();
    public static final int DEFAULT_PAGE = 1;

    private CategoryPagePresenterImpl(){}

    private static ICategoryPagerPresenter sInstance = null;

    public  static ICategoryPagerPresenter getsInstance(){
        if (sInstance == null){
            sInstance = new CategoryPagePresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId){
                callback.onLoading();
            }
        }
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null){
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId,DEFAULT_PAGE);
        }
        Call<HomePagerContent> task = api.getHomePageContent(UrlUtils.createHomePagerUrl(categoryId,DEFAULT_PAGE));
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    HomePagerContent content = response.body();
                    Log.d("TAG", "onResponse: " + content.getData());
                    handleHomePageContentResult(content,categoryId);
                }else{
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                Log.d("this", "返回错误");
                handleNetworkError(categoryId);
            }
        });
    }

    @Override
    public void loaderMore(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId){

            }
        }
    }

    @Override
    public void reload(int categoryId) {

    }

    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId){
                callback.onError();
            }
        }
    }


    private void handleHomePageContentResult(HomePagerContent content, int categoryId) {
        for(ICategoryPagerCallback callback : callbacks){
            if (callback.getCategoryId() == categoryId){
                if (content == null || content.getData().size() == 0){
                    callback.onEmpty();
                }else {
                    callback.onContentLoaded(content.getData());
                }
            }
        }
    }

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();
    @Override
    public void registerViewCallback(ICategoryPagerCallback callBack) {
        if (callbacks.contains(callBack)){
            return;
        }
        callbacks.add(callBack);
    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callBack) {
        callbacks.remove(callBack);
    }
}
