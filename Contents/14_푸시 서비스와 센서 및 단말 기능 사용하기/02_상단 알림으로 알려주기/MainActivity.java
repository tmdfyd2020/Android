package org.techtown.noti;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    private static String CHANNEL_ID2 = "channel2";
    private static String CHANNEL_NAME2 = "Channel2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);  // 알림 띄우기 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();  // Go to down !!
            }
        });

        Button button2 = findViewById(R.id.button2);  // 알림 띄우고 클릭하기 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti2();  // Go to down !!
            }
        });

    }

    // 알림은 NotificationManager 시스템 서비스를 이용해 화면 상단에 띄울 수 있다.
    // 알림을 띄우려면 Notification 객체를 만들어야 하는데 이 객체는 NotificationCompat.Builder 객체를 이용해서 만든다.

    public void showNoti1() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  // NotificationManager 객체 참조하기

        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 오레오 이후 버전에서는 알림 채널이 지정되어야 하며, 채널은 createNotificationChannel() 메서드를 이용해서 생성
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));

                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            }
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setContentTitle("간단 알림");  // 알림 제목
        builder.setContentText("알림 메시지입니다.");  // 알림 메시지를 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_view);  // 아이콘 설정에 사용
        Notification noti = builder.build();  // build() 메서드를 호출하면 Notification 객체가 생성된다.

        manager.notify(1, noti);  // 상단 알림 띄우기
    }

    public void showNoti2() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 오레오 이후 버전에서는 알림 채널이 지정되어야 하며, 채널은 createNotificationChannel() 메서드를 이용해서 생성
            if (manager.getNotificationChannel(CHANNEL_ID2) == null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID2, CHANNEL_NAME2, NotificationManager.IMPORTANCE_DEFAULT
                ));

                builder = new NotificationCompat.Builder(this, CHANNEL_ID2);
            }
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent : Intent와 유사하지만 시스템에서 대기하는 역할. 그리고 원하는 상황이 만들어졌을 때 시스템에 의해 해석되고 처리된다.

        builder.setContentTitle("간단 알림");  // 알림 제목
        builder.setContentText("알림 메시지입니다.");  // 알림 메시지를 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_view);  // 아이콘 설정에 사용
        builder.setAutoCancel(true);  // 알림을 클릭했을 때 자동으로 알림 표시를 삭제하라는 설정
        builder.setContentIntent(pendingIntent);  // 빌더에 PendingIntent 객체 설정하기

        Notification noti = builder.build();

        manager.notify(2, noti);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        // InboxStyle : 여러 줄의 텍스트 -> 이메일 목록을 보여주거나 할 때 사용
        // MessagingStyle : 여러 줄의 메시지를 보여준다. -> 시간까지 함께 보여줄 수 있어 카카오톡과 같은 메시징 앱에서 사용한다.
        // BigPictureStyle : 큰 이미지를 보여주고 싶을 때 사용한다.

        style.bigText("많은 글자들입니다");
        style.setBigContentTitle("제목입니다");
        style.setSummaryText("요약 글입니다");

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "channel3")
                .setContentTitle("알림 제목")
                .setContentText("알림 내용")
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style);

    }

}
