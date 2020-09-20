package com.cool.taobaojava.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.presenter.ICategoryPagerPresenter;
import com.cool.taobaojava.presenter.impl.CategoryPagePresenterImpl;
import com.cool.taobaojava.ui.adapter.HomePageContentAdapter;
import com.cool.taobaojava.ui.adapter.LooperPagerAdapter;
import com.cool.taobaojava.utils.Constants;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.view.ICategoryPagerCallback;

import java.util.List;

public class HomePagerFragment extends BaseFragment  implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mCategoryPagerPresenter;
    private int materialId;
    private LooperPagerAdapter mLoopAdapter;
    private ViewGroup looperPointContainer;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    RecyclerView mContentList;
    private HomePageContentAdapter mContentAdapter;
    private ViewPager2 mLoopView;
    private TextView mCategoryTitleTv;
    @Override
    protected void initView(View rootView) {
        mContentList = rootView.findViewById(R.id.home_page_content_list);
        // 设置布局样式
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        // 设置item上下边距
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 5;
            }
        });
        // 设置适配器
        mContentAdapter = new HomePageContentAdapter();
        mContentList.setAdapter(mContentAdapter);

        // 设置轮播图
        mLoopView = rootView.findViewById(R.id.loop_pager);
        mLoopAdapter = new LooperPagerAdapter();
        mLoopView.setAdapter(mLoopAdapter);

        // 分类标题
        mCategoryTitleTv = rootView.findViewById(R.id.home_pager_title);
        // 轮播图指示器
        looperPointContainer = rootView.findViewById(R.id.looper_point_container);
    }

    @Override
    protected void initPresenter() {
        mCategoryPagerPresenter = CategoryPagePresenterImpl.getsInstance();
        mCategoryPagerPresenter.registerViewCallback(this);
    }

    @Override
    protected void initListener() {
      mLoopView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              super.onPageScrolled(position, positionOffset, positionOffsetPixels);
          }

          @Override
          public void onPageSelected(int position) {
              super.onPageSelected(position);
              if (mLoopAdapter.getDataSize()==0) {
                  return;
              }
              // 切换指示器
              int targetPosition = position % mLoopAdapter.getDataSize();
              updateLooperIndicator(targetPosition);
          }

          @Override
          public void onPageScrollStateChanged(int state) {
              super.onPageScrollStateChanged(state);
          }
      });
    }

    private void updateLooperIndicator(int position) {
        for (int i = 0; i < mLoopView.getChildCount(); i++){
            View view = mLoopView.getChildAt(i);
            if (i == position){
                view.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else {
                view.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        Log.d("TAG", "loadData: " + title + materialId);
        if (mCategoryPagerPresenter!=null) {
            mCategoryPagerPresenter.getContentByCategoryId(materialId);
        }
        mCategoryTitleTv.setText(title);
    }

    @Override
    protected void release() {
        if (mCategoryPagerPresenter!=null) {
            mCategoryPagerPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {
        setUpState(State.SUCCESS);
        mContentAdapter.setData(contents);
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onError(){
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        Log.d("TAG", "onLooperListLoaded:  " + contents.size());
        mLoopAdapter.setData(contents);
        looperPointContainer.removeAllViews();

        // 设置初始值
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetCenterPosition = (Integer.MAX_VALUE / 2) - dx;
        mLoopView.setCurrentItem(targetCenterPosition);
        Log.d("TAG", "onLooperListLoaded: " + targetCenterPosition + "url" + contents.get(0).getPict_url());

        for (int i = 0; i < contents.size(); i++){
            View view = new View(getContext());
            int size = SizeUtils.dip2px(getContext(),8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size,size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(),5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(),5);
            view.setLayoutParams(layoutParams);
            if (i == 0){
                view.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            }else {
                view.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
            looperPointContainer.addView(view);
        }
    }

    @Override
    public int getCategoryId() {
        return materialId;
    }
}
