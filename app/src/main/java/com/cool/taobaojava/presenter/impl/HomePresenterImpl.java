package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.Categories;
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
        LogUtils.e(this,"开始加载数据");

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
                        mCallBack.onCategoriesLoaded(categories);
                    }
                    LogUtils.e(this,"加载数据成功" + categories.toString());

                }else {
                    LogUtils.e(this,"加载数据失败");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                // 加载失败的结果
                //TODO
                LogUtils.e(this,"加载数据错误");
            }
        });
    }

    @Override
    public void registerCallback(IHomeCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterCallback(IHomeCallBack callBack) {
        mCallBack = null;
    }
}
