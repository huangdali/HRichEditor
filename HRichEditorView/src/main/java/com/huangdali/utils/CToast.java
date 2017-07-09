package com.huangdali.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast简单封装
 * Created by HDL on 2017/7/9.
 */

public class CToast {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
