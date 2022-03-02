package com.xuhc.greendaotry.utils;

import com.xuhc.greendaotry.bean.Schedule;
import com.xuhc.greendaotry.gen.ScheduleDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class ScheduleDaoUtils {

    private static final String TAG = ScheduleDaoUtils.class.getSimpleName();
    private DaoManager mDaoManager;

    /**
     * Instantiates a new Schedule dao utils.
     */
    public ScheduleDaoUtils(){
        mDaoManager = DaoManager.getInstance();
    }

    /**
     * Close
     */
    public void close(){
        mDaoManager.closeConnection();
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param scheduleList the schedule list
     * @return boolean
     */
    public boolean insertOrReplaceSchedule(final List<Schedule> scheduleList) {
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Schedule schedule : scheduleList) {
                        mDaoManager.getDaoSession().getScheduleDao().insertOrReplace(schedule);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据id更新修改
     *
     * @param id     the id
     */
    public boolean updateSchedule(long id, Schedule editsche){
        boolean flag = false;
        try {
            Schedule schedule = mDaoManager.getDaoSession().queryBuilder(Schedule.class).where(ScheduleDao.Properties.MyId.eq(id)).build().unique();
            if (schedule != null){
                schedule.setYear(editsche.getYear());
                schedule.setMonth(editsche.getMonth());
                schedule.setDay(editsche.getDay());
                schedule.setHour(editsche.getHour());
                schedule.setMinute(editsche.getMinute());
                schedule.setRemind(editsche.getRemind());
                schedule.setDetails(editsche.getDetails());
                mDaoManager.getDaoSession().update(schedule);
            }
            flag = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据id删除单条记录
     *
     * @param id    the id
     * @return
     */
    public boolean deleteSchedule(long id){
        boolean flag = false;
        try {
            mDaoManager.getDaoSession().queryBuilder(Schedule.class).where(ScheduleDao.Properties.MyId.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
            flag = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return boolean
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mDaoManager.getDaoSession().deleteAll(Schedule.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return list
     */
    public List<Schedule> queryAll(){
        try {
            return mDaoManager.getDaoSession().loadAll(Schedule.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用queryBuilder进行查询
     * 根据日期
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return list
     */
    public List<Schedule> queryScheduleForDate(int year, int month, int day){
        try {
            QueryBuilder<Schedule> queryBuilder = mDaoManager.getDaoSession().queryBuilder(Schedule.class);
            return queryBuilder.where(ScheduleDao.Properties.Year.eq(year),ScheduleDao.Properties.Month.eq(month),ScheduleDao.Properties.Day.eq(day)).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用queryBuilder进行查询
     * 根据id
     * @param id
     * @return list
     */
    public List<Schedule> queryScheduleForId(long id){
        try {
            QueryBuilder<Schedule> queryBuilder = mDaoManager.getDaoSession().queryBuilder(Schedule.class);
            return queryBuilder.where(ScheduleDao.Properties.MyId.eq(id)).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}