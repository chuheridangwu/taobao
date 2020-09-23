package com.cool.taobaojava.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cool.taobaojava.R;
import com.cool.taobaojava.model.domain.OnSellContent;
import com.cool.taobaojava.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class OnSellContentAdapter extends RecyclerView.Adapter<OnSellContentAdapter.InnerHolder> {

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public OnSellContentAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_sell_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnSellContentAdapter.InnerHolder holder, int position) {
        OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean dataBean = mData.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(OnSellContent result) {
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data = result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setMoreData(OnSellContent result) {
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data = result.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        int olderSize = mData.size();
        mData.addAll(data);
        notifyItemChanged(olderSize-1,mData.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTv;
        private TextView mOriginPriceTv;
        private TextView mSellPriceTv;
        private ImageView mCover;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.on_sell_title);
            mOriginPriceTv= itemView.findViewById(R.id.on_sell_origin_price_tv);
            mSellPriceTv = itemView.findViewById(R.id.on_sell_price_tv);
            mCover = itemView.findViewById(R.id.on_sell_cover);

        }

        public void setData(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean dataBean) {
            mTitleTv.setText(dataBean.getTitle());
            mOriginPriceTv.setText("￥" + dataBean.getZk_final_price());
            // 设置下划线
            mOriginPriceTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url(),200);
            Glide.with(mCover.getContext()).load(coverPath).into(mCover);

            // 打折信息
            int couponAmount = dataBean.getCoupon_amount();
            float originPriseFloat = Float.parseFloat(dataBean.getZk_final_price());
            float finalsPrice = originPriseFloat - couponAmount;
            mSellPriceTv.setText("劵后价"  + String.format("%.2f",finalsPrice));
        }
    }

}
