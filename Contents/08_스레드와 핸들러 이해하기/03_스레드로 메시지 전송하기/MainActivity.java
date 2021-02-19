package org.techtown.looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    ProcessThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Message 객체에 문자열을 포함시켜 전송하기 위해서 obj 변수에 문자열을 할당한다. */
                String input = editText.getText().toString();
                Message message = Message.obtain();  // Message 객체는 Message.obtain() 메서드를 이용해서 참조할 수 있다.
                message.obj = input;

                // 메인 스레드에서 새로 만든 스레드 안에 있는 핸들러로 메시지 전송하기
                thread.processHandler.sendMessage(message);
            }
        });

        thread = new ProcessThread();
    }

    /*
    실행 결과만 보면 아주 간단한 기능 같겠지만
      내부적으로는 메인 스레드에서 새로 만든 별도의 스레드로 Message 객체를 전송하고,
        별도의 스레드에서는 전달받은 문자열에 다른 문자열을 덧붙여서 메인 스레드 쪽으로 다시 전송하는 과정을 거친다.
     */

    class ProcessThread extends Thread {
        ProcessHandler processHandler = new ProcessHandler();

        public void run() {
            Looper.prepare();
            Looper.loop();
        }

        class ProcessHandler extends Handler {
            /* 새로 만든 스레드 안에서 전달받은 메시지 처리하기 */
            public void handleMessage(Message msg) {
                final String output = msg.obj + " from thread.";  // final = 지역변수를 상수화 시켜주는 명령어

                handler.post(new Runnable() {
                    public void run() {
                        textView.setText(output);  // 메인 스레드로 전송하는 과정
                    }
                });
            }
        }
    }

}
