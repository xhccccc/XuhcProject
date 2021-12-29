package com.xuhc.servicetry;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final int FOREGROUND_ID = 1234;
    private static final String TAG = "xhccc" + MyService.class.getSimpleName();
    private Thread myThread;

    public MyService() {
        System.out.println("执行了MyService()");
    }

    //启动Service之后，就可以在onCreate()或onStartCommand()方法里去执行一些具体的逻辑
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("执行了onCreate()");

        //需要申请前台服务权限<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
        //Android O以上需要设置渠道才能显示
        String id = "my_channel_01";
        String name = "my_channel_name";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //设置点击处理，打开主页面
        Intent intent = new Intent(this, ServiceTryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        Notification notification = builder
                //设置标题
                .setContentTitle("MyService")
                //设置描述语句
                .setContentText("MyService正在运行...")
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
        myThread = new Thread(work);
        myThread.start();
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("执行了onDestroy()");
        if (myThread != null) {
            myThread.interrupt();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Runnable work = new Runnable() {

        @Override
        public void run() {
            while (!Thread.interrupted()) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                Log.d(TAG,"hello,world");

                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread interrupted. Exiting...");
                    break;
                }

            }

        }
    };
}