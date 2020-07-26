package com.example.network_check;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 네트워크 연결 상태 확인
        getConnectivityStatus(getApplicationContext());
    }

    public void getConnectivityStatus(Context context) {
        // 네트워크 연결 상태 확인하기 위한 ConnectivityManager 객체 생성
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {

            // 기기가 마시멜로우 버전인 Andorid 6 이상인 경우
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // 활성화된 네트워크의 상태를 표현하는 객체
                NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());

                if (nc != null) {

                    if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Toast.makeText(context, "와이파이 연결됨", Toast.LENGTH_SHORT).show();
                    } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Toast.makeText(context, "셀룰러 통신 사용", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "인터넷 연결 안됨", Toast.LENGTH_SHORT).show();
                }

            } else {

                // 기기 버전이 마시멜로우 버전보다 아래인 경우
                // getActiveNetworkInfo -> API level 29에 디플리케이트 됨
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        Toast.makeText(context, "와이파이 연결됨", Toast.LENGTH_SHORT).show();
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Toast.makeText(context, "셀룰러 통신 사용", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
