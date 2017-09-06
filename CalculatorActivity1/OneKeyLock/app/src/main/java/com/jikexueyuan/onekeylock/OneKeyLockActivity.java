package com.jikexueyuan.onekeylock;


import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/16.
 */
public class OneKeyLockActivity extends AppCompatActivity {
    private DevicePolicyManager devicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

       if (devicePolicyManager.isAdminActive(new ComponentName(getApplicationContext(), DeviceManagerBC.class))) {
           devicePolicyManager.lockNow();
           finish();
       }else {
           Toast.makeText(this,"未有系统权限，请先获取权限",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
