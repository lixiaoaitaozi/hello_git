package com.china.kuaiyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.VideoActivity;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.ToastUtil;
import com.china.kuaiyou.netbean.template.ResultTemplateBean;
import com.china.kuaiyou.netbean.template.TemplateBean;
import com.china.kuaiyou.netutil.LHCXUrl;
import com.china.kuaiyou.util.RandomUtil;
import com.china.kuaiyou.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class MainActivityModelRVAdapter extends RecyclerView.Adapter<MainActivityModelRVAdapterHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ResultTemplateBean resultTemplateBean;
    private int width;
    private int type;

    public MainActivityModelRVAdapter(Context context, ResultTemplateBean resultTemplateBean, int width,int type) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.resultTemplateBean = resultTemplateBean;
        this.width = width;
        this.type=type;
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
        final TemplateBean templateBean = resultTemplateBean.getList().get(position);
        holder.modelIV.getLayoutParams().width = width;
        holder.modelIV.getLayoutParams().height = width/2+(RandomUtil.getInstance().getRandomInt(5)*80);
        MyGlide.getInstance().setBitMap(context, holder.modelIV, LHCXUrl.IMEGA_HEAD + templateBean.getImageUrl());
        MyGlide.getInstance().setRoundedBitMap(context,holder.userIconIV,R.drawable.now_user_icon);
        holder.modelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("tb", LHCXUrl.IMEGA_HEAD + templateBean.getDownUrl());
                context.startActivity(intent);
            }
        });
        switch (type){
            case 1:
                //发现
                holder.distanceTV.setText(TimeUtil.getDate());
                break;
            case 2:
                //同城
                holder.distanceTV.setText(RandomUtil.getInstance().getRandomInt(10)+"km");
                break;
            case 3:
                //关注
                holder.distanceTV.setText(RandomUtil.getInstance().getRandomInt(1000)+"人");
                break;
        }
    }

    @Override
    public int getItemCount() {

        return resultTemplateBean.getList().size();
    }


}
