package org.techtown.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // MainActivity가 메모리에 만들어져 있지 않은 상태에서 처음 만들어지면
        super.onCreate(savedInstanceState);              // onCreate() 함수 안에서 getIntent() 함수를 호출하여 인텐트 객체를 참조한다.
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();

                // 인텐트 객체 만들고 부가 데이터 넣기
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                // Intent : 컴포넌트(액티비티, 서비스, 브로드캐스트 리시버) 간에 통신, 데이터 전달을 하기 위한 메시지 객체, 액티비티를 실행하는 용도로 많이 사용됨
                // getApplicationContext() : 어플리케이션 자체를 가져온다.
                  // this와의 차이 : this는 activity를 가져온다.
                intent.putExtra("command", "show"); // command는 서비스쪽으로 전달한 인텐트 객체의 데이터가 어떤 목적으로 사용되는지 구별하기 위해
                intent.putExtra("name", name); // name은 입력상자에서 가져온 문자열을 전달하기 위한 것

                // 서비스 시작하기
                startService(intent); // Intent객체는 MyService 클래스의 onStartCommand() 함수로 전달된다.
            }
        });

        // 액티비티가 새로 만들어질 때 전달된 인텐트 처리하기
        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {  // MainActivity가 이미 메모리에 만들어져 있으면 onNewIntent() 함수를 호출한다.
        // 액티비티가 이미 만들어져 있을 때 전달된 인텐트 처리하기
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Toast.makeText(this, "command : " + command + ", name : " + name, Toast.LENGTH_LONG).show();
        }
    }

}
