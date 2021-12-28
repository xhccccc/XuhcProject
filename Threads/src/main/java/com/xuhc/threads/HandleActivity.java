package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class HandleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + HandleActivity.class.getSimpleName();

    private TextView tvHandle1,tvHandle2,tvHandle3;
    public Handler mHandler,mHandler2,mHandler3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);

        //正确用法，避免内存泄露
        startFHandle();

        // 步骤2：在主线程中创建Handler实例(方式一|内部类)
        mHandler = new MyHandler();
        findViewById(R.id.bt_handle1).setOnClickListener(this);
        tvHandle1 = findViewById(R.id.tv_handle1);

        // 步骤1：在主线程中 通过匿名内部类 创建Handler类对象 (方式二|匿名内部类)
        mHandler2 = new Handler(Looper.myLooper()){
            // 通过复写handlerMessage()从而确定更新UI的操作
            @Override
            public void handleMessage(Message msg) {
                // 根据不同线程发送过来的消息，执行不同的UI操作
                switch (msg.what) {
                    case 3:
                        tvHandle2.setText("执行了线程1的UI操作");
                        break;
                    case 4:
                        tvHandle2.setText("执行了线程2的UI操作");
                        break;
                }
            }
        };
        findViewById(R.id.bt_handle2).setOnClickListener(this);
        tvHandle2 = findViewById(R.id.tv_handle2);

        // 步骤1：在主线程中创建Handler实例(方式三|Handler.post)
        mHandler3 = new Handler();
        findViewById(R.id.bt_handle3).setOnClickListener(this);
        tvHandle3 = findViewById(R.id.tv_handle3);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_handle1){
            // 采用继承Thread类实现多线程演示(方式一|内部类)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 步骤3：创建所需的消息对象(方式一|内部类)
                    Message msg = Message.obtain();
                    msg.what = 1; // 消息标识
                    msg.obj = "A"; // 消息内存存放

                    // 步骤4：在工作线程中 通过Handler发送消息到消息队列中(方式一|内部类)
                    mHandler.sendMessage(msg);
                }
            }.start();
            // 步骤5：开启工作线程（同时启动了Handler）(方式一|内部类)

            // 此处用2个工作线程展示(方式一|内部类)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 通过sendMessage（）发送
                    // a. 定义要发送的消息
                    Message msg = Message.obtain();
                    msg.what = 2; //消息的标识
                    msg.obj = "B"; // 消息的存放
                    // b. 通过Handler发送消息到其绑定的消息队列
                    mHandler.sendMessage(msg);
                }
            }.start();

        } else if (id == R.id.bt_handle2){
            // 采用继承Thread类实现多线程演示 (方式二|匿名内部类)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 步骤3：创建所需的消息对象 (方式二|匿名内部类)
                    Message msg = Message.obtain();
                    msg.what = 3; // 消息标识
                    msg.obj = "C"; // 消息内存存放

                    // 步骤4：在工作线程中 通过Handler发送消息到消息队列中 (方式二|匿名内部类)
                    mHandler2.sendMessage(msg);
                }
            }.start();
            // 步骤5：开启工作线程（同时启动了Handler） (方式二|匿名内部类)

            // 此处用2个工作线程展示 (方式二|匿名内部类)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 通过sendMessage（）发送
                    // a. 定义要发送的消息
                    Message msg = Message.obtain();
                    msg.what = 4; //消息的标识
                    msg.obj = "D"; // 消息的存放
                    // b. 通过Handler发送消息到其绑定的消息队列
                    mHandler2.sendMessage(msg);
                }
            }.start();
        } else if (id == R.id.bt_handle3){
            // 步骤2：在工作线程中 发送消息到消息队列中 & 指定操作UI内容(方式三|Handler.post)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 通过psot（）发送，需传入1个Runnable对象
                    mHandler3.post(new Runnable() {
                        @Override
                        public void run() {
                            // 指定操作UI内容
                            tvHandle3.setText("执行了线程1的UI操作");
                        }

                    });
                }
            }.start();
            // 步骤3：开启工作线程（同时启动了Handler）(方式三|Handler.post)

            // 此处用2个工作线程展示(方式三|Handler.post)
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler3.post(new Runnable() {
                        @Override
                        public void run() {
                            tvHandle3.setText("执行了线程2的UI操作");
                        }

                    });
                }
            }.start();
        }
    }

    // 步骤1：（自定义）新创建Handler子类(继承Handler类) & 复写handleMessage（）方法 (方式一|内部类)
    class MyHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 1:
                    tvHandle1.setText("执行了线程1的UI操作");
                    break;
                case 2:
                    tvHandle1.setText("执行了线程2的UI操作");
                    break;
            }
        }
    }


    private Handler showHandler;
    private void startFHandle(){
        //1. 实例化自定义的Handler类对象->>分析1
        //注：
        // a. 此处并无指定Looper，故自动绑定当前线程(主线程)的Looper、MessageQueue；
        // b. 定义时需传入持有的Activity实例（弱引用）
        showHandler = new FHandler(this);

        // 2. 启动子线程1
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1;// 消息标识
                msg.obj = "AA";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                showHandler.sendMessage(msg);
            }
        }.start();

        // 3. 启动子线程2
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 2;// 消息标识
                msg.obj = "BB";// 消息存放
                // b. 传入主线程的Handler & 向其MessageQueue发送消息
                showHandler.sendMessage(msg);
            }
        }.start();
    }


    // 分析1：自定义Handler子类
    // 设置为：静态内部类
    private static class FHandler extends Handler{

        // 定义 弱引用实例
        private WeakReference<Activity> reference;

        // 在构造方法中传入需持有的Activity实例
        public FHandler(Activity activity) {
            // 使用WeakReference弱引用持有Activity实例
            reference = new WeakReference<Activity>(activity); }

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "收到线程1的消息");
                    break;
                case 2:
                    Log.d(TAG, " 收到线程2的消息");
                    break;


            }
        }
    }


}