package com.cool.taobaojava.ui.adapter;

import android.media.Image;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class LooperPagerAdapter extends RecyclerView.Adapter<LooperPagerAdapter.InnerHolder> {

    List<HomePagerContent.DataBean> dataList = new ArrayList<>();

    public int getDataSize(){
        return dataList.size();
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onBindViewHolder: " + parent.getMeasuredHeight());

        ImageView iv = new ImageView(parent.getContext());
        // 设置宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        // 设置拉伸
        iv.setScaleType(ImageView.ScaleType.CENTER);
        return new InnerHolder(iv);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        int realPosition = position % dataList.size();
        HomePagerContent.DataBean data = dataList.get(realPosition);
        ImageView iv = (ImageView)holder.itemView;
        Glide.with(iv).load(UrlUtils.getCoverPath(data.getPict_url(),180)).into(iv);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        dataList.clear();
        dataList.addAll(contents);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
