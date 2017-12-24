package com.china.kuaiyou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.china.kuaiyou.R;

import java.util.List;

/**
 * @className :GossipPictureAdapter
 * @purpose: 图片显示适配器
 * @author:
 * @data:2017/11/12 15:28
 */

public class GossipPictureAdapter extends BaseAdapter {

    private List<Bitmap> list;
    private Context context;
    private LayoutInflater lf;

    public GossipPictureAdapter(List<Bitmap> list, Context context) {
        this.list = list;
        this.context = context;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = lf.inflate(R.layout.gossip_picturer_adapter_layout, null);
        ImageView worksV = (ImageView) convertView.findViewById(R.id.works_image);
        worksV.setImageBitmap(getItem(position));
        return convertView;
    }
}
