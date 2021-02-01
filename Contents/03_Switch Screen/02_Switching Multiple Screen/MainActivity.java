package org.techtown.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button); // 버튼 찾아주기
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);  // Context : 버튼일 경우 버튼이 포함되어 있는 주변 정보를 말한다.
                // Intent : 액티비티끼리 서로 호출하기 위해서 필요한 통신 장치
                // para 1 : 액티비티 클래스를 구현하는 Context, 주로 this를 많이 쓴다.
                // para 2 : 호출할 액티비티의 클래스
                startActivityForResult(intent, 101);
                // 액티비티에서 새 액티비티를 띄우기만 하려면 startActivity() 메서드를 구현해도 상관없지만,
                // 어떤 액티비티를 띄운 것인지, 띄웠던 액티비티로부터 다시 원래의 액티비티로 돌아오면서 응답을 받아 처리하기 위해서는
                // startActivityForResult 메서드를 사용해야 한다.
                // 코드의 값은 마음대로 지정해도 되지만, 앱에 들어갈 액티비티가 여러 개라면 중복되지 않는 값으로 정해야 한다.
                // 이 값은 나중에 새 액티비티로부터 응답을 받을 때 다시 전달받을 값이다.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  // MenuActivity에서 MainActivity에서 돌아올 때 자동 호출
        // para 1 : 액티비티를 띄울 때 전달했던 요청 코드. 어떤 액티비티로부터 응답을 받을 것인지 구분할 수 있다.
        // para 2 : 새 액티비티로부터 전달된 응답 코드. 새 액티비티에서 처리한 결과가 정상인지 아닌지를 구분하는데 사용된다.
        // para 3 : 새 액티비티로부터 전달 받은 인탠트
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (data != null) {
                String name = data.getStringExtra("name");
                if (name != null) {
                    Toast.makeText(this, "응답으로 받은 데이터 : " + name, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
