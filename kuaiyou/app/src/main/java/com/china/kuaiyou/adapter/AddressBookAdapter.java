package com.china.kuaiyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.bean.AddressBookBean;


import java.util.List;

/**
 * @className :AddressBookAdapter
 * @purpose: 通讯录适配器
 * @author:
 * @data:2017/11/7 22:34
 */

public class AddressBookAdapter extends BaseAdapter {

    private List<AddressBookBean> list;
    private Context context;
    private LayoutInflater lf;
    private ButtonListener buttonListener;//按钮监听回调接口

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public AddressBookAdapter(List<AddressBookBean> list, Context context) {
        this.list = list;
        this.context = context;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AddressBookBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Hodle hohle = null;
        if (convertView == null) {
            convertView = lf.inflate(R.layout.address_book_show_adapter_layout, null);
            hohle = new Hodle(convertView);
            convertView.setTag(hohle);
        } else
            hohle = (Hodle) convertView.getTag();
        hohle.iconImage.setImageResource(R.mipmap.the_drawer);
        hohle.userName.setText(getItem(position).getUserName());
        hohle.sourceV.setText(getItem(position).getSource());
        hohle.attentionV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.buttonClick(v, position);
            }
        });
        return convertView;
    }


    class Hodle {
        ImageView iconImage;//头像
        TextView userName;//用户名
        TextView sourceV;//来源
        Button attentionV;//关注按钮

        Hodle(View view) {
            iconImage = (ImageView) view.findViewById(R.id.icon_image);
            userName = (TextView) view.findViewById(R.id.name);
            sourceV = (TextView) view.findViewById(R.id.lastmsg);
            attentionV = (Button) view.findViewById(R.id.attention_view);
        }

    }

    public interface ButtonListener {
        void buttonClick(View view, int position);
    }

}
