package com.xuhc.xuhcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.xhc.xhcnote.NoteMainActivity;
import com.xuhc.broadcast.CustomBroadcastActivity;
import com.xuhc.contentresolver.ContentResolverActivity;
import com.xuhc.drawboard.DrawBoardActivity;
import com.xuhc.fileoperations.FileOperationsActivity;
import com.xuhc.floatingwindow.FloatWindowActivity;
import com.xuhc.fragment.FragmentTestActivity;
import com.xuhc.notificationtry.NotificationActivity;
import com.xuhc.permission.PermissionActivity;
import com.xuhc.sharedpreferenceutil.SharedPreferenceTryActivity;
import com.xuhc.threads.ThreadsTryActivity;
import com.xuhc.viewpager.ViewPagerActivity;
import com.xuhc.viewpager2.ViewPager2Activity;
import com.xuhc.xuhcrecyclerview.RecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private static final String TAG = "xhccc" + MainActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_500));
            //底部导航栏
            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initData();
        initView();
    }

    private void initData() {
        mList.add("RecyclerView");
        mList.add("画板");
        mList.add("添加悬浮窗");
        mList.add("动态权限申请");
        mList.add("简易Fragment");
        mList.add("ViewPager");
        mList.add("ViewPager2");
        mList.add("XuhcNote");
        mList.add("文件操作相关");
        mList.add("简单发送通知");
        mList.add("内容提供者查询（查询XuhcNote的提供）");
        mList.add("自定义广播");
        mList.add("SharePreferenceUtil");
        mList.add("Threads(线程使用)");
    }

    private void initView() {
        MainAdapter adapter = new MainAdapter(this);
        RecyclerView rvVertical = findViewById(R.id.main_rv);
        LinearLayoutManager managerVertical = new LinearLayoutManager(this);
        managerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        // 也可以直接写成：
//        rcvVertical.setLayoutManager(new LinearLayoutManager(this));
        rvVertical.setLayoutManager(managerVertical);
        rvVertical.setHasFixedSize(true);
        rvVertical.setAdapter(adapter);
        adapter.setDataList(mList);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG,"main_position: " + position);
        switch (position){
            case 0:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, DrawBoardActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, FloatWindowActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, FragmentTestActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ViewPager2Activity.class));
                break;
            case 7:
                startActivity(new Intent(this, NoteMainActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, FileOperationsActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case 10:
                startActivity(new Intent(this, ContentResolverActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, CustomBroadcastActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, SharedPreferenceTryActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, ThreadsTryActivity.class));
                break;
        }
    }
}