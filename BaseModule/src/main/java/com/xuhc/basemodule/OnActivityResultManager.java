package com.xuhc.basemodule;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class OnActivityResultManager {

    private WeakReference<Activity> mActivity;
    private HashMap<Integer,OnActivityResultCallBack> mCallBackMap = new HashMap<>();

    public OnActivityResultManager(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }
    

    private Activity getActivity(){
        return mActivity.get();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        OnActivityResultCallBack callBack = mCallBackMap.remove(requestCode);
        if (callBack != null){
            callBack.onActivityResultCallBack(requestCode,resultCode,data);
        }
    }


    public void startActivityForResult(Intent intent,int requestCode,OnActivityResultCallBack callBack){
        mCallBackMap.put(requestCode,callBack);
        getActivity().startActivityForResult(intent,requestCode);
    }


    public interface OnActivityResultCallBack{
        void onActivityResultCallBack(int requestCode,int resultCode,Intent data);
    }

}