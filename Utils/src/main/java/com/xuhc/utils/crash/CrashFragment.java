package com.xuhc.utils.crash;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuhc.basemodule.BaseFragment;
import com.xuhc.utils.R;
import com.xuhc.utils.date.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CrashFragment extends BaseFragment implements View.OnClickListener {

    private Button btMakeCrash;
    private Button btMakeCrash2;
    private TextView tvCrash;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_crash;
    }

    @Override
    public void initView(View view) {
        btMakeCrash = view.findViewById(R.id.bt_make_crash);
        btMakeCrash2 = view.findViewById(R.id.bt_make_crash2);
        tvCrash = view.findViewById(R.id.tv_crash);
    }

    @Override
    public void initFocus() {

    }

    @Override
    public void initData() {
        CrashHandler.getInstance().init();
    }

    @Override
    public void addListener() {
        btMakeCrash.setOnClickListener(this);
        btMakeCrash2.setOnClickListener(this);
    }

    @Override
    public void detach() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void pageSelect() {

    }

    @Override
    public void onClick(View v) {
        //特意制造异常,进行捕捉测试
        int id = v.getId();
        if (id == R.id.bt_make_crash){
            List<String> test = null;
            tvCrash.setText(test.toString());
        } else if (id == R.id.bt_make_crash2) {
            List<String> test2 = null;
            tvCrash.setText(test2.get(1));
        }
    }
}
