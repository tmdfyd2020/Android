package org.techtown.http;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

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
                final String urlStr = editText.getText().toString();

                new Thread(new Runnable() {  // request() 메서드 안에서는 인터넷을 사용할 것이므로
                                             //   스레드 객체를 하나 생성하고 그 안에서 request() 메서드를 호출하도록 한다.
                    @Override
                    public void run() {
                        request(urlStr);  // 스레드 안에서 request() 메서드 호출하기
                    }
                }).start();
            }
        });
    }

    public void request(String urlStr) {  // 응답 결과물을 모아서 화면에 출력한다.
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);  // URL 객체 만들기

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // HTTP 클라이언트를 만드는 방법

            if (conn != null) {
                conn.setConnectTimeout(10000);  // 연결 대기 시간을 설정 : 10초 동안 연결되기를 기다린다.
                conn.setRequestMethod("GET");  // 이 객체에 GET 방식으로 요청한다.
                conn.setDoInput(true);  // 이 객체의 입력이 가능하도록 만들어준다.

                int resCode = conn.getResponseCode();  // 이 시점에 내부적으로 웹서버에 페이지를 요청하는 과정을 수행하게 된다.
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  // 입력 데이터를 받기 위한 Reader 객체 생성하기
                String line = null;
                while (true) {
                    line = reader.readLine(); // 스트림에서 한 줄씩 읽어 들이는 메서드인 readLine() 메서드는 BufferedReader 클래스에 정의되어 있음
                    if (line == null) {
                        break;
                    }

                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception ex) {
            println("예외 발생함 : " + ex.toString());
        }

        println("응답 -> " + output.toString());
    }

    public void println(final String data) {  // 화면에 출력할 때 사용하는 println()
                                              //   핸들러를 사용하면서 화면에 들어있는 텍스트뷰의 append() 메서드를 호출한다.
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });

    }

}
