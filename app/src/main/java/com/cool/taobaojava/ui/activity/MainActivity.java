package com.cool.taobaojava.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cool.taobaojava.R;
import com.cool.taobaojava.ui.fragment.HomeFragment;
import com.cool.taobaojava.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        LogUtils.d(MainActivity.class,"首页");
                        break;
                    case R.id.selected:
                        LogUtils.i(MainActivity.class,"选中");
                        break;
                    case R.id.red_packet:
                        LogUtils.e(MainActivity.class,"红包");
                        break;
                    case R.id.search:
                        LogUtils.w(MainActivity.class,"搜索");
                        break;
                }

                return true;
            }
        });
    }

    private void initView() {
        mNavigationView = findViewById(R.id.main_navigation_bar);

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