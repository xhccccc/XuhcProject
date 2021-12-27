package com.xuhc.fileoperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class FileOperationsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operations);

        findViewById(R.id.path_activity).setOnClickListener(this);
        findViewById(R.id.file_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.path_activity){
            startActivity(new Intent(this,FilePathActivity.class));
        } else if (id == R.id.file_activity){
            startActivity(new Intent(this,FileActivity.class));
        }
    }
}