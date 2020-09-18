package com.cool.taobaojava.ui.fragment;

import android.view.View;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseFragment;

public class HomePagerFragment extends BaseFragment {
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
}
