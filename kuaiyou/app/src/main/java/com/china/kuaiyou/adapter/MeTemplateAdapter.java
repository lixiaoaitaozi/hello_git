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
import com.china.kuaiyou.fileutil.MyFileSearch;
import com.china.kuaiyou.glide.MyGlide;
import com.china.kuaiyou.mybase.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class MeTemplateAdapter extends RecyclerView.Adapter<MeTemPlateViewHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FileInfo> fileInfos;
    private String fileType;
    private MeTemplateAdapter.MyOnLongClick myOnLongClick;

    public MeTemplateAdapter(Context context, ArrayList<FileInfo> fileInfos, MyOnLongClick myOnLongClick) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.fileInfos = fileInfos;
        this.myOnLongClick = myOnLongClick;

    }

    @Override
    public MeTemPlateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_me_template_item_layout, parent, false);
        MeTemPlateViewHolder viewHolder = new MeTemPlateViewHolder(view);
        viewHolder.modelIV = view.findViewById(R.id.iv_me_template_item);
        viewHolder.modelTV = view.findViewById(R.id.tv_me_template_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MeTemPlateViewHolder holder, final int position) {
        final FileInfo fileInfo = fileInfos.get(position);

        final int fileType = MyFileSearch.fileType(fileInfo.getPath());
        switch (fileType) {
            case 0:
                MyGlide.getInstance().setBitMap(context, holder.modelIV, fileInfo.getPath());
                break;
            case 3:
            case 4:
                MyGlide.getInstance().setBitMap(context, holder.modelIV, fileInfo.getPath());
                break;
            case 6:

                break;
            default:
                break;
        }
        holder.modelTV.setText(fileInfo.getDisplayName());
        holder.modelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (fileType) {
                    case 0:
                        intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("tb", fileInfo.getPath());
                        context.startActivity(intent);
                        break;
                    case 3:
                    case 4:
//                    4    intent = new Intent(context, ImageActivity.class);
//                        intent.putExtra("tb", fileInfo.getPath());
//                        context.startActivity(intent);
                        break;
                    case 6:
                        break;
                    default:
                        ToastUtil.showToast("文件不符合规则非标准图片");
                        break;
                }
            }
        });

        holder.modelIV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (myOnLongClick != null)
                    myOnLongClick.myOnLongClickDataListen(fileInfo);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileInfos.size();
    }

    public interface MyOnLongClick {
        public void myOnLongClickDataListen(FileInfo fileInfo);
    }

}
