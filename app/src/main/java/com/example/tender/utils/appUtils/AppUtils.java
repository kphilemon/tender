package com.example.tender.utils.appUtils;


import android.content.Context;
import android.widget.Toast;

import com.example.tender.model.UserWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppUtils {

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String formatNames(HashMap<String, String> names, String excludeKey) {
        ArrayList<String> list = new ArrayList<>();
        for (String key: names.keySet()) {
            if (!key.equals(excludeKey)) {
                list.add(names.get(key));
            }
        }

        if (list.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(list.get(0));
        if (list.size() == 1) {
            return sb.toString();
        }

        for (int i = 1; i < list.size() - 1; i++) {
            sb.append(", ").append(list.get(i));
        }

        sb.append(" and ").append(list.get(list.size() - 1));
        return sb.toString();
    }

}
