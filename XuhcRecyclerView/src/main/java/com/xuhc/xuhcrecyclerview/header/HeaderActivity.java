package com.xuhc.xuhcrecyclerview.header;

import android.os.Bundle;

import com.xuhc.xuhcrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Xuhc on 2021/12/17
 */

public class HeaderActivity extends AppCompatActivity {

    private static final String TAG = HeaderActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

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
        HeaderAdapter adapter = new HeaderAdapter(this);

        RecyclerView rcvHeader = findViewById(R.id.rv_header);

        rcvHeader.setLayoutManager(new LinearLayoutManager(this));
        rcvHeader.setHasFixedSize(true);
        rcvHeader.setAdapter(adapter);

        // 网格类型（关键字：setSpanSizeLookup）
//        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int spanSize = 1;
//                if (position == 0) {
//                    spanSize = gridLayoutManager.getSpanCount();
//                }
//                return spanSize;
//            }
//        });
//        rcvHeader.setLayoutManager(gridLayoutManager);
//        rcvHeader.setHasFixedSize(true);
//        rcvHeader.setAdapter(adapter);

        adapter.setHeaderDataList(mList);
    }
}
