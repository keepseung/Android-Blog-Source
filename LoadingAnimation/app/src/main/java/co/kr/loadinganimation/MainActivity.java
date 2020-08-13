package co.kr.loadinganimation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FrameLayout loadingLayout, popupBackgroundLayout, loadingBackgroundLayout;
    Animation popupBackgroudFadeInAnim, popupBackgroudFadeOutAnim, loadingBackgroudFadeInAnim, loadingBackgroudFadeOutAnim;

    public static int OPEN = 0;
    public static int CLOSE = 1;

    // 메시지 종류를 식별하기 위해, what 변수에 전달할 값을 상수로 정의.
    private final int NORMAL_LOADING = 0 ;

    Button popbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로딩을 위한 뷰를 초기화 한다.
        initloading();
    }

    public void initloading() {
        popbt = findViewById(R.id.popbt);

        // 로딩
        loadingLayout = findViewById(R.id.view_loading);
        // 팝업 백그라운드
        popupBackgroundLayout = findViewById(R.id.popupBackgroundLayout);
        // 팝업 백그라운드
        loadingBackgroundLayout = findViewById(R.id.loadingBackgroundLayout);

        // 팝업 시작 애니메이션에 대한 설정
        popupBackgroudFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        popupBackgroudFadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        popupBackgroudFadeInAnim.setAnimationListener(new kr.onelive.listener.ViewAnimationListener(popupBackgroundLayout, OPEN));
        popupBackgroudFadeOutAnim.setAnimationListener(new kr.onelive.listener.ViewAnimationListener(popupBackgroundLayout, CLOSE));

        // 로딩 시작 애니메이션을 어떤 파일로 할 것인지(애니메이션 시작하고 종료할 때까지 알파값의 변화)
        // 안드에서 android.R.anim.fade_in 나타나는 기본 파일을 제공해줌
        loadingBackgroudFadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        // 로딩 종료 애니메이션을 어떤 파일로 할 것인지(애니메이션 시작하고 종료할 때까지 알파값의 변화)
        // 안드에서 android.R.anim.fade_out 나타나는 기본 파일을 제공해줌
        loadingBackgroudFadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        // 로딩 시작 애니메이션은 보이게 하기
        loadingBackgroudFadeInAnim.setAnimationListener(new LoadingAnimationListener(this,loadingBackgroundLayout, OPEN));
        // 로딩 종료 애니메이션은 안보이게 하기
        loadingBackgroudFadeOutAnim.setAnimationListener(new LoadingAnimationListener( this,loadingBackgroundLayout, CLOSE));
    }


    @Override
    protected void onResume() {
        super.onResume();

        // 로딩을 시작한다.
        startloading();
        // 3초 이후 로딩을 멈추는 스레드를 작동시킨다..
        new LoadingThread(NORMAL_LOADING).start();

        // 팝업 버튼 클릭시
        popbt.setOnClickListener(v -> {
            // 다이어로그 생성될 때 백그라운드 검게 만드는 애니메이션 활성화
            popupFadeInAnimation();

            // 다이어로그 생성됨
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("애니메이션 보기");
            builder.setMessage("다이어로그 열고 닫을 때 애니메이션 보기");
            builder.setPositiveButton("예",
                    (dialog, which) ->
                            // 다이어로그 사라질 때 백그라운드 애니메이션 활성화
                            popupFadeOutAnimation());
            builder.show();

        });
    }

    private final Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NORMAL_LOADING:
                    // 일반 로딩의 경우
                    quitLoading();
                    break;
            }
        }
    };

    private void quitLoading() {
        // 로딩 사라지는 애니메이션 시작함
        loadingBackgroundLayout.startAnimation(loadingBackgroudFadeOutAnim);
        // 로딩 이미지 보여주는 레이아웃은 안보이게 함
        loadingLayout.setVisibility(View.GONE);
    }

    public void popupFadeInAnimation() {
        if (popupBackgroundLayout.getVisibility() == View.GONE) {
            // 팝업 시작시 배경이 어두워지는 애니메이션 시작함
            popupBackgroundLayout.startAnimation(popupBackgroudFadeInAnim);
        }
    }

    public void popupFadeOutAnimation() {
        if (popupBackgroundLayout.getVisibility() == View.VISIBLE) {
            // 팝업 종료시 배경이 밝아지는 애니메이션 시작함
            popupBackgroundLayout.startAnimation(popupBackgroudFadeOutAnim);
        }
    }

    private void startloading() {
        // 로딩시작시 배경화면 어두워지는 애니메이션 시작함
        loadingBackgroundLayout.startAnimation(loadingBackgroudFadeInAnim);
        ImageView loadingImageView = findViewById(R.id.loadingImageView);
        // loadingImageView.getBackground() -> 미리 준비된 10개의 로딩 이미지들
        final AnimationDrawable loadingAnim = (AnimationDrawable) (loadingImageView.getBackground());
        // post 함수로 인해 startloading을 별도의 스레드에서 실행시켜도 된다.
        // UI 스레드에서 작업을 할 수 있도록 지정하는 것
        loadingImageView.post(loadingAnim::start);
    }

    class LoadingThread extends Thread{
        Handler handler= mHandler;
        int loadingType;

        LoadingThread(int loadingType){
            this.loadingType = loadingType;
        }

        @Override
        public void run() {

            try {
                // 3초간 기다린다.
                Thread.sleep(3000);
                // obtain a message.
                Message message = handler.obtainMessage();
                message.what = loadingType;

                // send message object.
                handler.sendMessage(message) ;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}