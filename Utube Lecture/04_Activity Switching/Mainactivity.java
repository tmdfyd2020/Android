package org.techtown.tutorial6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameText = (EditText) findViewById(R.id.nameText);
        Button nameButton = (Button) findViewById(R.id.nameButton);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();

                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                // □ 새로운 창을 열기 위해 필요한 객체인 Intent
                // □ 액티비티끼리 서로 호출하기 위해서 필요한 통신 장치
                // □ parameter 1 : 액티비티 클래스를 구현하는 컨텍스트
                // □ parameter 2 : 호출할 액티비티 클래스

                intent.putExtra("nameText", name);  // ☆
                startActivity(intent); // 액티비티 호출하는 함수
            }
        });
    }
}
