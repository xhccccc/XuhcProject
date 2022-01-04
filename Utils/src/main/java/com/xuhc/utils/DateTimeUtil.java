package com.xuhc.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.format.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    /**
     * 获取当前系统日期，格式：yyyy.MM.dd
     *
     * @return current date
     */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
        return sf.format(calendar.getTime());
    }

    /**
     * 获取当前系统日期,自定义格式
     *
     * @param simpleDateFormat 自定义要返回的日期格式;例: new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.getDefault())
     * @return 自定义的日期字符
     */
    public static String getCurrentDate(SimpleDateFormat simpleDateFormat) {
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * Get time long long.
     *
     * @return the long
     */
    public static long getTimeLong(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    /**
     * 根据时间戳获取
     *
     * @param time 时间戳
     * @return 日期时间字符
     */
    public static String getCurrentDateNoYear(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("M月dd日", Locale.getDefault());
        return sf.format(time);
    }

    /**
     * 获取年
     *
     * @return 当前年份字符
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月。月是从0开始的，所以需要+1
     *
     * @return 当前月份字符
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日（当月的第几日）
     *
     * @return 当前日的字符
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 月的缩写，3月9日的表达方式是：March 9th 或者 March 09th
     * <p>
     * 更多格式：
     * 英式日期格式（日,月,年）（注意，英文逗号）：22nd,July,2009 或 22,july,2009
     * 美式日期格式（月,日,年）（注意，英文逗号）：july 22nd,2009 或 july 22,2009
     *
     * @param context the context
     * @return mouth 2 english abb name
     */
    public static String getMouthToEnglish(Context context) {
        if (context == null){
            return null;
        }
        String[] dateFormat = context.getResources().getStringArray(R.array.str_array_month_english_name_abb);
        return dateFormat[getCurrentMonth() - 1];
    }

    /**
     * 日的缩写，3月9日的表达方式是：March 9th 或者 March 09th
     * <p>
     * 更多格式：
     * 英式日期格式（日,月,年）（注意，英文逗号）：22nd,July,2009 或 22,july,2009
     * 美式日期格式（月,日,年）（注意，英文逗号）：july 22nd,2009 或 july 22,2009
     *
     * @param context the context
     * @return day 2 english abb name
     */
    public static String getDayToEnglish(Context context) {
        if (context == null){
            return null;
        }
        String[] dateFormat = context.getResources().getStringArray(R.array.str_array_day_english_name_abb);
        return dateFormat[getCurrentDay() - 1];
    }


    /**
     * 获取当前时间
     *
     * @param context the context
     * @return 当前时间
     */
    public static String getCurrentTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf;
        if (isCurrent24Hour(context.getApplicationContext())) {
            sf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        } else {
            sf = new SimpleDateFormat("hh:mm", Locale.getDefault());
        }
        return sf.format(calendar.getTime());
    }

    /**
     * 是否是24小时制
     *
     * @param context the context
     * @return true 是24小时制； false 是12小时制
     */
    public static boolean isCurrent24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }


    /**
     * Gets date format.
     *
     * @param context the context
     * @return the date format
     */
    public static String getDateFormat(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
    }

    /**
     * Sets date format.
     *
     * @param context the context
     * @param format  the format
     */
    public static void setDateFormat(Context context, String format) {
        Settings.System.putString(context.getContentResolver(), Settings.System.DATE_FORMAT
                , format);
    }

}
