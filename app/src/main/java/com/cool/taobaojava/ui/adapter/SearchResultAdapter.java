package com.cool.taobaojava.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cool.taobaojava.R;
import com.cool.taobaojava.model.domain.SearchResult;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.InnerHolder> {

    private List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_goods_content,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(SearchResult result) {
        List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> list = result.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data();
        mData.clear();
        mData = list;
        notifyDataSetChanged();
    }

    public class InnerHolder  extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
