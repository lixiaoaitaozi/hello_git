package com.china.kuaiyou.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.china.kuaiyou.R;
import com.china.kuaiyou.bean.UploadBean;
import com.china.kuaiyou.mybase.LG;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class UploadAdapter extends RecyclerView.Adapter<UploadViewHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private List<UploadBean> list;
    private Map<String, Integer> map = new ArrayMap<>();

    public UploadAdapter(Context context, List<UploadBean> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        LG.i("uploadAdapter", "-----------" + list.size() + "----" + list.toString());
        for (UploadBean uploadBean : list) {
            map.put(uploadBean.getFileName(), 0);
        }
    }


    @Override
    public UploadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_upload_layout, parent, false);
        UploadViewHolder viewHolder = new UploadViewHolder(view);
        viewHolder.fileNameTV = view.findViewById(R.id.tv_upload_filename);
        viewHolder.progressTV = view.findViewById(R.id.tv_upload_progress);
        viewHolder.uploadProgressPB = view.findViewById(R.id.pb_upload_file);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UploadViewHolder holder, final int position) {
        UploadBean uploadBean = list.get(position);
        holder.fileNameTV.setText(uploadBean.getFileName());
        int progress = map.get(uploadBean.getFileName());
        if (progress == 101) {
            holder.uploadProgressPB.setProgress(100);
            holder.progressTV.setText("成功");
        } else if (progress == 102) {
            holder.progressTV.setText("失败");
        } else if (progress >= 0 && progress <= 100) {
            holder.progressTV.setText(progress + "%");
            holder.uploadProgressPB.setProgress(progress);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNowProgress(String uploadName, int progress) {
        if (!TextUtils.isEmpty(uploadName)) {
            map.put(uploadName, progress);
        }
    }

    public boolean checkUploadIsOver() {
        boolean isOver = false;
        for (UploadBean uploadBean : list) {
            int progress = map.get(uploadBean.getFileName());
            if (progress == 100 || progress == 101) {
                //说明当前的是有的
                isOver = true;
            } else {
                isOver = false;
                return isOver;
            }
        }
        return isOver;
    }

}
