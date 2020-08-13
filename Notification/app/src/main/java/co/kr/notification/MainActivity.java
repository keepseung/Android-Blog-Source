package co.kr.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button button;
    Notification notification;
    NotificationManager notificationManager;
    int ad =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        makeNotiChannel();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    private void makeNotiChannel() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 7버전 이하인 경우에 대한 예외처리가 없다...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 채널 아이디, 이름, 중요도 수준을(긴급=알림음 울리고 헤드업 알림 표시됨) 정함
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(getString(R.string.channel_desc));
            notificationChannel.enableLights(true);
            // 알림 색상을 정함
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void sendNotification(){

        // 새로운 스레이일 경우에만 시작한다.
        if(thread.getState() == Thread.State.NEW){
            thread.start();

        }else{
            notificationManager.notify(1, notification);
        }
        ad +=1;
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notiIntent = new Intent(MainActivity.this, MainActivity.class);
            // 새로운 테스크를 생성해 그 안에 액티비티를 추가할 때 사용함
            // 기존에 해당 테스크가 없으면 새로운 task를 만들면서 생성함
            notiIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notiIntent.putExtra("data", "data");
            // getActivity -> 액티비티를 시작하는 인텐트를 생성함
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                notification = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.channel_id))
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(getString(R.string.noti_title))
                        .setContentText(getString(R.string.noti_content)+ad)
                        .setLargeIcon(Glide.with(MainActivity.this).asBitmap().load("https://cdn.pixabay.com/photo/2020/01/02/10/52/monk-4735530_960_720.jpg").into(255,255).get())
                        // 유저가 알림을 클릭하면 알림 리스트에서 자동으로 사라지게 함
                        .setAutoCancel(true)
                        .setChannelId(getString(R.string.channel_id))
                        .setContentIntent(pendingIntent)
                        .build();

                if (notificationManager != null) {
                    // 알림을 상태바에 보이게 한다.
                    notificationManager.notify(1, notification);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    });



}