package com.xuhc.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xuhc.threads.util.ThreadPoolManager;

public class ThreadPoolTryActivity extends AppCompatActivity implements View.OnClickListener {

    private ThreadPoolManager threadPoolManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_try);

        threadPoolManager = ThreadPoolManager.getInstance();
        findViewById(R.id.bt_thread_pool_try).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_thread_pool_try){
            for(int i = 0;i<30;i++){
                final int finali = i;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Log.d("xhcccThread", "run: " + finali);
                            Log.d("xhccc当前线程：",Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                threadPoolManager.execute(runnable);
            }
        }
    }
}