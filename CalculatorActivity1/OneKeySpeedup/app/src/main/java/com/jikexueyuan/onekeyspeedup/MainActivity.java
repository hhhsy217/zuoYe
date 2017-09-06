package com.jikexueyuan.onekeyspeedup;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Formatter;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        //获取后台运行的进程
        List<ActivityManager.RunningAppProcessInfo> ProcessInfos = activityManager.getRunningAppProcesses();
        //获取本项目的进程
        long beforeMen=getAvailMemory(this);

        //对系统中所有正在运行的进程进行迭代，如果进程名不是当前进程，则Kill掉
        for (int i=0;i< ProcessInfos.size();i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo=ProcessInfos.get(i);
            if (runningAppProcessInfo.importance>ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                String[]pkgList=runningAppProcessInfo.pkgList;
                for (int j = 0; j < pkgList.length; ++i) {
                    activityManager.killBackgroundProcesses(pkgList[i]);
                }
            }
        }

        long afterMen = getAvailMemory(this);//加速之后的系统内存
        Toast.makeText(getApplicationContext(), "为您节省了" +(afterMen-beforeMen), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "为您节省了" +Formatter.formatFileSize(this,afterMen-beforeMen), Toast.LENGTH_SHORT).show();
        //试了注释上面的，在Formatter.formatFileSize处报错，
        finish();
    }

    /*获取可用内存大小*/
    private long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem/1024*1024;//返回值以 M 为单位
    }
}
