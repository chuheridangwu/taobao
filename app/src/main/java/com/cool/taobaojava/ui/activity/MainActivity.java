package com.cool.taobaojava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cool.taobaojava.R;
import com.cool.taobaojava.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // 创建fragment
        HomeFragment home = new HomeFragment();
        // 获取fragment管理器
        FragmentManager manager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction =  manager.beginTransaction();
        // 添加 或 替换 fragment
        transaction.add(R.id.main_page_container,home);
        // 提交事务
        transaction.commit();
    }
}