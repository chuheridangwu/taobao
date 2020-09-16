package com.cool.taobaojava.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends RecyclerView.Adapter<HomePagerAdapter.ViewHolder> {

    private  List<Categories.DataBean> dataList = new ArrayList<>();

    public void setDataList(List<Categories.DataBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomePagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_pager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
