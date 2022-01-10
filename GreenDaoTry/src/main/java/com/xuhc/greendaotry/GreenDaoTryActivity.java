package com.xuhc.greendaotry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xuhc.greendaotry.bean.Schedule;
import com.xuhc.greendaotry.utils.DaoManager;
import com.xuhc.greendaotry.utils.ScheduleDaoUtils;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoTryActivity extends AppCompatActivity implements View.OnClickListener {

    private ScheduleDaoUtils mScheduleDaoUtils;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_try);
        initGreenDao();

        findViewById(R.id.s_btn_insert).setOnClickListener(this);
        findViewById(R.id.s_btn_update).setOnClickListener(this);
        findViewById(R.id.s_btn_delete).setOnClickListener(this);
        findViewById(R.id.s_btn_delete_all).setOnClickListener(this);
        findViewById(R.id.s_btn_check_all).setOnClickListener(this);
        findViewById(R.id.s_btn_query).setOnClickListener(this);
        show = (TextView) findViewById(R.id.s_tv_show);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduleDaoUtils.close();
    }

    private void initGreenDao() {
        //一般init放在application里执行
        DaoManager.getInstance().init(this);
        mScheduleDaoUtils = new ScheduleDaoUtils();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        StringBuilder allText;
        if (id == R.id.s_btn_insert) {//插入
            List<Schedule> scheduleList = new ArrayList<>();
            scheduleList.add(new Schedule(null, 2020, 5, 18, 8, 0, 0, "Hello"));
            scheduleList.add(new Schedule(null, 2020, 5, 18, 9, 0, 0, "Hello"));
            scheduleList.add(new Schedule(null, 2020, 5, 19, 8, 0, 0, "Hello"));
            scheduleList.add(new Schedule(null, 2020, 5, 20, 8, 0, 0, "xhc"));
            scheduleList.add(new Schedule(null, 2020, 5, 21, 8, 0, 0, "xuhc"));
            scheduleList.add(new Schedule(null, 2022, 1, 7, 15, 45, 0, "早上突发验核酸"));
            mScheduleDaoUtils.insertOrReplaceSchedule(scheduleList);
        } else if (id == R.id.s_btn_update) {
            List<Schedule> scheduleList3 = mScheduleDaoUtils.queryAll();
            mScheduleDaoUtils.updateSchedule(scheduleList3.get(0).getMyId(), new Schedule(null,2022,1,7,15,42,0,"Hello,fucking day"));
        } else if (id == R.id.s_btn_delete) {
            List<Schedule> scheduleList4 = mScheduleDaoUtils.queryAll();
            mScheduleDaoUtils.deleteSchedule(scheduleList4.get(0).getMyId());
        } else if (id == R.id.s_btn_delete_all) {
            mScheduleDaoUtils.deleteAll();
        } else if (id == R.id.s_btn_check_all) {
            List<Schedule> scheduleList1 = mScheduleDaoUtils.queryAll();
            allText = new StringBuilder();

            if (scheduleList1.size() > 0){
                for (Schedule schedule : scheduleList1) {
                    String a = schedule.getDetails();
                    allText = allText.append("\n").append(a);
                }
            } else {
                allText.append("没有内容");
            }


            show.setText("Details: " + allText);
        } else if (id == R.id.s_btn_query) {
            List<Schedule> scheduleList2 = mScheduleDaoUtils.queryScheduleForDate(2020, 5, 21);
            allText = new StringBuilder();

            if (scheduleList2.size() > 0){
                for (Schedule schedule : scheduleList2) {
                    String a = schedule.getDetails();
                    allText = allText.append("\n").append(a);
                }
            } else {
                allText.append("没有查询到相关内容");
            }


            show.setText("Details: " + allText);
        }
    }
}