package com.xhc.xhcnote.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.xhc.xhcnote.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class NoteSQLite extends SQLiteOpenHelper {

    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;

    public NoteSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public NoteSQLite(@Nullable Context context) {
        super(context,UtilDB.DATABASE_NAME,null,UtilDB.DATABASE_VERION);
        mContext = context;

        //增删改查
        mSQLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UtilDB.showCreateSql());
//        ToastUtil.ShowShort(mContext,"Create Succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        mSQLiteDatabase.execSQL("drop table if exists UserInfo");
    }

    /***
     * 添加数据
     * @param userContent
     * @param userYearTime
     * @return
     */
    public boolean insertDate(String userContent,String userYearTime){

        //方式1  SQL语句的方式
      /*  String stu_sql="insert into "+UtilDB.DATABASE_TABLE+"("+UtilDB.USER_CONTENT+","+UtilDB.USER_YEAR+","+UtilDB.USER_TIME+") values('"+userContent+"','"+userYear+"','"+userTime+"')";
        sqLiteDatabase.execSQL(stu_sql);
        return true;*/
        //方式2
        ContentValues contentValues=new ContentValues();
        contentValues.put(UtilDB.USER_CONTENT,userContent);
        contentValues.put(UtilDB.USER_YEAR_TIME,userYearTime);
        return mSQLiteDatabase.insert(UtilDB.DATABASE_TABLE,null,contentValues)>0;

    }

    /***
     * 删除数据
     * @param id
     * @return
     */
    public boolean deleteData(String id){


        /*String sql = "delete from "+UtilDB.DATABASE_TABLE+" where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/

        String sql=UtilDB.USER_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return mSQLiteDatabase.delete(UtilDB.DATABASE_TABLE,sql,contentValuesArray)>0;
    }

    /****
     * 修改数据
     * @param id
     * @param content
     * @param userYear
     * @return
     */
    public boolean updateData(String id,String content,String userYear){
      /*  String sql = "update "+UtilDB.DATABASE_TABLE+" set "+UtilDB.USER_CONTENT+" = '"+content+"',"
                +UtilDB.USER_YEAR+" = '"+userYear+"',"+UtilDB.USER_TIME+"='"+userTime+"' where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/
        ContentValues contentValues=new ContentValues();
        contentValues.put(UtilDB.USER_CONTENT,content);
        contentValues.put(UtilDB.USER_YEAR_TIME,userYear);
        String sql=UtilDB.USER_ID+"=?";
        String[] strings=new String[]{id};
        return mSQLiteDatabase.update(UtilDB.DATABASE_TABLE,contentValues,sql,strings)>0;
    }


    /***
     * 查询数据
     * @return
     */
    public List<UserInfo> query(){
        List<UserInfo> list=new ArrayList<UserInfo>();
        Cursor cursor=mSQLiteDatabase.query(UtilDB.DATABASE_TABLE,null,null,null,null,null,UtilDB.USER_YEAR_TIME+" desc");
        if (cursor!=null){
            while (cursor.moveToNext()){
                UserInfo userInfo=new UserInfo();
                userInfo.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(UtilDB.USER_ID))));
                userInfo.setUserContent(cursor.getString(cursor.getColumnIndex(UtilDB.USER_CONTENT)));
                userInfo.setUserYearTime(cursor.getString(cursor.getColumnIndex(UtilDB.USER_YEAR_TIME)));
                list.add(userInfo);
            }
        }
        return list;
    }

    /**
     * Raw query like list.
     *
     * @param key the key
     * @return the list
     */
    public List<UserInfo> rawQueryLike(String key) {
        List<UserInfo> list=new ArrayList<UserInfo>();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from "+ UtilDB.DATABASE_TABLE +" where content like ? " , new String[]{"%"+key+"%"});
        if (cursor != null){
            while (cursor.moveToNext()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(UtilDB.USER_ID))));
                userInfo.setUserContent(cursor.getString(cursor.getColumnIndex(UtilDB.USER_CONTENT)));
                userInfo.setUserYearTime(cursor.getString(cursor.getColumnIndex(UtilDB.USER_YEAR_TIME)));
                list.add(userInfo);
            }
        }
        return list;
    }


}
