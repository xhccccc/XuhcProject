package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandleThreadActivity extends AppCompatActivity implements View.OnClickListener {

    Handler mainHandler,workHandler;
    HandlerThread mHandlerThread;
    TextView tvHandleThread;
    Button btHandleThread1,btHandleThread2,btHandleThread3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_thread);

        // 显示文本
        tvHandleThread = (TextView) findViewById(R.id.tv_handle_thread);

        // 创建与主线程关联的Handler
        mainHandler = new Handler();

        /**
         * 步骤1：创建HandlerThread实例对象
         * 传入参数 = 线程名字，作用 = 标记该线程
         */
        mHandlerThread = new HandlerThread("handlerThread");

        /**
         * 步骤2：启动线程
         */
        mHandlerThread.start();

        /**
         * 步骤3：创建工作线程Handler & 复写handleMessage（）
         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
         */

        workHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg) {
                //设置了两种消息处理操作,通过msg来进行识别
                switch(msg.what){
                    // 消息1
                    case 1:
                        try {
                            //延时操作
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                tvHandleThread.setText("我爱学习");
                            }
                        });
                        break;

                    // 消息2
                    case 2:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                tvHandleThread.setText("我不喜欢学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };

        /**
         * 步骤4：使用工作线程Handler向工作线程的消息队列发送消息
         * 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
         */
        // 点击Button1
        btHandleThread1 = (Button) findViewById(R.id.bt_handle_thread1);
        btHandleThread1.setOnClickListener(this);

        // 点击Button2
        btHandleThread2 = (Button) findViewById(R.id.bt_handle_thread2);
        btHandleThread2.setOnClickListener(this);

        // 点击Button3
        // 作用：退出消息循环
        btHandleThread3 = (Button) findViewById(R.id.bt_handle_thread3);
        btHandleThread3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_handle_thread1){
            // 通过sendMessage（）发送
            // a. 定义要发送的消息
            Message msg = Message.obtain();
            msg.what = 1; //消息的标识
            msg.obj = "A"; // 消息的存放
            // b. 通过Handler发送消息到其绑定的消息队列
            workHandler.sendMessage(msg);
        } else if (id == R.id.bt_handle_thread2){
            // 通过sendMessage（）发送
            // a. 定义要发送的消息
            Message msg = Message.obtain();
            msg.what = 2; //消息的标识
            msg.obj = "B"; // 消息的存放
            // b. 通过Handler发送消息到其绑定的消息队列
            workHandler.sendMessage(msg);
        } else if (id == R.id.bt_handle_thread3){
            mHandlerThread.quitSafely();
        }
    }
}