package com.xuhc.notificationtry;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //通知建立通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        findViewById(R.id.send_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.send_notification){
            sendChatMsg();
            sendSubscribeMsg();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void sendChatMsg() {
        Intent intent = new Intent(this, NotificationContentActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.area)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.area))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }

    public void sendSubscribeMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.area)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.area))
                .setAutoCancel(true)
                .build();
        manager.notify(2, notification);
    }
}
