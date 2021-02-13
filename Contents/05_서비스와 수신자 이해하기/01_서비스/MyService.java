package org.techtown.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate() 호출됨."); // Logcat 창에서 확인할 수 있는 메시지 출력
        // 로그 출력을 위해서 첫 번째 파라미터로 로그를 구분할 수 있는 문자열 전달 : Tag
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  // 서비스로 전달된 인텐트 객체를 처리하는 함수
        Log.d(TAG, "onStartCommand() 호출됨.");

        if (intent == null) { // 서비스는 시스템에 의해 자동으로 다시 시작될 수 있기 때문에 Intent가 null인 경우를 검사한다.
            return Service.START_STICKY; // null이면 서비스가 비정상 종료되었다는 의미이므로 시스템이 자동으로 재시작한다.
        } else {
            processCommand(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent) {
        String command = intent.getStringExtra("command"); // 인텐트에서 부가데이터 가져오기
        String name = intent.getStringExtra("name");
        Log.d(TAG, "command : " + command + ", name : " + name);

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {};

            Log.d(TAG, "Waiting " + i + " seconds.");
        }

        // 액티비티를 띄우기 위한 인텐트 객체 만들기
        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);

        // 인텐트에 플래그 추가하기
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
          // 서비스에서 startActivity() 함수를 호출할 때는 새로운 테스크를 생성하도록 FLAG_ACTIVITY_NEW_TASK 플래그를 인텐트에 추가해야 한다.
          // 서비스는 화면이 없기 때문에 화면이 없는 서비스에서 화면이 있는 액티비티를 띄우려면 새로운 테스크를 만들어야 하기 때문이다.
          // MainActivity 객체가 이미 메모리에 만들어져 있을 때 재사용하도록 FLAG_ACTIVITY_SINGLE_TOP과 FLAG_ACTIVITY_CLEAR_TOP 플래그도 추가한다.
        showIntent.putExtra("command", "show");
        showIntent.putExtra("name", name + " from service.");

        startActivity(showIntent); // 메인 액티비티 쪽으로 인텐트 객체가 전달됨
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 바인딩 : 서비스가 서버 역할을 하면서 액티비티와 연결될 수 있도록 만드는 것
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
