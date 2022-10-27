package com.xuhc.floatingwindow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FloatWindowActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addBtn;
    private SideBar mSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        initView();
        addListener();
        initSideBar();
    }

    private void initView() {
        addBtn = findViewById(R.id.bt_add);
    }

    private void addListener() {
        addBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_add) {
            if (mSideBar != null) {
                mSideBar.addBar();
            }
        }
    }

    private void initSideBar() {
        mSideBar = new SideBar(this, false);
    }


}