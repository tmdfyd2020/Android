package org.techtown.callintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent를 사용하여 Activity를 띄워주는 첫 번째 경우
                /*
                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-9434-9845"));
                    ○ 전화걸기 화면을 보여줄 Intent 객체 생성
                   startActivity(intent);
                    ○ 액티비티 띄우기
                */

                // Intent를 사용하여 Activity를 띄워주는 두 번째 경우
                Intent intent = new Intent();
                ComponentName name = new ComponentName("org.techtown.callintent",
                        "org.techtown.callintent.MenuActivity");  // 컴포넌트 이름을 지정할 수 있는 객체 생성
                // Activity를 ComponentName으로 가리킬 수 있다.
                intent.setComponent(name);  // Intent 객체에 컴포넌트 지정
                startActivityForResult(intent, 101);  // 액티비티 띄우기
            }
        });
    }
}
