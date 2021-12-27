package com.xuhc.fileoperations;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;

public class FileActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        mEditText = (EditText) findViewById(R.id.file_edit);

        String inputcontent = load();
        if (!TextUtils.isEmpty(inputcontent)){
            mEditText.setText(inputcontent);
            mEditText.setSelection(inputcontent.length());
            Toast.makeText(this,"读取文件成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String input = mEditText.getText().toString();
        save(input);
    }

    public void save(String input){
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;
        try {
            outputStream = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String load(){
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            inputStream = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
