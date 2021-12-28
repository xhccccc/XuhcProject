package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MyIntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intent_service);

        // 同一服务只会开启1个工作线程
        // 在onHandleIntent（）函数里，依次处理传入的Intent请求
        // 将请求通过Bundle对象传入到Intent，再传入到服务里

        // 请求1
        Intent i = new Intent();
        i.setAction("cn.scu.finch");
        i.setPackage("com.xuhc.xuhcproject");
        Bundle bundle = new Bundle();
        bundle.putString("taskName", "task1");
        i.putExtras(bundle);
        startService(i);

        // 请求2
        Intent i2 = new Intent();
        i2.setAction("cn.scu.finch");
        i2.setPackage("com.xuhc.xuhcproject");
        Bundle bundle2 = new Bundle();
        bundle2.putString("taskName", "task2");
        i2.putExtras(bundle2);
        startService(i2);

        startService(i);  //多次启动

    }
}