package com.china.kuaiyou.mybase;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreferences {
    private static MySharePreferences mySharePreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MySharePreferences() {
        setSPXml();
    }

    public static MySharePreferences getInstance() {
        if (mySharePreferences == null) {
            synchronized (MySharePreferences.class) {
                if (mySharePreferences == null) {
                    mySharePreferences=new MySharePreferences();
                }
            }
        }
        return mySharePreferences;
    }

    // 创建共享存储
    private void setSPXml() {
        sharedPreferences =BaseAppliction.getContext().getSharedPreferences("xiaoge_sp_xml",
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // 在共享文件放入String
    public void putString(String K, String V) {
        editor.putString(K, V);
        editor.commit();
    }

    // 取出共享文件的String
   public String getString(String K) {
        String shuju = sharedPreferences.getString(K, "");
        return shuju;
    }

    // 清楚共享数据
    public void editorClear() {
        editor.clear();
        editor.commit();
    }
}
