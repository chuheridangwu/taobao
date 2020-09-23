package com.cool.taobaojava.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.SelectedCategory;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.presenter.impl.SelectedPagePresenterImpl;
import com.cool.taobaojava.ui.adapter.SelectedPageLeftAdapter;
import com.cool.taobaojava.ui.adapter.SelectedPageRightAdapter;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.view.ISelectedPageCallback;

public class SelectedFragment extends BaseFragment implements ISelectedPageCallback, SelectedPageLeftAdapter.onLeftItemClickListener {

    private SelectedPagePresenterImpl mSelectedPagePresenter;
    private SelectedCategory.DataBean mItem;
    private RecyclerView leftCategory;
    private SelectedPageLeftAdapter mLeftAdapter;
    private RecyclerView rightContentList;
    private SelectedPageRightAdapter mRightAdapter;
    private TextView mTitle;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_selected;
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_with_bar_layout,container,false);
    }

    @Override
    protected void initPresenter() {
        mSelectedPagePresenter = PresentManager.getInstance().getmSelectedPresenter();
        mSelectedPagePresenter.registerViewCallback(this);
        mSelectedPagePresenter.getCategories();
    }

    @Override
    protected void release() {
        if (mSelectedPagePresenter == null) {
            mSelectedPagePresenter.unregisterViewCallback(this);
        }
    }

    @Override
    protected void initListener() {
        mLeftAdapter.setOnLeftItemClickListener(this);
    }

    @Override
    protected void initView(View rootView) {
        leftCategory = rootView.findViewById(R.id.left_category_list);
        leftCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter = new SelectedPageLeftAdapter();
        leftCategory.setAdapter(mLeftAdapter);

        rightContentList = rootView.findViewById(R.id.right_content_list);
        rightContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightAdapter = new SelectedPageRightAdapter();
        rightContentList.setAdapter(mRightAdapter);
        // 添加间距
        rightContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
               int topAndBottom =  SizeUtils.dip2px(getContext(),4);
               int leftAndRight =  SizeUtils.dip2px(getContext(),6);

                outRect.top = topAndBottom;
               outRect.bottom = topAndBottom;
               outRect.left = leftAndRight;
               outRect.right = leftAndRight;
            }
        });

        mTitle = rootView.findViewById(R.id.bar_title_tv);
        mTitle.setText("精选宝贝");
    }

    @Override
    public void onCategoriesLoaded(SelectedCategory result) {
        setUpState(State.SUCCESS);

        mLeftAdapter.setData(result);

        if (result.getData() != null) {
            mItem = result.getData().get(0);
            mSelectedPagePresenter.getContentByCategory(mItem);
        }
    }

    @Override
    public void onContentLoad(SelectedContent content) {
        mRightAdapter.setData(content);
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

    @Override
    public void onLeftItemClick(SelectedCategory.DataBean item) {
        mItem = item;
        mSelectedPagePresenter.getContentByCategory(mItem);
    }
}
