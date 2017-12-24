package com.china.kuaiyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.VideoActivity;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.ToastUtil;
import com.china.kuaiyou.netbean.template.ResultTemplateBean;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netutil.LHCXUrl;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class UpdataVideoRVAdapter extends RecyclerView.Adapter<MainActivityModelRVAdapterHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> list;

    public UpdataVideoRVAdapter(Context context, List<String> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MainActivityModelRVAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_main_activity_show_model_rv_layout, null);
        MainActivityModelRVAdapterHolder viewHolder = new MainActivityModelRVAdapterHolder(view);
        viewHolder.modelIV = view.findViewById(R.id.iv_main_show_model_rv_adapter_model);
        viewHolder.userIconIV = view.findViewById(R.id.iv_main_show_model_rv_adapter_user_icon);
        viewHolder.distanceTV = view.findViewById(R.id.tv_main_show_model_rv_adapter_distance);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityModelRVAdapterHolder holder, final int position) {
        final String path = list.get(position);
        holder.modelIV.getLayoutParams().width = 80;
        holder.modelIV.getLayoutParams().height = 160;
        MyGlide.getInstance().setBitMap(context, holder.modelIV, path);
        holder.distanceTV.setVisibility(View.GONE);
        holder.userIconIV.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }


}
