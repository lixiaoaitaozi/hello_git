package com.china.kuaiyou.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class UploadViewHolder extends RecyclerView.ViewHolder {
    TextView fileNameTV, progressTV;
    ProgressBar uploadProgressPB;

    public UploadViewHolder(View itemView) {
        super(itemView);
    }
}
