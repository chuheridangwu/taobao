package com.cool.taobaojava.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseActivity;
import com.cool.taobaojava.model.domain.TicketResult;
import com.cool.taobaojava.presenter.impl.TicketPresenterImpl;
import com.cool.taobaojava.ui.custom.LoadingView;
import com.cool.taobaojava.utils.PresentManager;
import com.cool.taobaojava.utils.ToastUtils;
import com.cool.taobaojava.utils.UrlUtils;
import com.cool.taobaojava.view.ITicketPagerCallback;

public class TicketActivity extends BaseActivity implements ITicketPagerCallback {

    private TicketPresenterImpl mTicketPresenter;
    private EditText mCodeView;
    private ImageView mIconView;
    private View mBack;
    private LoadingView mLoadingView;
    private Button mBtn;
    private boolean mHasTabbaoApp;


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
        mBtn = findViewById(R.id.ticket_btn);
    }

    @Override
    protected void initEvent() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用粘贴板复制功能
                String ticketCode = mCodeView.getText().toString().trim();
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("sob_taobao_ticket_code",ticketCode);
                cm.setPrimaryClip(clipData);
                if (mHasTabbaoApp){
                    // 跳转到淘宝
                    Intent taobao = new Intent();
                    ComponentName componentName = new ComponentName("com.taobao.taobao","com.taobao.taobao.welcome.Welcome");
                    taobao.setComponent(componentName);
                    startActivity(taobao);

                }else {
                    ToastUtils.showToast("复制成功,粘贴分享到淘宝");
                }
            }
        });

    }

    @Override
    protected void initPresenter() {
        mTicketPresenter = PresentManager.getInstance().getmTicketPresenter();
        mTicketPresenter.registerViewCallback(this);

        // 判断是否有安装淘宝
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao",PackageManager.MATCH_UNINSTALLED_PACKAGES);
            mHasTabbaoApp = packageInfo != null;
        }catch (Exception e){
            e.printStackTrace();
            mHasTabbaoApp = false;
        }

        mBtn.setText(mHasTabbaoApp ? "打开淘宝领劵" : "复制淘宝口令");
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
