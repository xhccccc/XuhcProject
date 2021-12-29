package com.xuhc.servicetry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class ServiceTryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + ServiceTryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_try);

        findViewById(R.id.bt_startService).setOnClickListener(this);
        findViewById(R.id.bt_stopService).setOnClickListener(this);
        findViewById(R.id.bt_bindService).setOnClickListener(this);
        findViewById(R.id.bt_unbindService).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_startService){
            //构建启动服务的Intent对象
            Intent startIntent = new Intent(this, MyService.class);
            //调用startService()方法-传入Intent对象,以此启动服务
            startService(startIntent);
        } else if (id == R.id.bt_stopService){
            //构建停止服务的Intent对象
            Intent stopIntent = new Intent(this, MyService.class);
            //调用stopService()方法-传入Intent对象,以此停止服务
            stopService(stopIntent);
        } else if (id == R.id.bt_bindService){
            //构建绑定服务的Intent对象
            Intent bindIntent = new Intent(this, MyService2.class);
            //调用bindService()方法,以此绑定服务
            startForegroundService(bindIntent);
            bindService(bindIntent,connection,BIND_AUTO_CREATE);
            //参数说明
            //第一个参数:Intent对象
            //第二个参数:上面创建的Serviceconnection实例
            //第三个参数:标志位
            //这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
            //这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行(纯绑定不会)
        } else if (id == R.id.bt_unbindService){
            //调用unbindService()解绑服务
            //参数是上面创建的Serviceconnection实例
            unbindService(connection);
        }
    }


    private MyService2.MyBinder myBinder;
    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用


        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
        }


        //在Activity与Service建立关联的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected");
            //实例化Service的内部类myBinder
            //通过向下转型得到了MyBinder的实例
            myBinder = (MyService2.MyBinder) service;
            //在Activity调用Service类的方法
            myBinder.service_connect_Activity();
            Log.d(TAG,"myBinder.getContent(): " + myBinder.getContent());
        }
    };
}