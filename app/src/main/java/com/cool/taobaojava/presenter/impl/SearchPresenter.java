package com.cool.taobaojava.presenter.impl;

import com.cool.taobaojava.model.Api;
import com.cool.taobaojava.model.domain.Histories;
import com.cool.taobaojava.model.domain.SearchRecommend;
import com.cool.taobaojava.model.domain.SearchResult;
import com.cool.taobaojava.presenter.ISearchPresenter;
import com.cool.taobaojava.utils.JsonCacheUtil;
import com.cool.taobaojava.utils.RetrofitManager;
import com.cool.taobaojava.view.ISearchViewCallback;

import java.net.HttpURLConnection;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchPresenter implements ISearchPresenter {

    private final Api mApi;
    private ISearchViewCallback mViewCallback;

    private static final int DEFAULT_PAGE = 0;
    private int mCurrentPage = DEFAULT_PAGE;
    private String mCurrentKeyboard;
    private JsonCacheUtil mJsonCacheUtil;


    public SearchPresenter(){
        RetrofitManager instance = RetrofitManager.getInstance();
        Retrofit retrofit = instance.getRetrofit();
        mApi = retrofit.create(Api.class);
        mJsonCacheUtil = JsonCacheUtil.getInstance();
    }

    @Override
    public void getHistories() {
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORIES,Histories.class);
        if (mViewCallback != null && histories != null && histories.getHistories() != null && histories.getHistories().size() != 0) {
            mViewCallback.onHistoriesLoaded(histories.getHistories());
        }
    }

    @Override
    public void delHistory() {
        mJsonCacheUtil.delCache(KEY_HISTORIES);
    }

    @Override
    public void doSearch(String keyword) {
        if (mCurrentKeyboard == null || !mCurrentKeyboard.endsWith(keyword)) {
            mCurrentKeyboard =  keyword;
            saveHistory(keyword);
        }

        if (mViewCallback != null) {
            mViewCallback.onLoading();
        }
        Call<SearchResult> task = mApi.doSearch(mCurrentPage,keyword);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    handleSearchResult(response.body());
                }else {
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                onError();
            }
        });

    }

    private void onError() {
        if (mViewCallback != null) {
            mViewCallback.onError();
        }
    }

    private void handleSearchResult(SearchResult body) {
        if (mViewCallback != null) {
            if (isResultEmpty(body)){
                mViewCallback.onEmpty();
            }else {
                mViewCallback.onSearchSuccess(body);
            }
        }
    }

    private boolean isResultEmpty(SearchResult result){
        try {
            return result == null || result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data().size() == 0;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public void research() {
        if (mCurrentKeyboard==null) {
            if (mViewCallback != null) {
                mViewCallback.onEmpty();
            }
        }else {
            doSearch(mCurrentKeyboard);
        }
    }

    @Override
    public void loaderMore() {
        mCurrentPage += 1;
        if (mCurrentKeyboard == null) {
            if (mViewCallback != null) {

            }
        }else {
           doSearchMore();
        }
    }

    private void doSearchMore() {
        Call<SearchResult> task = mApi.doSearch(mCurrentPage,mCurrentKeyboard);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    onLoadMoreSuccess(response.body());
                }else {
                    onLoadMoreError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                onLoadMoreError();
            }
        });
    }

    private void onLoadMoreSuccess(SearchResult result){
        if (mViewCallback != null) {
            if (isResultEmpty(result)){
                mViewCallback.onEmpty();
            }else {
                mViewCallback.onSearchSuccess(result);
            }
        }
    }

    private void onLoadMoreError(){
        mCurrentPage--;
        if (mViewCallback != null) {
            mViewCallback.onMoreLoadedError();
        }
    }

    @Override
    public void gerRecommendWords() {
        Call<SearchRecommend> task = mApi.getRecommendWords();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK){
                    SearchRecommend recommend = response.body();
                    if (mViewCallback != null) {
                        mViewCallback.onRecommendWordsLoaded(recommend.getData());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {

            }
        });
    }

    public static final  String KEY_HISTORIES = "key_histories";
    public final static  int DEFAULT_HISTORIES_SIZE = 10;
    private int historiesMaxSize = DEFAULT_HISTORIES_SIZE;
    // 添加历史记录
    private void saveHistory(String history){
        Histories histories = mJsonCacheUtil.getValue(KEY_HISTORIES,Histories.class);
        List<String> historiesList = null;

        if (histories != null && histories.getHistories() != null) {
            historiesList =  histories.getHistories();
            if (historiesList.contains(history)) {
                historiesList.remove(history);
            }
        }

        if (historiesList==null) {
            historiesList = new ArrayList<>();
        }
        if (histories == null) {
            histories = new Histories();
        }

        histories.setHistories(historiesList);

        if (historiesList.size() > historiesMaxSize) {
            historiesList = historiesList.subList(0,historiesMaxSize);
        }

        historiesList.add(history);

        mJsonCacheUtil.saveCache(KEY_HISTORIES,histories);
    }

    @Override
    public void registerViewCallback(ISearchViewCallback callBack) {
        mViewCallback = callBack;
    }

    @Override
    public void unregisterViewCallback(ISearchViewCallback callBack) {
        mViewCallback = null;
    }
}
