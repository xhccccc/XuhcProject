package com.xuhc.xuhcrecyclerview.waterfall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.xuhc.xuhcrecyclerview.R;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 瀑布流 的 Activity
 *
 * 关键字：StaggeredGridLayoutManager
 *
 * Created by Xuhc on 2021/12/17
 *
 * api也可能已失效
 */

public class WaterfallActivity extends AppCompatActivity {

    private static final String TAG = "xhccc" + WaterfallActivity.class.getSimpleName();

    private WaterfallService mService;

    private WaterfallAdapter mAdapter;

    private RecyclerView mRcvWaterfall;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);

        initWaterfallService();

        initView();

        initWaterfallData();
    }

    private void initWaterfallService() {
        mService = new RetrofitClient().getWaterfallService();
    }

    private void initView() {
        mAdapter = new WaterfallAdapter(this);

        mRcvWaterfall = findViewById(R.id.rv_waterfall);
        mProgressBar = findViewById(R.id.progress_bar);

        mRcvWaterfall.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRcvWaterfall.setHasFixedSize(true);
        mRcvWaterfall.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRcvWaterfall.setItemAnimator(new DefaultItemAnimator());
        mRcvWaterfall.setAdapter(mAdapter);
    }

    private void initWaterfallData() {
        Observable<WaterfallBean> observable = mService.getWaterfallData(
                "福利",
                10,
                1
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WaterfallBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");

                        mProgressBar.setVisibility(View.VISIBLE);
                        mRcvWaterfall.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(WaterfallBean waterfallBean) {
                        Log.d(TAG, "onNext: ");

                        mAdapter.setWaterfallData(waterfallBean.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mProgressBar.setVisibility(View.GONE);
                        mRcvWaterfall.setVisibility(View.VISIBLE);
                    }
                });
    }
}
