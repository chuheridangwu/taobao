package com.cool.taobaojava.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.OnSellContent;
import com.cool.taobaojava.presenter.impl.OnSellPagePresenterImpl;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;
import com.cool.taobaojava.ui.activity.TicketActivity;
import com.cool.taobaojava.ui.adapter.OnSellContentAdapter;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.utils.TicketUtil;
import com.cool.taobaojava.utils.ToastUtils;
import com.cool.taobaojava.view.IOnSellPageCallback;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

public class OnSellFragment extends BaseFragment implements IOnSellPageCallback, OnSellContentAdapter.OnSellPageItemClickListener {

    private OnSellPagePresenterImpl mOnSellPresenter;
    private RecyclerView mContentList;
    private OnSellContentAdapter mContentAdapter;
    private static final int DEFAULT_SPAN_COUNT = 2;
    private SmartRefreshLayout mRefresh;
    private TextView mTitle;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_on_sell;
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_with_bar_layout,container,false);
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

        mRefresh = rootView.findViewById(R.id.on_sell_refresh);
        // 设置刷新
        mRefresh.setRefreshFooter(new ClassicsFooter(getContext()));

        mTitle = rootView.findViewById(R.id.bar_title_tv);
        mTitle.setText("特惠精选");
    }

    @Override
    protected void initListener() {
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mOnSellPresenter.loaderMore();
            }
        });

        mContentAdapter.setOnSellPageItemClickListener(this);
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
        mRefresh.setEnableLoadMore(true);
    }

    @Override
    public void onMoreLoaded(OnSellContent result) {
        mContentAdapter.setMoreData(result);
        mRefresh.finishLoadMore();
        int size = result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
        ToastUtils.showToast("加载了" + size + "条数据");

    }

    @Override
    public void onMoreLoadedError() {
        mRefresh.finishLoadMore();
        ToastUtils.showToast("加载数据出错了....");

    }

    @Override
    public void onMoreLoadedEmpty() {
        mRefresh.finishLoadMore();
        ToastUtils.showToast("没有更多数据了");
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
    public void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean dataBean) {

        TicketUtil.toTicketPage(getContext(), dataBean);
    }
}
