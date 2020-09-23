package com.cool.taobaojava.ui.fragment;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.OnSellContent;
import com.cool.taobaojava.presenter.impl.OnSellPagePresenterImpl;
import com.cool.taobaojava.ui.adapter.OnSellContentAdapter;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.view.IOnSellPageCallback;

public class OnSellFragment extends BaseFragment implements IOnSellPageCallback {

    private OnSellPagePresenterImpl mOnSellPresenter;
    private RecyclerView mContentList;
    private OnSellContentAdapter mContentAdapter;
    private static final int DEFAULT_SPAN_COUNT = 2;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_on_sell;
    }

    @Override
    protected void initView(View rootView){
        mContentList = rootView.findViewById(R.id.on_sell_content_list);
        mContentList.setLayoutManager(new GridLayoutManager(getContext(),DEFAULT_SPAN_COUNT));
        mContentAdapter = new OnSellContentAdapter();
        mContentList.setAdapter(mContentAdapter);
        // 设置每个item的边距
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(),2.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(),2.5f);
                outRect.left = SizeUtils.dip2px(getContext(),2.5f);
                outRect.right = SizeUtils.dip2px(getContext(),2.5f);
            }
        });
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void release() {
        mOnSellPresenter.unregisterViewCallback(this);
    }

    @Override
    protected void initPresenter() {
        mOnSellPresenter = PresentManager.getInstance().getmOnSellPresenter();
        mOnSellPresenter.registerViewCallback(this);
        mOnSellPresenter.getOnSellContent();
    }

    @Override
    public void onContentLoadSuccess(OnSellContent result) {
        setUpState(State.SUCCESS);
        mContentAdapter.setData(result);
    }

    @Override
    public void onMoreLoaded(OnSellContent result) {

    }

    @Override
    public void onMoreLoadedError() {

    }

    @Override
    public void onMoreLoadedEmpty() {

    }

    @Override
    public void onError() {
        setUpState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }
}
