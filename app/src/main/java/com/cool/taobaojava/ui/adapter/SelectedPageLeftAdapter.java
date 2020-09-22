package com.cool.taobaojava.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cool.taobaojava.R;
import com.cool.taobaojava.model.domain.SelectedCategory;

import java.util.ArrayList;
import java.util.List;

public class SelectedPageLeftAdapter extends RecyclerView.Adapter<SelectedPageLeftAdapter.InnerHolder> {

    private List<SelectedCategory.DataBean> mData = new ArrayList<>();
    private int mCurrentSelectedPosition = 0;
    private onLeftItemClickListener mItemClickListener;

    @NonNull
    @Override
    public SelectedPageLeftAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_left,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedPageLeftAdapter.InnerHolder holder, int position) {
        TextView itemTv = holder.itemView.findViewById(R.id.left_category_tv);
        if (mCurrentSelectedPosition == position){
            itemTv.setBackgroundColor(itemTv.getResources().getColor(R.color.colorEEEEEE));
        }else {
            itemTv.setBackgroundColor(itemTv.getResources().getColor(R.color.white));
        }

        SelectedCategory.DataBean dataBean = mData.get(position);
        itemTv.setText(dataBean.getFavorites_title());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null && mCurrentSelectedPosition != position) {
                    mCurrentSelectedPosition = position;
                    mItemClickListener.onLeftItemClick(dataBean);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(SelectedCategory category){
        List<SelectedCategory.DataBean> data = category.getData();
        if (data!= null){
            this.mData.clear();
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnLeftItemClickListener(onLeftItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface onLeftItemClickListener{
        void onLeftItemClick(SelectedCategory.DataBean item);
    }
}
