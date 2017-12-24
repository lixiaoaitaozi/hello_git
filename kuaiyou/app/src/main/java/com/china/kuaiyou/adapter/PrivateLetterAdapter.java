package com.china.kuaiyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.bean.PrivatesLetterBean;

import java.util.List;

/**
 * @className :PrivateLetterAdapter
 * @purpose: 私信界面显示适配器
 * @author:
 * @data:2017/11/12 16:27
 */

public class PrivateLetterAdapter extends BaseAdapter {

    private List<PrivatesLetterBean> list;
    private Context context;
    private LayoutInflater lf;

    public PrivateLetterAdapter(List<PrivatesLetterBean> list, Context context) {
        this.list = list;
        this.context = context;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PrivatesLetterBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = lf.inflate(R.layout.privates_letter_adapter_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        PrivatesLetterBean ob = getItem(position);
        viewHolder.nameV.setText(ob.getLetterName());
        viewHolder.contentsV.setText(ob.getContents());
        viewHolder.timesV.setText(ob.getDataTimes());
        return convertView;
    }

    class ViewHolder {
        ImageView iconV;
        TextView nameV;
        TextView contentsV;
        TextView timesV;

        ViewHolder(View view) {
            iconV = (ImageView) view.findViewById(R.id.icon_image);
            nameV = (TextView) view.findViewById(R.id.lettes_name_view);
            contentsV = (TextView) view.findViewById(R.id.contents_lettes_view);
            timesV = (TextView) view.findViewById(R.id.letter_times_view);
        }
    }
}
