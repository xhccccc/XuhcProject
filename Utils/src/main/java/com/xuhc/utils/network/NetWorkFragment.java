package com.xuhc.utils.network;

import android.net.Network;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuhc.basemodule.BaseFragment;
import com.xuhc.utils.R;
import com.xuhc.utils.log.LogUtil;

public class NetWorkFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = NetWorkFragment.class.getSimpleName();

    private Button btDoNetWork;
    private TextView tvContent;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_network;
    }

    @Override
    public void initView(View view) {
        btDoNetWork = view.findViewById(R.id.bt_network_utils_fragment);
        tvContent = view.findViewById(R.id.tv_network_utils_fragment);
    }

    @Override
    public void initFocus() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onNetWorkAvailable(Network network) {
        super.onNetWorkAvailable(network);
        LogUtil.d(TAG, "onNetWorkAvailable: ");
    }

    @Override
    public void addListener() {
        btDoNetWork.setOnClickListener(this);
    }

    @Override
    public void detach() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void pageSelect() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_network_utils_fragment) {
            Log.d("xhccc", "test");
            String content = "getNetworkType: " + String.valueOf(NetWorkUtil.getNetworkType(mContext)) + "\n"
                    + "isNetworkConnected: " + NetWorkUtil.isNetworkConnected(mContext) + "\n"
                    + "getNetIp: " + NetWorkUtil.getNetIp(mContext) + "\n"
                    + "getLocalIpAddress: " + NetWorkUtil.getLocalIpAddress() + "\n";
            tvContent.setText(content);
        }
    }
}
