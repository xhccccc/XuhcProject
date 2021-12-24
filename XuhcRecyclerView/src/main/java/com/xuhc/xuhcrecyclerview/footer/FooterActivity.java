package com.xuhc.xuhcrecyclerview.footer;

import android.os.Bundle;

import com.xuhc.xuhcrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Xuhc on 2021/12/17
 */

public class FooterActivity extends AppCompatActivity {

    private static final String TAG = FooterActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);

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
        final FooterAdapter adapter = new FooterAdapter(this);

        RecyclerView rcvFooter = findViewById(R.id.rv_footer);

        rcvFooter.setLayoutManager(new LinearLayoutManager(this));
        rcvFooter.setHasFixedSize(true);
        rcvFooter.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvFooter.setAdapter(adapter);

        // 网格类型（关键字：setSpanSizeLookup）
//        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        rcvFooter.setLayoutManager(gridLayoutManager);
//        rcvFooter.setHasFixedSize(true);
//        rcvFooter.setAdapter(adapter);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int spanSize = 1;
//                if (position == mList.size()) {
//                    spanSize = gridLayoutManager.getSpanCount();
//                }
//                return spanSize;
//            }
//        });

        adapter.setFooterListData(mList);
    }
}
