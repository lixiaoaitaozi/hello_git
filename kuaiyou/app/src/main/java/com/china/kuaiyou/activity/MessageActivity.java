package com.china.kuaiyou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.huanxin.HXObserver;
import com.china.kuaiyou.huanxin.HXSubscriptionSubject;
import com.china.kuaiyou.mybase.BaseCompatActivity;
import com.china.kuaiyou.mybase.BaseMainActivity;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class MessageActivity extends BaseCompatActivity implements HXObserver {

    private TextView messageShowTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myWindowSet.setFlatStyleColoredBars();

        setContentView(R.layout.activity_message_layout);
        initTitle();
        HXSubscriptionSubject.getInstence().attach(this);
        initView();
    }

    private void initTitle() {
        ImageView backIV = (ImageView) findViewById(R.id.iv_title_back);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        messageShowTV= (TextView) findViewById(R.id.tv_message_show);
        messageShowTV.setText("当前有0条消息");
    }
    int count=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            messageShowTV.append("\n"+msg.obj);
        }
    };
    @Override
    public void update(String message, String userId) {
        count++;
        Message msg=new Message();
        msg.what=0;
        msg.obj="当前有"+count+"条消息>>"+userId;
        handler.sendMessage(msg);
    }
}
