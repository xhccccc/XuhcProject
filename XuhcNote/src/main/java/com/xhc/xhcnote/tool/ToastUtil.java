package com.xhc.xhcnote.tool;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void ShowShort(Context context, String text){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
    }

}
