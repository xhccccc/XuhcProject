package com.xuhc.utils.date;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuhc.basemoudle.BaseFragment;
import com.xuhc.utils.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeFragment extends BaseFragment implements View.OnClickListener {

    private Button btDoWork;
    private TextView tvContent;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_utils;
    }

    @Override
    public void initView(View view) {
        btDoWork = view.findViewById(R.id.bt_date_utils_fragment);
        tvContent = view.findViewById(R.id.tv_date_utils_fragment);
    }

    @Override
    public void initFocus() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        btDoWork.setOnClickListener(this);
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
        int id = v.getId();
        if (id == R.id.bt_date_utils_fragment){
            Log.d("xhccc","test");
            String content = "getCurrentDate: " + DateTimeUtil.getCurrentDate() + "\n"
                    + "自定义格式的getCurrentDate: " + DateTimeUtil.getCurrentDate(new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.getDefault())) + "\n"
                    + "getTimeLong: " + DateTimeUtil.getTimeLong() + "\n"
                    + "getCurrentDateNoYear(long time): " + DateTimeUtil.getCurrentDateNoYear(DateTimeUtil.getTimeLong()) + "\n"
                    + "getCurrentYear: " + DateTimeUtil.getCurrentYear() + "\n"
                    + "getCurrentMonth: " + DateTimeUtil.getCurrentMonth() + "\n"
                    + "getCurrentDay: " + DateTimeUtil.getCurrentDay() + "\n"
                    + "getCurrentWeekend: " + DateTimeUtil.getCurrentWeekend()+ "\n"
                    + "getCurrentTimeByAPM: " + DateTimeUtil.getCurrentTimeByAPM() + "\n"
                    + "getMouthToEnglish: " + DateTimeUtil.getMouthToEnglish(getContext()) + "\n"
                    + "getDayToEnglish: " + DateTimeUtil.getDayToEnglish(getContext()) + "\n"
                    + "getCurrentTime: " + DateTimeUtil.getCurrentTime(getContext()) + "\n"
                    + "是否是24小时制_isCurrent24Hour: " + DateTimeUtil.isCurrent24Hour(getContext()) + "\n";
            tvContent.setText(content);
        }
    }
}
