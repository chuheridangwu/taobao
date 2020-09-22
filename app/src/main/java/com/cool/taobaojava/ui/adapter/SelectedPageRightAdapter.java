package com.cool.taobaojava.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.R;
import com.cool.taobaojava.model.domain.SelectedContent;
import com.cool.taobaojava.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SelectedPageRightAdapter extends RecyclerView.Adapter<SelectedPageRightAdapter.InnerHolder> {

    private List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public SelectedPageRightAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_page_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedPageRightAdapter.InnerHolder holder, int position) {
        SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data = mData.get(position);
        holder.setData(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(SelectedContent content) {
        if (content.getCode() == Constants.SUCCESS_CODE  && content.getData().getTbk_uatm_favorites_item_get_response() != null) {
            List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> bean = content.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
            mData.clear();
            mData.addAll(bean);
            notifyDataSetChanged();
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        private  TextView mTitle;
        private  TextView mOriginal;
        private  ImageView mCover;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.selected_title);
            mCover = itemView.findViewById(R.id.selected_cover);
            mOriginal = itemView.findViewById(R.id.selected_original);
        }

        public void setData(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data) {
            mTitle.setText(data.getTitle());
            Glide.with(mCover).load(data.getPict_url()).into(mCover);
            mOriginal.setText("原价:"  + data.getZk_final_price());
        }
    }
}
