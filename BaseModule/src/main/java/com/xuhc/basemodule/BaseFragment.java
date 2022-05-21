package com.xuhc.basemodule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected BaseActivity mContext;
    private NetworkChangeCallback mNetworkCallback;
    private ConnectivityManager mConnectivityManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = (BaseActivity) getActivity();
        View view = inflater.inflate(setLayoutId(), container, false);
        initView(view);
        initFocus();
        initData();
        addListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConnectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkCallback = new NetworkChangeCallback();
        mConnectivityManager.registerDefaultNetworkCallback(mNetworkCallback);

        mNetworkCallback.setOnNetWorkChangeListener(new NetworkChangeCallback.OnNetWorkChangeListener() {
            @Override
            public void onAvailable(Network network) {
//                LogUtil.d(TAG, "onAvailable: ");
//                if (NetWorkUtil.isNetworkConnected(requireContext())) {
//                    onNetWorkAvailable(network);
//                }
                onNetWorkAvailable(network);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detach();
        mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public abstract int setLayoutId();

    public abstract void initView(View view);

    public abstract void initFocus();

    public abstract void initData();

    public abstract void addListener();

    public abstract void detach();

    public abstract void resume();

    public abstract void pause();

    public abstract void pageSelect();

    public void onNetWorkAvailable(Network network) {

    }

}
