package com.xuhc.servicetry;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService2 extends Service {

    private static final int FOREGROUND_ID = 1235;
    private static final String TAG = "xhccc" + MyService2.class.getSimpleName();

    private final String MyService2Text = "xhccc";

    public MyService2() {
    }

    private final MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("执行了onCreate()");

        //需要申请前台服务权限<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
        //Android O以上需要设置渠道才能显示
        String id = "my_channel_02";
        String name = "my_channel_name_02";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //设置点击处理，打开主页面
        Intent intent = new Intent(this, ServiceTryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        Notification notification = builder
                //设置标题
                .setContentTitle("MyService2")
                //设置描述语句
                .setContentText("MyService2正在运行...")
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置点击事件处理
                .setContentIntent(pendingIntent)
                //设置不显示时间
                .setShowWhen(false)
                //Android O以下此项设置无效，Android O以上必须设置
                .setChannelId(id)
                //设置优先级
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //设置通知持续显示，并且不会被清除
                .setOngoing(true)
                //设置点击不消失
                .setAutoCancel(false)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(FOREGROUND_ID, notification);
        startForeground(FOREGROUND_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        System.out.println("执行了onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("执行了onBind()");
        mBinder.setContent(getMyService2Text());
        //返回实例
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("执行了onUnbind()");
        stopForeground(true);
        stopSelf();
        return super.onUnbind(intent);
    }

    //新建一个子类继承自Binder类
    static class MyBinder extends Binder {

        private String Content;

        public void service_connect_Activity() {
            System.out.println("Service关联了Activity,并在Activity执行了Service的方法");
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }

    public String getMyService2Text() {
        return MyService2Text;
    }
}