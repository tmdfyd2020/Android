package org.techtown.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int value = 0;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 스레드 객체 생성하고 시작시키기 */
                // 버튼을 누르면 스레드가 동작하고 value 변수의 값은 스레드에 의해 1초마다 1씩 증가한다.
                BackgroundThread thread = new BackgroundThread();
                thread.start();  // start() 메서드를 호출하면 스레드가 시작된다.
                // 스레드를 시작시키면 그 안에 run() 메서드가 실행되고, run() 메서드 안에서는 반복문을 돌면서 1초마다 value 값을 증가시킨다.
            }
        });

    }

    class BackgroundThread extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {}

                value += 1;
                Log.d("Thread", "value : " + value);

                textView.setText("value 값 : " + value);  // 스레드 안에서 텍스트뷰의 setText() 메서드 호출하기
            }
        }
    }

}
/*
But!!
소스 코드에는 에러 표시가 없지만 앱을 실행하면 앱이 정상적으로 실행되지 못하고 에러가 발생한다.
BackgroundThread 객체에서 UI 객체를 직접 접근했다는 것을 말하고 있다. 결국 메인 스에드에서 관리하는 UI 객체는
우리가 직접 만든 스레드 객체에서듸 접근할 수 없다는 뜻이다.
이를 해결하기 위해서는 핸들러를 사용해야 한다.
 */
