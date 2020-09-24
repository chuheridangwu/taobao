package com.cool.taobaojava.ui.activity;

import com.cool.taobaojava.R;
import com.cool.taobaojava.base.BaseActivity;
import com.cool.taobaojava.ui.custom.TextFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        List<String> textList = new ArrayList<>();
        textList.add("电脑");
        textList.add("键盘");
        textList.add("家用电器");
        TextFlowLayout view = findViewById(R.id.text_flow_view);
        view.setTextList(textList);
    }
}
