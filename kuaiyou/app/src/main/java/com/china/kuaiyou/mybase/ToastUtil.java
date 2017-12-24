package com.china.kuaiyou.mybase;

import android.widget.Toast;


/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(
            String content) {
        if (BaseAppliction.getContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(BaseAppliction.getContext(),
                        content,
                        Toast.LENGTH_SHORT);
            } else {
                toast.setText(content);
            }
            toast.show();
        }
    }
}
