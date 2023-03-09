package com.xuhc.getcontent;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class GetContentActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout content;
    private Button btGetContent, btPick;

    public static final String[] STORAGE_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.MANAGE_DOCUMENTS};
    ActivityResultLauncher<String[]> requestPermissionActivityResultLauncherMultiple;
    ActivityResultLauncher<Intent> intentActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_content);
        initView();
        addListener();
        addActivityResult();
    }

    private void initView() {
        btGetContent = findViewById(R.id.bt_get_content);
        btPick = findViewById(R.id.bt_pick);
        content = findViewById(R.id.content);
    }

    private void addListener() {
        btGetContent.setOnClickListener(this);
        btPick.setOnClickListener(this);
    }

    private void addActivityResult() {
        //多个申请回调
        ActivityResultContracts.RequestMultiplePermissions requestMultiplePermission = new ActivityResultContracts.RequestMultiplePermissions();
        requestPermissionActivityResultLauncherMultiple = registerForActivityResult(requestMultiplePermission, result -> {
            Log.d("xhccc", "result: " + result);
        });
        requestPermissionActivityResultLauncherMultiple.launch(STORAGE_PERMISSIONS);
        ActivityResultContracts.StartActivityForResult mStartActivityForResult = new ActivityResultContracts.StartActivityForResult();
        intentActivityResultLauncher = registerForActivityResult(mStartActivityForResult, result -> {
            Log.d("xhccc", "result: " + result.toString());
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                Uri uri = result.getData().getData();
                Log.d("xhccc", "uri: " + uri);
                if (uri != null) {
                    content.removeAllViews();
                    String path = getContentPath(uri);
                    if (path != null) {
                        addImg(path);
                    } else {
                        addImgByFileDescriptor(uri);
                    }

                } else {
                    List<String> contentList = new ArrayList<>();
                    ClipData clipData = result.getData().getClipData();
                    Log.d("xhccc", "clipData.toString(): " + clipData.toString());
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri uri1 = clipData.getItemAt(i).getUri();
                        String path = getContentPath(uri1);
                        if (path != null) {
                            contentList.add(path);
                        }
                    }
                    content.removeAllViews();
                    if (contentList.size() > 0) {
                        for (int i = 0; i < contentList.size(); i++) {
                            addImg(contentList.get(i));
                        }
                    } else {
                        //一般用这种
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri1 = clipData.getItemAt(i).getUri();
                            addImgByFileDescriptor(uri1);
                        }
                    }

                }

            }
        });
    }

    private String getContentPath(Uri uri) {
        try {
            if (uri != null) {
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                    }
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addImg(String path) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 15, 0, 1);
        Glide.with(GetContentActivity.this).load(path).into(imageView);
        content.addView(imageView, layoutParams);
    }

    private void addImgByFileDescriptor(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            //mode 只能"r", "rw"要系统应用
            ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 15, 0, 1);
            Glide.with(GetContentActivity.this).load(bitmap).into(imageView);
            content.addView(imageView, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_get_content) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentActivityResultLauncher.launch(intent);
        } else if (v.getId() == R.id.bt_pick) {
            Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intentActivityResultLauncher.launch(chooseIntent);
        }
    }
}