package com.xuhc.glidetry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 高版本sdk不支持http明文访问
 * 需要在application添加android:usesCleartextTraffic="true"
 * 或把sdk降到27以下
 */

public class GlideTryActivity extends AppCompatActivity {

    private ImageView ivGlide;

    private static String ME = "http://wx2.sinaimg.cn/large/ceeb653ely1fo88dsm6y3j20f00iutdq.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gilde_try);

        ivGlide = findViewById(R.id.iv_glide);
        setViewImgUrl(ME,ivGlide);
    }

    public void setViewImgUrl(Object imgUrl, ImageView view){
        if(imgUrl != null){
            Log.d("xhccc","setViewImgUrl");
            RequestOptions options = new RequestOptions()
                    .error(R.drawable.app_icon)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(this)
                    .load(imgUrl)
                    .apply(options)
                    .into(view);
        }
    }
}