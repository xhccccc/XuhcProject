package com.xuhc.fileoperations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class FilePathActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_path);

        findViewById(R.id.button1).setOnClickListener(this);
        tv1 = findViewById(R.id.tv1);
        findViewById(R.id.button2).setOnClickListener(this);
        tv2 = findViewById(R.id.tv2);
        findViewById(R.id.button3).setOnClickListener(this);
        tv3 = findViewById(R.id.tv3);
        findViewById(R.id.button5).setOnClickListener(this);
        tv4 = findViewById(R.id.tv4);
        findViewById(R.id.button5).setOnClickListener(this);
        tv5 = findViewById(R.id.tv5);
        findViewById(R.id.button6).setOnClickListener(this);
        tv6 = findViewById(R.id.tv6);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button1){
            tv1.setText(FileOperationsUtil.getCacheDirPath(this));
        } else if (id == R.id.button2){
            tv2.setText(FileOperationsUtil.getFilesDirPath(this));
        } else if (id == R.id.button3){
            tv3.setText(FileOperationsUtil.getExternalCacheDirPath(this));
        } else if (id == R.id.button4){
            //Deprecated
            tv4.setText(FileOperationsUtil.getExternalStorageDirectoryPath());
        } else if (id == R.id.button5){
            //Deprecated
            tv5.setText(FileOperationsUtil.getExternalStoragePublicDirectoryPath(Environment.DIRECTORY_DCIM));
        } else if (id == R.id.button6){
            String allPath = FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_MUSIC) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_PODCASTS) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_RINGTONES) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_ALARMS) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_NOTIFICATIONS) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_PICTURES) + "\n"
                    + FileOperationsUtil.getExternalFilesDirPath(this,Environment.DIRECTORY_MOVIES) + "\n";
            tv6.setText(allPath);
        }
    }
}