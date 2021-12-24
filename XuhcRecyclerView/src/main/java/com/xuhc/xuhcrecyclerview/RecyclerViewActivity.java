package com.xuhc.xuhcrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xuhc.xuhcrecyclerview.click.RvClickActivity;

import com.xuhc.xuhcrecyclerview.drag.DragActivity;
import com.xuhc.xuhcrecyclerview.expandcollapse.ExpandCollapseActivity;
import com.xuhc.xuhcrecyclerview.footer.FooterActivity;
import com.xuhc.xuhcrecyclerview.grid.GridActivity;
import com.xuhc.xuhcrecyclerview.group.GroupActivity;
import com.xuhc.xuhcrecyclerview.header.HeaderActivity;
import com.xuhc.xuhcrecyclerview.horizontal.HorizontalActivity;
import com.xuhc.xuhcrecyclerview.link.LinkActivity;
import com.xuhc.xuhcrecyclerview.load.LoadActivity;
import com.xuhc.xuhcrecyclerview.refresh.RefreshActivity;
import com.xuhc.xuhcrecyclerview.slide.SlideActivity;
import com.xuhc.xuhcrecyclerview.snaphelper.SnapHelperActivity;
import com.xuhc.xuhcrecyclerview.sticky.StickyActivity;
import com.xuhc.xuhcrecyclerview.swipe.SwipeActivity;
import com.xuhc.xuhcrecyclerview.timeline.TimelineActivity;
import com.xuhc.xuhcrecyclerview.vertical.VerticalActivity;
import com.xuhc.xuhcrecyclerview.waterfall.WaterfallActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView使用集合 的 Activity
 *
 * Created by Xuhc on 2021/12/17
 */


public class RecyclerViewActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "xhccc" + RecyclerViewActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initData();
        initView();
    }

    private void initData() {
        mList.add("纵向布局");
        mList.add("横向布局");
        mList.add("网格布局");
        mList.add("点击");
        mList.add("分组");
        mList.add("顶部悬浮");
        mList.add("拖动");
        mList.add("滑动删除");
        mList.add("下拉刷新(api失效)");
        mList.add("上拉加载(api失效)");
        mList.add("双向滑动");
        mList.add("RecyclerView item 居中对齐");
        mList.add("展开和收缩");
        mList.add("瀑布流");
        mList.add("时间轴");
        mList.add("为 RecyclerView 添加 Footer");
        mList.add("为 RecyclerView 添加 Header");
        mList.add("左右联动");
    }

    private void initView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        RecyclerView rvVertical = findViewById(R.id.rv_recycler);
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
        Log.d(TAG,"recyclerViewActivity_position: " + position);
        switch (position){
            case 0:
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, HorizontalActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GridActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RvClickActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, GroupActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, StickyActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, DragActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, SwipeActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, RefreshActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, LoadActivity.class));
                break;
            case 10:
                startActivity(new Intent(this, SlideActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, SnapHelperActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, ExpandCollapseActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, WaterfallActivity.class));
                break;
            case 14:
                startActivity(new Intent(this, TimelineActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, FooterActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, HeaderActivity.class));
                break;
            case 17:
                startActivity(new Intent(this, LinkActivity.class));
                break;
        }
    }
}