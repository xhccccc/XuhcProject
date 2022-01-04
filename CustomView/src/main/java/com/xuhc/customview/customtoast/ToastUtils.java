package com.xuhc.customview.customtoast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhc.customview.R;

public class ToastUtils {

    private static Toast toast = null;

    public static void showShortToast(Context context, String text){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text){
        Toast.makeText(context,text, Toast.LENGTH_LONG).show();
    }

    public static void showCustomToast(Context context, String message) {
        if (toast != null) {
            toast.cancel();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        LinearLayout toastContainer = (LinearLayout) view.findViewById(R.id.toast_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        toastContainer.setLayoutParams(layoutParams);
        TextView toastText = (TextView) view.findViewById(R.id.toast_text);
        toastText.setText(message);
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP,0,DestinyUtil.dp2px(context,646));
        toast.setView(view);
        toast.show();
    }

}
