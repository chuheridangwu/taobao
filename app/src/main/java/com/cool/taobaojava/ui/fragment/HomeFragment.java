package com.cool.taobaojava.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.presenter.impl.HomePresenterImpl;
import com.cool.taobaojava.ui.adapter.HomePagerAdapter;
import com.cool.taobaojava.view.IHomeCallBack;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends BaseFragment  implements IHomeCallBack {

    private HomePresenterImpl mHomePresenter;
    private TabLayout mTableView;
    private ViewPager2 mPageView2;
    private HomePagerAdapter mHomePagerAdapter;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTableView = rootView.findViewById(R.id.home_indicator);
        mPageView2 = rootView.findViewById(R.id.home_view_page2);
        mHomePagerAdapter = new HomePagerAdapter(this);
        mPageView2.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
    }

    // 加载数据
    @Override
    protected void loadData() {
        mHomePresenter.getCategories();
    }

    // 创建Presenter
    @Override
    protected void initPresenter() {
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerViewCallback(this);
    }

    // 返回的数据
    @Override
    public void onCategoriesLoaded(Categories categories) {
        setUpState(State.SUCCESS);
        if (mHomePagerAdapter != null){
            mHomePagerAdapter.setListData(categories.getData());
        }
        new TabLayoutMediator(mTableView, mPageView2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Categories.DataBean data = categories.getData().get(position);
                tab.setText(data.getTitle());
            }
        }).attach();
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
    public void onRetryNetWork() {
        if (mHomePresenter != null){
            mHomePresenter.getCategories();
        }
    }

    // 取消回调注册
    @Override
    protected void release() {
        if (mHomePresenter!=null) {
            mHomePresenter.unregisterViewCallback(this);
        }
    }


}
