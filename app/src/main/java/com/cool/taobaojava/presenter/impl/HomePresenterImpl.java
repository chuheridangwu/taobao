package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.presenter.IHomePresenter;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.view.IHomeCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallBack mCallBack;

    @Override
    public void getCategories() {
        if (mCallBack != null){
            mCallBack.onLoading();
        }
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                // 返回数据
                if (response.code() == HttpURLConnection.HTTP_OK){
                    Categories categories = response.body();
                    if (mCallBack!=null) {
                        if (categories == null || categories.getData().size() == 0){
                            mCallBack.onEmpty();
                            return;
                        }
                        mCallBack.onCategoriesLoaded(categories);
                    }

                }else {
                    if (mCallBack!=null) {
                        mCallBack.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                // 加载失败的结果
                if (mCallBack!=null) {
                    mCallBack.onError();
                }
            }
        });
    }

    @Override
    public void registerViewCallback(IHomeCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallback(IHomeCallBack callBack) {
        mCallBack = null;
    }
}
