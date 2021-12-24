package com.xuhc.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//这里主要是简单是fragment应用
//更多有关fragment的使用会在viewpager等结合使用
public class FragmentTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        replaceFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            replaceFragment(new AnotherRightFragment());
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.right_fragment,fragment);

        //这个用于吧fragment加入回退栈，即使用了后，进行返回键会关闭fragment
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
