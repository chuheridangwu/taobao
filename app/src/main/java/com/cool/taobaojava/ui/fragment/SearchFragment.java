package com.cool.taobaojava.ui.fragment;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;
import com.cool.taobaojava.model.domain.Histories;
import com.cool.taobaojava.model.domain.IBaseInfo;
import com.cool.taobaojava.model.domain.SearchRecommend;
import com.cool.taobaojava.model.domain.SearchResult;
import com.cool.taobaojava.presenter.impl.SearchPresenter;
import com.cool.taobaojava.ui.adapter.HomePageContentAdapter;
import com.cool.taobaojava.ui.adapter.SearchResultAdapter;
import com.cool.taobaojava.ui.custom.TextFlowLayout;
import com.cool.taobaojava.utils.KeyboardUtil;
import com.cool.taobaojava.utils.LogUtils;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.SizeUtils;
import com.cool.taobaojava.utils.TicketUtil;
import com.cool.taobaojava.utils.ToastUtils;
import com.cool.taobaojava.view.ISearchViewCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends BaseFragment implements ISearchViewCallback {

    private SearchPresenter mSearchPresenter;
    private TextFlowLayout mTextFlowLayout;
    private TextFlowLayout mRecommendLayout;
    private View mHistoryView;
    private View mRecommendView;
    private ImageView mHistoryDelete;
    private RecyclerView mSearchList;
    private HomePageContentAdapter mSearchAdapter;
    private SmartRefreshLayout mRefresh;
    private EditText mSearchEdit;
    private ImageView mSearchRemove;
    private TextView mSearchCancel;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_layout,container,false);
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
        mTextFlowLayout = rootView.findViewById(R.id.search_history_view);
        mRecommendLayout = rootView.findViewById(R.id.search_recommend_view);
        mRecommendView = rootView.findViewById(R.id.search_recommend_container);
        mHistoryView = rootView.findViewById(R.id.search_history_container);
        mHistoryDelete = rootView.findViewById(R.id.search_history_delete);

        mSearchList = rootView.findViewById(R.id.search_result_list);
        mSearchList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(),5);
                outRect.bottom = SizeUtils.dip2px(getContext(),8);
            }
        });

        mSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchAdapter = new HomePageContentAdapter();
        mSearchList.setAdapter(mSearchAdapter);

        mRefresh = rootView.findViewById(R.id.search_refresh);

        mSearchEdit = rootView.findViewById(R.id.search_edit_text);
        mSearchRemove = rootView.findViewById(R.id.search_remove);
        mSearchCancel = rootView.findViewById(R.id.search_cancel);
    }

    @Override
    protected void release() {
        mSearchPresenter.unregisterViewCallback(this);
    }

    @Override
    protected void initPresenter() {
        mSearchPresenter = PresentManager.getInstance().getmSearchPresenter();
        mSearchPresenter.registerViewCallback(this);
        mSearchPresenter.gerRecommendWords();
        mSearchPresenter.getHistories();
    }

    @Override
    protected void initListener() {
        mHistoryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchPresenter.delHistory();
            }
        });

        // 上拉加载更多
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mSearchPresenter!=null) {
                    mSearchPresenter.loaderMore();
                }
            }
        });

        // 跳转商品优惠页面
        mSearchAdapter.setOnListItemClickListener(new HomePageContentAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(IBaseInfo dataBean) {
                TicketUtil.toTicketPage(getContext(),dataBean);
            }
        });

        // 点击确定按钮
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || mSearchPresenter!= null){
                    String keyword = textView.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        return false;
                    }
                    mSearchPresenter.doSearch(keyword);

                }
                return false;
            }
        });

        // 监听输入框变化
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mSearchRemove.setVisibility(hasInput(true) ? View.VISIBLE : View.GONE );
                mSearchCancel.setText(hasInput(false)  ? "搜索" : "取消");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 清除输入框内容
        mSearchRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchEdit.setText("");
                // 回到历史和推荐界面
                switch2HistoryPage();
            }
        });

        // 点击搜索按钮发起搜索
        mSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasInput(false)){
                    if (mSearchPresenter != null) {
                        mSearchPresenter.doSearch(mSearchEdit.getText().toString().trim());
                        // 隐藏键盘
                        KeyboardUtil.hide(getContext(),view);
                    }
                }else {

                }
            }
        });
    }

    // 切换到历史和推荐界面
    private void switch2HistoryPage(){
        if (mTextFlowLayout.getContentSize()!=0) {
            mHistoryView.setVisibility(View.VISIBLE);
        }else {
            mHistoryView.setVisibility(View.GONE);
        }

        if (mRecommendLayout.getContentSize()!=0) {
            mRecommendView.setVisibility(View.VISIBLE);
        }else {
            mRecommendView.setVisibility(View.GONE);
        }

        // 内容隐藏
        mSearchList.setVisibility(View.GONE);
    }

    private boolean hasInput(boolean containSpace){
        if (containSpace){
            return mSearchEdit.getText().toString().length() > 0;
        }else {
            return mSearchEdit.getText().toString().trim().length() > 0;
        }
    }

    @Override
    public void onHistoriesLoaded(Histories histories) {
        LogUtils.d(this,"保存的关键字" + histories);
        if (histories == null || histories.getHistories().size() == 0){
            mHistoryView.setVisibility(View.GONE);
        }else {
            mHistoryView.setVisibility(View.VISIBLE);
            mTextFlowLayout.setTextList(histories.getHistories());
        }
    }

    @Override
    public void onHistoriesDeleted() {
        if (mSearchPresenter != null) {
            mSearchPresenter.getHistories();
        }
    }

    @Override
    public void onSearchSuccess(SearchResult result) {
        setUpState(State.SUCCESS);

        mRecommendView.setVisibility(View.GONE);
        mHistoryView.setVisibility(View.GONE);

        mSearchList.setVisibility(View.VISIBLE);

        try {
            mSearchAdapter.setData(result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onMoreLoaded(SearchResult result) {
        mRefresh.finishLoadMore();
        mSearchAdapter.addData(result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data());

    }

    @Override
    public void onMoreLoadedError() {
        mRefresh.finishLoadMore();

    }

    @Override
    public void onMoreLoadedEmpty() {
        mRefresh.finishLoadMore();
        ToastUtils.showToast("啊哈，没有更多数据了");

    }

    @Override
    public void onRecommendWordsLoaded(List<SearchRecommend.DataBean> recommendWords) {
        List<String> recommendKeywords = new ArrayList<>();
        for (SearchRecommend.DataBean item : recommendWords) {
            recommendKeywords.add(item.getKeyword());
        }
        if (recommendKeywords.size() == 0){
            mRecommendView.setVisibility(View.GONE);
        }else {
            mRecommendView.setVisibility(View.VISIBLE);
            mRecommendLayout.setTextList(recommendKeywords);
        }
    }

    @Override
    public void onError() {
        setUpState(State.ERROR);
        ToastUtils.showToast("啊哈，搜索出错了");
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
        if (mSearchPresenter != null) {
            mSearchPresenter.research();
        }
    }
}
