package com.example.phonenum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 22;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (chkPermission()) {
            // 휴대폰 정보는 TelephonyManager 를 이용
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            // READ_PHONE_NUMBERS 또는 READ_PHONE_STATE 권한을 허가 받았는지 확인
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            Log.d(TAG, "음성통화 상태 : [ getCallState ] >>> " + tm.getCallState());
            Log.d(TAG, "데이터통신 상태 : [ getDataState ] >>> " + tm.getDataState());
            Log.d(TAG, "전화번호 : [ getLine1Number ] >>> " + tm.getLine1Number());
            Log.d(TAG, "통신사 ISO 국가코드 : [ getNetworkCountryIso ] >>> "+tm.getNetworkCountryIso());
            Log.d(TAG, "통신사 ISO 국가코드 : [ getSimCountryIso ] >>> "+tm.getSimCountryIso());
            Log.d(TAG, "망사업자 MCC+MNC : [ getNetworkOperator ] >>> "+tm.getNetworkOperator());
            Log.d(TAG, "망사업자 MCC+MNC : [ getSimOperator ] >>> "+tm.getSimOperator());
            Log.d(TAG, "망사업자명 : [ getNetworkOperatorName ] >>> "+tm.getNetworkOperatorName());
            Log.d(TAG, "망사업자명 : [ getSimOperatorName ] >>> "+tm.getSimOperatorName());
            Log.d(TAG, "SIM 카드 상태 : [ getSimState ] >>> "+tm.getSimState());

            // 유니크한 단말 번호 >>> Android ID 사용
            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            Log.d(TAG, "Android_ID >>> "+android_id);
        }

    }

    public boolean chkPermission() {
        // 위험 권한을 모두 승인했는지 여부
        boolean mPermissionsGranted = false;
        String[] mRequiredPermissions = new String[1];
        // 승인 받기 위한 권한 목록
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;

        }else{
            mRequiredPermissions[0] = Manifest.permission.READ_PRECISE_PHONE_STATE;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 필수 권한을 가지고 있는지 확인한다.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);

            // 필수 권한 중에 한 개라도 없는 경우
            if (!mPermissionsGranted) {
                // 권한을 요청한다.
                ActivityCompat.requestPermissions(MainActivity.this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }

        return mPermissionsGranted;
    }


    public boolean hasPermissions(String[] permissions) {
        // 필수 권한을 가지고 있는지 확인한다.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // 권한을 모두 승인했는지 여부
            boolean chkFlag = false;
            // 승인한 권한은 0 값, 승인 안한 권한은 -1을 값으로 가진다.
            for (int g : grantResults) {
                if (g == -1) {
                    chkFlag = true;
                    break;
                }
            }

            // 권한 중 한 개라도 승인 안 한 경우
            if (chkFlag){
                chkPermission();
            }
        }
    }
}