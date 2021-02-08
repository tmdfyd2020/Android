// app 오른쪽 클릭 -> New -> Activity -> Empty Activity ->
package org.techtown.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = findViewById(R.id.button);  // main과 menu의 버튼 id는 중복해도 서로 다른 환경이므로 무방하다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();  // Intent 객체 생성하고
                intent.putExtra("name", "mike");  // name의 값을 부가 데이터로 넣기
                // putExtra : 인텐트 객체에 데이터를 넣는 방법
                // Key와 Value를 쌍으로 넣어야 한다. 값을 확인할 때는 Key를 사용한다.
                setResult(200, intent);  // 응답 보내기
                // setResult : 새로 띄운 액티비티에서 이전 액티비티로 인텐트를 전달하고 싶을 때 사용
                // para 1 : 응답 코드
                finish();  // 현재 액티비티 없애기
            }
        });
    }
}
