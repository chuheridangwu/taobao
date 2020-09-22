package com.cool.taobaojava.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseActivity;
import com.cool.taobaojava.model.domain.TicketResult;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;
import com.cool.taobaojava.ui.custom.LoadingView;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.ITicketPagerCallback;

public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private TicketPresenterImpl mTicketPresenter;
    private EditText mCodeView;
    private ImageView mIconView;
    private View mBack;
    private LoadingView mLoadingView;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initView() {
        mCodeView = findViewById(R.id.ticket_code);
        mIconView = findViewById(R.id.ticket_icon);
        mBack = findViewById(R.id.ticket_back);
        mLoadingView = findViewById(R.id.ticket_cover_loading);
    }

    @Override
    protected void initEvent() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresentManager.getInstance().getmTicketPresenter();
        mTicketPresenter.registerViewCallback(this);
    }

    @Override
    protected void release() {
        if (mTicketPresenter!=null) {
            mTicketPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onTicketLoaded(String cover, TicketResult result) {
        if (mLoadingView == null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        if (!cover.isEmpty()){
            Glide.with(this).load(cover).into(mIconView);
        }
        if (result != null && result.getData().getTbk_tpwd_create_response() != null){
            mCodeView.setText(result.getData().getTbk_tpwd_create_response().getData().getModel());
        }
    }

    @Override
    public void onError() {
        if (mLoadingView == null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoading() {
        if (mLoadingView == null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEmpty() {

    }
}
