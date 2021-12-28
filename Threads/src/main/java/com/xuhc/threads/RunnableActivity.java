package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


/**
 *
 * 目的:两个窗口一起卖100张票
 *
 **/
public class RunnableActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + RunnableActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable);

        findViewById(R.id.bt_runnable_ticket).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_runnable_ticket){
            //步骤2:创建线程类的实例
            //因为是两个窗口共卖100张票,即共用资源
            //所以只实例化一个实现了Runnable接口的类
            MyThread1 mt = new MyThread1();

            //因为要创建二个线程，模拟二个窗口卖票
            Thread mt11 = new Thread(mt, "窗口1");
            Thread mt12 = new Thread(mt, "窗口2");

            //步骤3：调用start()方法开启线程
            //启动二个线程，也即是窗口，开始卖票
            mt11.start();
            mt12.start();

        }
    }

    //步骤1:创建线程类，实现Runnable接口
    private static class MyThread1 implements Runnable{

        private int ticket = 100;//两个窗口一共要卖100张票

        //在run方法里复写需要进行的操作:卖票速度1s/张
        @Override
        public void run(){
            while (ticket>0){
                ticket--;
                System.out.println(Thread.currentThread().getName() + "卖掉了1张票，剩余票数为:"+ticket);

                try {
                    Thread.sleep(1000);//卖票速度是1s一张
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}