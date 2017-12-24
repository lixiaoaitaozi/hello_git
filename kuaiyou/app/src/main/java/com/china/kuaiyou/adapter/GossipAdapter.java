package com.china.kuaiyou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.bean.GossipBean;


import java.util.List;

/**
 * @className :GossipAdapter
 * @purpose: 八卦的显示列表适配器
 * @author:
 * @data:2017/11/12 15:00
 */

public class GossipAdapter extends BaseAdapter {

    private List<GossipBean> list;
    private Context context;
    private LayoutInflater lf;

    public GossipAdapter(List<GossipBean> list, Context context) {
        this.list = list;
        this.context = context;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GossipBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = lf.inflate(R.layout.gossip_adapter_layout, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else
            holder = (Holder) convertView.getTag();
        GossipBean gb = getItem(position);
        holder.userNameV.setText(gb.getUserName() + gb.getContents() + gb.getSignObject());
        if (gb.getDataType() == 2) {
            holder.showPictureL.setVisibility(View.VISIBLE);
            setGridView(gb.getList(), holder.showPictureV);
            GossipPictureAdapter gp = new GossipPictureAdapter(gb.getList(), context);
            holder.showPictureV.setAdapter(gp);
        } else {
            holder.showPictureL.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class Holder {
        ImageView iconV;
        TextView userNameV;
        LinearLayout showPictureL;
        GridView showPictureV;

        Holder(View view) {
            iconV = (ImageView) view.findViewById(R.id.gossip_user_icon_image);
            userNameV = (TextView) view.findViewById(R.id.gossip_userNmae_content_text);
            showPictureL = (LinearLayout) view.findViewById(R.id.show_works_layout);
            showPictureV = (GridView) view.findViewById(R.id.picture_show_view);
        }
    }


    /**
     * 设置GridView的属性
     *
     * @param allImages GridView的数据对象
     */
    private void setGridView(List<Bitmap> allImages, GridView showImageV) {
        // item宽度
        int itemWidth = dip2px(50);
        // item之间的间隔
        int itemPaddingH = dip2px(1);
        int size = allImages.size();
        // 计算GridView宽度
        int gridviewWidth = size * (itemWidth + itemPaddingH);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        showImageV.setLayoutParams(params);
        showImageV.setColumnWidth(itemWidth);
        showImageV.setHorizontalSpacing(itemPaddingH);
        showImageV.setStretchMode(GridView.NO_STRETCH);
        showImageV.setNumColumns(size);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue dp值
     * @return px值
     */
    protected int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
