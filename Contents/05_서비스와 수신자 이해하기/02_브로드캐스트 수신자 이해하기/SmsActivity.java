package org.techtown.receiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SmsActivity extends AppCompatActivity {  // 메시지를 보여주기 위한 Activity
    EditText editText;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 전달받은 Intent 처리하도록 processIntent 함수 호출하기
        Intent passedIntent = getIntent();  // 브로드캐스트 수신자로부터 Intent를 전달받을 것이므로 getIntent() 함수를 호출하여
        processIntent(passedIntent);  // processIntent() 함수를 호출
    }

    @Override
    protected void onNewIntent(Intent intent) {  // 이 액티비티가 이미 만들어져 있는 상태에서 전달받은 Intent도 처리하도록 한다.
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {  // Intent 객체 안에 들어 있는 부가 데이터를 꺼내서 입력상자에 설정한다.

        // Intent가 null이 아니면 그 안에 들어있는 부가 데이터를 화면에 보여주기
        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String receivedDate = intent.getStringExtra("receivedDate");

            editText.setText(sender);
            editText2.setText(contents);
            editText3.setText(receivedDate);
        }
    }

}
