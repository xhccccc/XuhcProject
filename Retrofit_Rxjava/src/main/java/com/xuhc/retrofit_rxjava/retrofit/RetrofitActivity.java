package com.xuhc.retrofit_rxjava.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xuhc.retrofit_rxjava.R;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        mEditText = findViewById(R.id.et_retrofit_result);
        findViewById(R.id.bt_retrofit_test1).setOnClickListener(this);
        findViewById(R.id.bt_retrofit_test1_Gosn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_retrofit_test1){
            test1();
        } else if (id == R.id.bt_retrofit_test1_Gosn){
            test1_Gson();
        }
    }

    private void test1() {
        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/wxarticle/chapters/json/")
                .build();

        //用Retrofit创建接口实例对象
        ApiService apiService = retrofit.create(ApiService.class);
        //获取Call对象
        Call<ResponseBody> call = apiService.get();
        //开始异步操作
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String json = null;
                try {
                    if (response.body() != null){
                        json = response.body().string();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                mEditText.setText(json);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mEditText.setText("failure");
            }
        });
    }

    private void test1_Gson() {
        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wanandroid.com/wxarticle/chapters/json/")
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析(记得加入依赖)
                .build();

        //用Retrofit创建接口实例对象
        ApiService apiService = retrofit.create(ApiService.class);
        //获取Call对象
        Call<DataModel> call = apiService.get_gson();
        //开始异步操作
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.body() != null){
                    mEditText.setText(response.body().getData().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                mEditText.setText("failure");
            }
        });
    }
}