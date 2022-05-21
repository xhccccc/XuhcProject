package com.xuhc.basemodule;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;

public class NetworkChangeCallback extends ConnectivityManager.NetworkCallback {

    private static final String TAG = NetworkChangeCallback.class.getSimpleName();

    private OnNetWorkChangeListener mOnNetWorkChangeListener;

    public void setOnNetWorkChangeListener(OnNetWorkChangeListener onNetWorkChangeListener) {
        mOnNetWorkChangeListener = onNetWorkChangeListener;
    }

    public interface OnNetWorkChangeListener {
        void onAvailable(Network network);
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
//        LogUtil.d(TAG, "onAvailable: ");
        if (mOnNetWorkChangeListener != null) {
            mOnNetWorkChangeListener.onAvailable(network);
        }
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
//        LogUtil.d(TAG, "onLost: ");
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
//        LogUtil.d(TAG, "onCapabilitiesChanged: ");
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
//        LogUtil.d(TAG, "onUnavailable: ");
    }
}