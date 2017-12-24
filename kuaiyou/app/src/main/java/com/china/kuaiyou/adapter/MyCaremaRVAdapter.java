package com.china.kuaiyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.kuaiyou.R;
import com.china.kuaiyou.activity.VideoActivity;
import com.china.kuaiyou.fileutil.FileInfo;
import com.china.kuaiyou.glide.MyGlide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class MyCaremaRVAdapter extends RecyclerView.Adapter<MyCaremaRVAdapterHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FileInfo> fileInfos;
    private int width;
    private DataListen dataListen;

    public MyCaremaRVAdapter(Context context, ArrayList<FileInfo> fileInfos, int width, DataListen dataListen) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.fileInfos = fileInfos;
        this.width = width;
        this.dataListen = dataListen;
    }

    @Override
    public MyCaremaRVAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_my_carema_rv_layout, null);
        MyCaremaRVAdapterHolder viewHolder = new MyCaremaRVAdapterHolder(view);
        viewHolder.modelIV = view.findViewById(R.id.iv_main_show_model_rv_adapter_model);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyCaremaRVAdapterHolder holder, final int position) {
        final FileInfo fileInfo = fileInfos.get(position);
        holder.modelIV.getLayoutParams().width = width;
        holder.modelIV.getLayoutParams().height = width;
        MyGlide.getInstance().setBitMap(context, holder.modelIV, fileInfo.getPath());
        holder.modelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataListen.getUrl(fileInfo.getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileInfos.size();
    }

    public interface DataListen {
        public void getUrl(String url);
    }

}
