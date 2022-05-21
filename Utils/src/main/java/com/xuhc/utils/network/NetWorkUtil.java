package com.xuhc.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetWorkUtil {
    private static final String TAG = NetWorkUtil.class.getSimpleName();

    public static final String ETHERNET_NAME = "eth0";

    private final static int NETWORK_DISCONNECT = -1;
    private final static int WIFI = 0;
    private final static int ETHERNET = 1;

    public NetWorkUtil() {

    }

    /**
     * 获取当前系统的网络类型
     *
     * @param context
     * @return 1：ETHERNET；0：WIFI；-1：NETWORK_DISCONNECT
     * need android.permission.ACCESS_NETWORK_STAT
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = mConnectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(network);
        if (networkCapabilities == null) {
            return NETWORK_DISCONNECT;
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
            return WIFI;
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return ETHERNET;
        }
        return NETWORK_DISCONNECT;
    }

    /**
     * 判断当前系统是否已联网
     *
     * @param context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        boolean netState = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null) {
                netState = networkInfo.isConnected();
            }

        }
        return netState;
    }


    public static String getNetIp(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                return getEth0Ip();
            } else {
                return getWifiIp(context);
            }

        }
        return "";
    }

    /**
     * 获取当前网络的IP地址
     *
     * @param context
     * @return String
     */
    private static String getWifiIp(Context context) {
        try {
            Enumeration<NetworkInterface> enumerationNi = NetworkInterface.getNetworkInterfaces();
            while (enumerationNi.hasMoreElements()) {
                NetworkInterface networkInterface = enumerationNi.nextElement();
                Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses();

                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    private static String getEth0Ip() {
        try {
            Enumeration<NetworkInterface> enNetworkInterface = NetworkInterface.getNetworkInterfaces(); //获取本机所有的网络接口
            while (enNetworkInterface.hasMoreElements()) {  //判断 Enumeration 对象中是否还有数据
                NetworkInterface networkInterface = enNetworkInterface.nextElement();   //获取 Enumeration 对象中的下一个数据
                if (!networkInterface.isUp()) { // 判断网口是否在使用
                    continue;
                }
                if (!ETHERNET_NAME.equals(networkInterface.getDisplayName())) { // 网口名称是否和需要的相同
                    continue;
                }
                Enumeration<InetAddress> enInetAddress = networkInterface.getInetAddresses();   //getInetAddresses 方法返回绑定到该网卡的所有的 IP 地址。
                while (enInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enInetAddress.nextElement();
                    if (inetAddress instanceof Inet4Address) {  //判断是否未ipv4
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            return "";
        }
        return "";
    }
}
