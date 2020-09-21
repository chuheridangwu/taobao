package com.cool.taobaojava.ui.fragment;

import android.view.View;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;

public class RedPacketFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.LOADING);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initPresenter() {

    }
}
