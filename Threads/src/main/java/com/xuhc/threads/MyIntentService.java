package com.xuhc.threads;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

  /** 
    * 在构造函数中传入线程名字
    **/  
    public MyIntentService() {
        // 调用父类的构造函数
        // 参数 = 工作线程的名字
        super("MyIntentService");
    }

   /** 
     * 复写onHandleIntent()方法
     * 根据 Intent实现 耗时任务 操作
     **/  
    @Override
    protected void onHandleIntent(Intent intent) {

        // 根据 Intent的不同，进行不同的事务处理
        String taskName = intent.getExtras().getString("taskName");
        switch (taskName) {
            case "task1":
                Log.i("MyIntentService", "do task1");
                break;
            case "task2":
                Log.i("MyIntentService", "do task2");
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate() {
        Log.i("MyIntentService", "onCreate");
        super.onCreate();
    }
   /** 
     * 复写onStartCommand()方法
     * 默认实现 = 将请求的Intent添加到工作队列里
     **/  
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyIntentService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("MyIntentService", "onDestroy");
        super.onDestroy();
    }
}
