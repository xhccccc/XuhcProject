package com.xuhc.xuhcproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xuhc.camera.CameraActivity;
import com.xuhc.getcontent.GetContentActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xhc.xhcnote.NoteMainActivity;
import com.xuhc.animationexample.AnimationExampleActivity;
import com.xuhc.basemodule.BaseModuleActivity;
import com.xuhc.broadcast.CustomBroadcastActivity;
import com.xuhc.contentresolver.ContentResolverActivity;
import com.xuhc.customview.CustomViewTryActivity;
import com.xuhc.drawboard.DrawBoardActivity;
import com.xuhc.fileoperations.FileOperationsActivity;
import com.xuhc.floatingwindow.FloatWindowActivity;
import com.xuhc.fragment.FragmentTestActivity;
import com.xuhc.glidetry.GlideTryActivity;
import com.xuhc.greendaotry.GreenDaoTryActivity;
import com.xuhc.livedata.LiveDataActivity;
import com.xuhc.mediaplayer.MediaPlayerActivity;
import com.xuhc.mvvm.MVVMActivity;
import com.xuhc.notificationtry.NotificationActivity;
import com.xuhc.permission.PermissionActivity;
import com.xuhc.retrofit_rxjava.RetrofitRxjavaTryActivity;
import com.xuhc.servicetry.ServiceTryActivity;
import com.xuhc.sharedpreferenceutil.SharedPreferenceTryActivity;
import com.xuhc.threads.ThreadsTryActivity;
import com.xuhc.utils.UtilsActivity;
import com.xuhc.viewpager.ViewPagerActivity;
import com.xuhc.viewpager2.ViewPager2Activity;
import com.xuhc.xuhcrecyclerview.RecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private static final String TAG = "xhccc" + MainActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    //可以控制应用dpi
//    @Override // androidx.appcompat.app.AppCompatActivity, android.content.Context, android.view.ContextThemeWrapper, android.content.ContextWrapper
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        Configuration configuration = new Configuration();
//        configuration.densityDpi = 480;
//        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        return resources;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.purple_500));
//            //底部导航栏
//            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = findViewById(R.id.fab_scrolling);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_CONTENT);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.share_with)));
            }
        });
        ImageView image_scrolling_top = findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.drawable.material_design_3).apply(new RequestOptions().fitCenter()).into(image_scrolling_top);


        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            CollapsingToolbarLayout collapsing_toolbar_layout = findViewById(R.id.collapsing_toolbar_layout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        }
    }

    private void initData() {
        mList.add("RecyclerView");
        mList.add("Utils工具类集合");
        mList.add("Base组件");
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
        mList.add("Service");
        mList.add("添加悬浮窗");
        mList.add("画板");
        mList.add("一些自定义View的集合");
        mList.add("Glide");
        mList.add("Retrofit&Rxjava");
        mList.add("GreenDaoTry");
        mList.add("LiveData");
        mList.add("MVVMDemo");
        mList.add("MediaPlayer");
        mList.add("AnimationExample");
        mList.add("GetContent");
        mList.add("Camera");
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
                startActivity(new Intent(this, UtilsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, BaseModuleActivity.class));
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
            case 14:
                startActivity(new Intent(this, ServiceTryActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, FloatWindowActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, DrawBoardActivity.class));
                break;
            case 17:
                startActivity(new Intent(this, CustomViewTryActivity.class));
                break;
            case 18:
                startActivity(new Intent(this, GlideTryActivity.class));
                break;
            case 19:
                startActivity(new Intent(this, RetrofitRxjavaTryActivity.class));
                break;
            case 20:
                startActivity(new Intent(this, GreenDaoTryActivity.class));
                break;
            case 21:
                startActivity(new Intent(this, LiveDataActivity.class));
                break;
            case 22:
                startActivity(new Intent(this, MVVMActivity.class));
                break;
            case 23:
                startActivity(new Intent(this, MediaPlayerActivity.class));
                break;
            case 24:
                startActivity(new Intent(this, AnimationExampleActivity.class));
                break;
            case 25:
                startActivity(new Intent(this, GetContentActivity.class));
                break;
            case 26:
                startActivity(new Intent(this, CameraActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}