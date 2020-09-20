package com.cool.taobaojava.ui.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.R;
import com.cool.taobaojava.model.domain.HomePagerContent;
import com.cool.taobaojava.utils.UrlUtils;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {

    List<HomePagerContent.DataBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public HomePageContentAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content,parent,false);
        return  new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageContentAdapter.InnerHolder holder, int position) {
        HomePagerContent.DataBean dataBean = mData.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }

    public void addData(List<HomePagerContent.DataBean> contents) {
        int olderSize = mData.size();
        mData.addAll(contents);
        notifyItemRangeChanged(olderSize,mData.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        ImageView good_img;
        TextView good_title;
        TextView good_off_price;
        TextView good_result_price;
        TextView good_final_price;
        TextView good_buy_count;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            good_img = itemView.findViewById(R.id.goods_cover);
            good_title = itemView.findViewById(R.id.goods_title);
            good_off_price = itemView.findViewById(R.id.goods_off_price);
            good_result_price = itemView.findViewById(R.id.goods_result_price);
            good_final_price = itemView.findViewById(R.id.goods_final_price);
            good_buy_count = itemView.findViewById(R.id.goods_buy_count);
        }

        public void setData(HomePagerContent.DataBean dataBean) {
            Log.d("TAG", "setData: " + dataBean.getPict_url());
            Glide.with(good_img).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(good_img);
            good_title.setText(dataBean.getShop_title());
            good_off_price.setText(String.format(itemView.getContext().getString(R.string.text_goods_off_price),dataBean.getCoupon_amount()));
            String finalPrice = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            float resultPrise = Float.parseFloat(finalPrice) - couponAmount;
            good_result_price.setText(String.format("%.2f",resultPrise));
            // 文字中划线
            good_final_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            good_final_price.setText(String.format(itemView.getContext().getString(R.string.text_goods_original_price),finalPrice));
            good_buy_count.setText(String.format(itemView.getContext().getString(R.string.text_goods_sell_count),dataBean.getVolume()));
        }
    }
}
