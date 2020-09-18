package com.cool.taobaojava.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cool.taobaojava.model.domain.Categories;
import com.cool.taobaojava.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentStateAdapter {

    List<Categories.DataBean> listData = new ArrayList<>();

    public void setListData(List<Categories.DataBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public HomePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Categories.DataBean dataBean = listData.get(position);
        HomePagerFragment fragment = new HomePagerFragment().newInstance(dataBean);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
