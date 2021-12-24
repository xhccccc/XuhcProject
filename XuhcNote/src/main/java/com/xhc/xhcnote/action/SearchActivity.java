package com.xhc.xhcnote.action;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;

import com.xhc.xhcnote.BaseActivity;
import com.xhc.xhcnote.R;
import com.xhc.xhcnote.adapter.NoteAdapter;
import com.xhc.xhcnote.bean.UserInfo;
import com.xhc.xhcnote.tool.NoteSQLite;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchActivity extends BaseActivity implements View.OnClickListener,NoteAdapter.OnItemClickListener {

    private EditText et_search;
    private String searchText;
    private ImageView iv_search_cancel;
    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private NoteSQLite mNoteSQLite;

    private List<UserInfo> mUserInfoList = new ArrayList<>();

    @Override
    protected int getRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search_cancel = (ImageView) findViewById(R.id.iv_search_cancel);
        mRecyclerView = (RecyclerView) findViewById(R.id.recy_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        mRecyclerView.setLayoutAnimation(controller);
        mNoteSQLite = new NoteSQLite(this);
        setAdapter(searchText);
    }

    private void setAdapter(String text){
        mUserInfoList = mNoteSQLite.rawQueryLike(text);
        mNoteAdapter = new NoteAdapter(this,mUserInfoList);
        mNoteAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mNoteAdapter);
    }

    @Override
    protected void setListener() {
        et_search.addTextChangedListener(mTextWatcher);
        iv_search_cancel.setOnClickListener(this);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() == 0){
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                searchText = et_search.getText().toString();
                setAdapter(searchText);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_search_cancel) {
            et_search.setText("");
        }
    }

    @Override
    public void OnItemClick(int posi) {
        UserInfo userInfo = mUserInfoList.get(posi);
        Intent intent = new Intent(SearchActivity.this,AddActivity.class);
        intent.putExtra("id",userInfo.getId());
        intent.putExtra("content",userInfo.getUserContent());
        intent.putExtra("time",userInfo.getUserYearTime());
        startActivityForResult(intent,1);
//        ToastUtil.ShowShort(this,"posi: "+ posi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == 2){
                initData();
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(2);
        super.onBackPressed();
    }
}
