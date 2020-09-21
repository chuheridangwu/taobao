package com.cool.taobaojava.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cool.taobaojava.R;
import com.cool.taobaojava.ui.fragment.HomeFragment;
import com.cool.taobaojava.ui.fragment.RedPacketFragment;
import com.cool.taobaojava.ui.fragment.SearchFragment;
import com.cool.taobaojava.ui.fragment.SelectedFragment;
import com.cool.taobaojava.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.cool.taobaojava.R.id.main_page_container;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SelectedFragment mSelectedFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFm;

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
                        switchFragment(mHomeFragment);
                        break;
                    case R.id.selected:
                        LogUtils.i(MainActivity.class,"选中");
                        switchFragment(mSelectedFragment);
                        break;
                    case R.id.red_packet:
                        LogUtils.e(MainActivity.class,"红包");
                        switchFragment(mRedPacketFragment);
                        break;
                    case R.id.search:
                        LogUtils.w(MainActivity.class,"搜索");
                        switchFragment(mSearchFragment);
                        break;
                }

                return true;
            }
        });
    }

    private void initView() {
        mNavigationView = findViewById(R.id.main_navigation_bar);

        mHomeFragment = new HomeFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSelectedFragment = new SelectedFragment();
        mSearchFragment = new SearchFragment();
        mFm = getSupportFragmentManager();

        switchFragment(mHomeFragment);
    }

    // 切换Fragment
    private Fragment lastFragment;
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = mFm.beginTransaction();

        if (fragment.isAdded()){
            transaction.show(fragment);
        }else {
            transaction.add(main_page_container,fragment);
        }
        if (lastFragment != null){
            transaction.hide(lastFragment);
        }
        lastFragment = fragment;

        transaction.commit();
    }
}