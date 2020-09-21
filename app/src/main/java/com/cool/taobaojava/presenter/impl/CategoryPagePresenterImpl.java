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
    private Integer mCurrentPage;

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId){
                callback.onLoading();
            }
        }

        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null){
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId,DEFAULT_PAGE);
        }
        Call<HomePagerContent> task = createTask(categoryId, targetPage);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    HomePagerContent content = response.body();
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

    private Call<HomePagerContent> createTask(int categoryId, Integer targetPage) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String url = UrlUtils.createHomePagerUrl(categoryId,targetPage);
        return api.getHomePageContent(url);
    }

    @Override
    public void loaderMore(int categoryId) {
        mCurrentPage = pagesInfo.get(categoryId);
        if (mCurrentPage == null){
            mCurrentPage = 1;
        }
        mCurrentPage++;
        Call<HomePagerContent> task = createTask(categoryId, mCurrentPage);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    HomePagerContent result = response.body();
                    handleLoaderResult(result,categoryId);
                }else {
                    handleNetworkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                handleLoaderMoreError(categoryId);
            }
        });
    }

    private void handleLoaderResult(HomePagerContent result, int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (result == null || result.getData().size() == 0){
                callback.onLoadMoreEmpty();
            }else {
                callback.onLoadMoreLoaded(result.getData());
            }
        }
    }

    private void handleLoaderMoreError(int categoryId) {
        mCurrentPage--;
        pagesInfo.put(mCurrentPage,categoryId);
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoadMoreError();
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
                    List<HomePagerContent.DataBean> data = content.getData();
                    List<HomePagerContent.DataBean> looperData = data.subList(data.size() - 5,data.size());
                    callback.onLooperListLoaded(looperData);
                    callback.onContentLoaded(data);
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
