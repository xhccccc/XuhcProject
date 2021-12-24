package com.xuhc.xuhcrecyclerview.timeline;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xuhc.xuhcrecyclerview.R;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 时间轴 的 Activity
 *
 * Created by Xuhc on 2021/12/17
 *
 * api失效
 */

public class TimelineActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + TimelineActivity.class.getSimpleName();

    private TimelineService mService;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    private TimelineAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        initService();
        initView();
        initData();
    }

    private void initService() {
        mService = new RetrofitClient().getService();
    }

    private void initView() {
        mAdapter = new TimelineAdapter(this);

        mRecyclerView = findViewById(R.id.rv_timeline);
        mProgressBar = findViewById(R.id.progress_bar);

        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void initData() {
        Observable<TimelineBean> observable = mService.getTimeline(
                "zhongtong", "482357364071"
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TimelineBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(TimelineBean timelineBean) {
                        Log.d(TAG, "onNext: ");

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.setTimelineData(timelineBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(TimelineActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
