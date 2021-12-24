package com.xuhc.xuhcrecyclerview.grid;

import android.os.Bundle;

import com.xuhc.xuhcrecyclerview.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 网格布局 的 Activity
 *
 * Created by Xuhc on 2021/12/17
 */

public class GridActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + GridActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initData();
        initView();
    }

    private void initData() {
        mList.add("元祖");
        mList.add("强袭");
        mList.add("自由");
        mList.add("强袭自由");
        mList.add("独角兽");
        mList.add("卡牛");
        mList.add("扎古");
        mList.add("卡扎比");
        mList.add("红异端");
        mList.add("蓝异端");
        mList.add("hg");
        mList.add("rg");
        mList.add("mg");
        mList.add("pg");
        mList.add("元祖2");
        mList.add("强袭2");
        mList.add("自由2");
        mList.add("强袭自由2");
        mList.add("独角兽2");
        mList.add("卡牛2");
        mList.add("扎古2");
        mList.add("卡扎比2");
        mList.add("红异端2");
        mList.add("蓝异端2");
        mList.add("hg2");
        mList.add("rg2");
        mList.add("mg2");
        mList.add("pg2");
    }

    private void initView() {
        GridAdapter adapter = new GridAdapter(this);

        RecyclerView rvGrid = findViewById(R.id.rv_grid);
        GridLayoutManager managerGrid = new GridLayoutManager(this, 2);
        rvGrid.setLayoutManager(managerGrid);
        rvGrid.setHasFixedSize(true);
        rvGrid.setAdapter(adapter);

        adapter.setGridDataList(mList);
    }
}
