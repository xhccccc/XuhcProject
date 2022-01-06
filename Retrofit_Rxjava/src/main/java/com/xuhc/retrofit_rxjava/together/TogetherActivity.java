package com.xuhc.retrofit_rxjava.together;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xuhc.retrofit_rxjava.R;
import com.xuhc.retrofit_rxjava.retrofit.DataModel;

/**
 * retrofit&rxjava一起使用的例子
 * 用的账户中心来做的例子
 * 账户中心也一并上传到github，留自己查阅
 */
public class TogetherActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xhccc" + TogetherActivity.class.getSimpleName();

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together);

        mEditText = findViewById(R.id.et_together_result);

        findViewById(R.id.bt_together).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_together){
            RetrofitHelper.getInstance().get_gson()
                    .subscribeOn(Schedulers.io())// 切换到IO线程进行网络请求
                    .observeOn(AndroidSchedulers.mainThread())// 切换回到主线程 处理请求结果
                    .subscribe(new Observer<DataModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG,d.toString());
                        }

                        @Override
                        public void onNext(DataModel dataModel) {
                            //接收服务器返回的数据
                            Log.d(TAG,dataModel.toString());
                            mEditText.setText(dataModel.toString());
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            mEditText.setText(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}