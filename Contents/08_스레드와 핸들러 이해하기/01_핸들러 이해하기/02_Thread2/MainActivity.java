package org.techtown.thread;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
            }
        });

        handler = new MainHandler();
    }

    class BackgroundThread extends Thread {
        int value = 0;

        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {}

                value += 1;
                Log.d("Thread", "value : " + value);

                Message message = handler.obtainMessage(); // 새로 만든 스레드 객체에서 수행한 작업의 결과가 나왔을 때 핸들러 객체의 obtainMessage()로 메시지 참조한 후
                Bundle bundle = new Bundle();  // Bundle : 여러 가지 타입의 값을 저장하는 Map 클래스, 아무거나 포장할 수 있는 상자와 유사하다.
                bundle.putInt("value", value);
                message.setData(bundle);

                handler.sendMessage(message);  // sendMessage() 메서드를 이용해 메시지 큐에 넣는다.
            }
        }
    }

    class MainHandler extends Handler {
        /* 핸들러 안에서 전달받은 메시지 객체 처리하기 */
        @Override
        public void handleMessage(Message msg) {  // 이 메서드를 다시 정의하면 메시지가 메인 스레드에서 수행될 때 필요한 기능을 넣어둘 수 있다.
            // 이렇게 정의한 핸들러는 onCreate() 메서드에서 액티비티가 초기화될 때 new 연산자를 이용해 객체로 만들어진다.
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("value 값 : " + value);
        }
    }

}
