package com.xuhc.contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ContentResolverActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver);

        findViewById(R.id.bt_query_note).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_query_note){
            queryNote();
        }
    }

    //获取xuhcNote里的内容，通过内容提供者，小demo练习，只写了查询
    private void queryNote() {
        Uri uri = Uri.parse("content://com.xhc.xhcnote.provider/UserInfo");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String time = cursor.getString(cursor.getColumnIndex("useryear"));
                Log.d("xhccc_NoteContent","content: " + content);
                Log.d("xhccc_Note_Time","contentTime: " + time);
            }
            cursor.close();
        }
    }
}