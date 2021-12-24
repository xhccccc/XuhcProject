package com.xuhc.xuhcrecyclerview.sticky;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xuhc.xuhcrecyclerview.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 顶部悬浮 的 Activity
 *
 * Created by Xuhc on 2021/12/17
 */

public class StickyActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + StickyActivity.class.getSimpleName();

    private List<String> mList = new ArrayList<>();

    private List<StickyData> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);

        initList();

        initData();

        initView();
    }

    private void initList() {
        mList.add("东南分区|亚特兰大老鹰");
        mList.add("东南分区|夏洛特黄蜂");
        mList.add("东南分区|迈阿密热火");
        mList.add("东南分区|奥兰多魔术");
        mList.add("东南分区|华盛顿奇才");
        mList.add("大西洋分区|波士顿凯尔特人");
        mList.add("大西洋分区|布鲁克林篮网");
        mList.add("大西洋分区|纽约尼克斯");
        mList.add("大西洋分区|费城76人");
        mList.add("大西洋分区|多伦多猛龙");
        mList.add("中央分区|芝加哥公牛");
        mList.add("中央分区|克里夫兰骑士");
        mList.add("中央分区|底特律活塞");
        mList.add("中央分区|印第安纳步行者");
        mList.add("中央分区|密尔沃基雄鹿");
        mList.add("西南分区|达拉斯独行侠");
        mList.add("西南分区|休斯顿火箭");
        mList.add("西南分区|孟菲斯灰熊");
        mList.add("西南分区|新奥尔良鹈鹕");
        mList.add("西南分区|圣安东尼奥马刺");
        mList.add("西北分区|丹佛掘金");
        mList.add("西北分区|明尼苏达森林狼");
        mList.add("西北分区|俄克拉荷马城雷霆");
        mList.add("西北分区|波特兰开拓者");
        mList.add("西北分区|犹他爵士");
        mList.add("太平洋分区|金州勇士");
        mList.add("太平洋分区|洛杉矶快船");
        mList.add("太平洋分区|洛杉矶湖人");
        mList.add("太平洋分区|菲尼克斯太阳");
        mList.add("太平洋分区|萨克拉门托国王");
    }

    private void initData() {
        for (int i = 0; i < mList.size(); i++) {
            StickyData bean = new StickyData();

            String s = mList.get(i);
            // area
            String area = s.substring(0, s.indexOf("|"));
            // team
            String team = s.substring(s.indexOf("|") + 1, s.length());

            bean.setArea(area);
            bean.setTeam(team);

            mDataList.add(bean);
        }

        Log.d(TAG, "initData: " + mDataList.size());
    }

    private void initView() {
        StickyAdapter adapter = new StickyAdapter(this);

        RecyclerView rcvSticky = findViewById(R.id.rv_sticky);
        final TextView tvArea = findViewById(R.id.tv_sticky_header_view);

        rcvSticky.setLayoutManager(new LinearLayoutManager(this));
        rcvSticky.setHasFixedSize(true);
        rcvSticky.setAdapter(adapter);

        adapter.setStickyDataList(mDataList);

        rcvSticky.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        tvArea.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvArea.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvArea.getMeasuredWidth() / 2, tvArea.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvArea.getMeasuredHeight();

                    if (transViewStatus == StickyAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvArea.setTranslationY(dealtY);
                        } else {
                            tvArea.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyAdapter.NONE_STICKY_VIEW) {
                        tvArea.setTranslationY(0);
                    }
                }
            }
        });
    }
}
