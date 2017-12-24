package com.china.kuaiyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.VideoActivity;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.netbean.template.ResultTemplateBean;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netutil.LHCXUrl;
import com.china.kuaiyou.util.RandomUtil;
import com.china.kuaiyou.util.TimeUtil;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class MainActivityRVAdapter extends RecyclerView.Adapter<MainActivityRVAdapterHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ResultTemplateBean beans_1;
    private ResultTemplateBean beans_2;
    private int width;
    private int type;

    public MainActivityRVAdapter(Context context, ResultTemplateBean resultTemplateBean, int width, int type) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        beans_1=new ResultTemplateBean();
        beans_2=new ResultTemplateBean();
        for(int i=0;i<resultTemplateBean.getList().size();i++){
            if(i%2==0){
                beans_1.add(resultTemplateBean.getList().get(i));
            }else{
                beans_2.add(resultTemplateBean.getList().get(i));
            }
        }
        this.width = width;
        this.type=type;
    }

    @Override
    public MainActivityRVAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_main_activity_rv, null);
        MainActivityRVAdapterHolder viewHolder = new MainActivityRVAdapterHolder(view);
        viewHolder.rv = view.findViewById(R.id.rv);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityRVAdapterHolder holder, final int position) {
        ResultTemplateBean resultTemplateBean=null;
        if(position==0){
            resultTemplateBean=beans_1;
        }else{
            resultTemplateBean=beans_2;
        }
        MainActivityModelRVAdapter adapter_1=new MainActivityModelRVAdapter(context,resultTemplateBean,width,type);
        LinearLayoutManager linearLayoutManager_1=new LinearLayoutManager(context);
        linearLayoutManager_1.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rv.setLayoutManager(linearLayoutManager_1);
        holder.rv.setItemAnimator(new DefaultItemAnimator());
        holder.rv.setAdapter(adapter_1);



    }

    @Override
    public int getItemCount() {

        return 2;
    }


}
