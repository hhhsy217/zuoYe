package com.example.administrator.xiaohw;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by zh on 2017/4/8.
 */

public class Utils {

    /*
       函数名：checkNetworkState
       描述：检查当前网络是否连接正常
       参数：context
       返回值：true：网络连接正常 false：无网络连接
    */
    public static boolean checkNetworkState(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager.getActiveNetworkInfo() != null) {
                return manager.getActiveNetworkInfo().isAvailable();
            }
        }
        return false;
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
//            //1.判断是否有网络连接
//            boolean networkAvailable = networkInfo.isAvailable();
//            //2.获取当前网络连接的类型信息
//            int networkType = networkInfo.getType();
//            if (ConnectivityManager.TYPE_WIFI == networkType) {
//                //当前为wifi网络
//                System.out.println("----------当前为wifi网络-----------");
//            } else if (ConnectivityManager.TYPE_MOBILE == networkType) {
//                //当前为mobile网络
//                System.out.println("----------当前为mobile网络-----------");
//            } else {
//                System.out.println("----------没有网络连接-----------");
//            }
//            return networkAvailable;  //返回网络状态
//        }
//        return false;
    }
}