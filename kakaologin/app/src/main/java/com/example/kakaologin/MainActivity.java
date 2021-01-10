package com.example.kakaologin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private Button loginV1, logout;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginV1 = findViewById(R.id.loginV1);
        logout = findViewById(R.id.logout);


        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        loginV1.setOnClickListener(v -> {
            if (Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.d(TAG, "onClick: 로그인 세션살아있음");
                // 카카오 로그인 시도 (창이 안뜬다.)
                sessionCallback.requestMe();
            } else {
                Log.d(TAG, "onClick: 로그인 세션끝남");
                // 카카오 로그인 시도 (창이 뜬다.)
                session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
            }
        });

        logout.setOnClickListener(v -> {
            Log.d(TAG, "onCreate:click ");
            UserManagement.getInstance()
                    .requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            super.onSessionClosed(errorResult);
                            Log.d(TAG, "onSessionClosed: "+errorResult.getErrorMessage());

                        }
                        @Override
                        public void onCompleteLogout() {
                            if (sessionCallback != null) {
                                Session.getCurrentSession().removeCallback(sessionCallback);
                            }
                            Log.d(TAG, "onCompleteLogout:logout ");
                        }
                    });
        });

        // 카카오 개발자 홈페이지에 등록할 해시키 구하기
//        getHashKey();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

}