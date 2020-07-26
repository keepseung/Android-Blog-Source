package com.example.appupdate_check;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int UPDATE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void updaterequest() {
        // 앱 업데이트 여부 확인하고 업데이트 되있으면 업데이트 요청한다.

        // AppUpdateManager 객체 만들기
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        // 업데이트를 확인하기 위한 인텐트 겍체를 만든다.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // 업데이트 가능한지 확인한다.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            // 사용 가능한 업데이트가 있는 경우
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d(TAG, "updaterequest:업데이트할 수 있음 ");
                // 사용자에게 업데이트를 요청한다.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // 업데이트 요청을 위한 현재 화면
                            this,
                            // Include a request code to later monitor this update request.
                            UPDATE_REQUEST_CODE);
                    Log.d(TAG, "updaterequest:업데이트 요청함 ");
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else{
                Log.d(TAG, "updaterequest:업데이트할 것이 없음 ");
            }
        });
    }
}
