package com.xuhc.xuhcrecyclerview.refresh;

import android.os.Bundle;
import android.util.Log;

import com.xuhc.xuhcrecyclerview.R;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 下拉刷新 的 Activity
 *
 * 关键字：setOnRefreshListener
 *
 * Created by Xuhc on 2021/12/17
 *
 * !!!api失效，看使用方法即可!!!
 */

public class RefreshActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + RefreshActivity.class.getSimpleName();

    private RefreshService mService;

    private RefreshAdapter mAdapter;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        initService();
        initView();
        initData();
    }

    private void initService() {
        mService = new RetrofitClient().getService();
    }

    private void initView() {
        mAdapter = new RefreshAdapter(this);

        mRefreshLayout = findViewById(R.id.srl_refresh);
        RecyclerView rvRefresh = findViewById(R.id.rv_refresh);

        // 初始画面，使用 SwipeRefreshLayout 做 Loading
        mRefreshLayout.setRefreshing(true);

        // 自定义 SwipeRefreshLayout 颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple
        );

        // 设定下拉圆圈的背景色
        mRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        // 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        rvRefresh.setLayoutManager(new LinearLayoutManager(this));
        rvRefresh.setHasFixedSize(true);
        rvRefresh.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvRefresh.setAdapter(mAdapter);
    }

    private void initData() {
        Observable<RefreshDataBean> observable = mService.getRefreshData(
                "0b2bdeda43b5688921839c8ecb20399b",
                "%E5%8C%97%E4%BA%AC",
                0,
                10,
                "",
                ""
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RefreshDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(RefreshDataBean refreshDataBean) {
                        Log.d(TAG, "onNext: ");

                        mAdapter.setRefreshDataList(refreshDataBean.getSubjects());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
