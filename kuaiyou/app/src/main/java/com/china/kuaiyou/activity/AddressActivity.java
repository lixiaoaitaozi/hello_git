package com.china.kuaiyou.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.china.kuaiyou.R;
import com.china.kuaiyou.adapter.AddressBookAdapter;
import com.china.kuaiyou.bean.AddressBean;
import com.china.kuaiyou.bean.AddressBookBean;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @className: AddressActivity
 * @purpose: 本地通讯录界面
 * @athor :
 * @date:2017/11/11 11:53
 */
@ContentView(R.layout.activity_address)
public class AddressActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AddressBookAdapter.ButtonListener {

    @ViewInject(R.id.iv_title_back)
    private ImageView blockV;

    @ViewInject(R.id.tv_title)
    private TextView titleV;

    @ViewInject(R.id.look_phone_find_layout)
    private LinearLayout lookFindLayout;

    @ViewInject(R.id.show_address_list_view)
    private ListView showAddressV;
    private AddressBookAdapter abookAdapter;//通讯录适配器
    private List<AddressBookBean> list;//数据源

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    @Override
    protected void initView() {
        titleV.setText("手机通讯录");
    }

    @Override
    protected void initObject() {
        getData();
        abookAdapter = new AddressBookAdapter(list, this);
        showAddressV.setAdapter(abookAdapter);
        abookAdapter.setButtonListener(this);
    }

    @OnClick({R.id.iv_title_back, R.id.look_phone_find_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.look_phone_find_layout://绑定手机界面
                break;
        }
    }

    /**
     * 获取数据源
     */
    private void getData() {
        list = new ArrayList<>();
        AddressBookBean ab = null;
        for (AddressBean abc : getPhoneContacts()) {
            ab = new AddressBookBean();
            ab.setUserID((int) abc.getUserID());
            ab.setUserName(abc.getUserName());
            ab.setSource(abc.getPhone());
            ab.setIsAttention(0);
            list.add(ab);
        }
    }

    /**
     * @return 得到手机通讯录联系人信息
     */
    private List<AddressBean> getPhoneContacts() {

        List<AddressBean> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            AddressBean ab = null;
            while (phoneCursor.moveToNext()) {
                ab = new AddressBean();
                //得到手机号码
                String phoneNumber = phoneCursor.getString(1);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //得到联系人名称
                String contactName = phoneCursor.getString(0);
                //得到联系人ID
                Long contactid = phoneCursor.getLong(2);
                //得到联系人头像ID
                Long photoid = phoneCursor.getLong(3);
                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;
                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                }
                ab.setPhone(phoneNumber);
                ab.setUserName(contactName);
                ab.setUserID(contactid);
                ab.setIconBitmap(contactPhoto);
                list.add(ab);
            }
            phoneCursor.close();
        }
        return list.size() > 0 ? list : null;
    }

    /**
     * @return 得到手机SIM卡联系人人信息
     */
    private List<AddressBean> getSIMContacts() {
        List<AddressBean> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
                null);
        if (phoneCursor != null) {
            AddressBean ab = null;
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(1);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(0);
                //Sim卡中没有联系人头像
                ab.setPhone(phoneNumber);
                ab.setUserName(contactName);
                ab.setIconBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                list.add(ab);
            }
            phoneCursor.close();
        }
        return list;
    }

    @OnItemClick({R.id.show_address_list_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, UserMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uesrID", list.get(position).getUserID() + "");
        bundle.putString("userName", list.get(position).getUserName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void buttonClick(View view, int position) {
        print("关注 ");
    }
}
