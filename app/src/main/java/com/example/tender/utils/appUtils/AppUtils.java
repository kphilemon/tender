package com.example.tender.utils.appUtils;


import android.content.Context;
import android.widget.Toast;

public class AppUtils {

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
