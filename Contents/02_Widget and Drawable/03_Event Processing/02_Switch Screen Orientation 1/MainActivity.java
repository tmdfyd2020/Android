package org.techtown.orientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String name;
    EditText editText;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // 액티비티 시작
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast("onCreate 호출됨");

        editText = findViewById(R.id.editText);
        textView2 = findViewById(R.id.textView2);

        Button button = findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null) {
                        name = editText.getText().toString();  // 세로 방향일 때 입력한 텍스트를 name에 저장
                        showToast("사용자 입력값을 name 변수에 할당함");
                    }
                }
            });
        }

        if (savedInstanceState != null) {
            if (textView2 != null) {
                name = savedInstanceState.getString("name");
                textView2.setText(name);  // 세로 방향에서 입력한 name을 가로 방향의 텍스트에 넣어줌

                showToast("값을 복원했습니다 : " + name);
            }
        }
    }

    // 오른쪽 클릭 -> Generate -> Override Methods -> onSaveInstanceState
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {  // 단말의 방향이 바뀔때 액티비티가 메모리에서 없어졌다가 새로 만들어진다. (onDestroy -> onCreate)
                                                                    // 이 경우에 액티비티 안에 선언해 둔 변수 값이 사라지므로 변수의 값을 저장했다가 다시 복원하는 방법이 필요
                                                                    // onSaveInstanceState : 액티비티가 종료되기 전의 상태를 저장
                                                                    // name 변수의 값을 파라미터로 전달받은 Bundle 객체에 넣어준다.
                                                                    // 이 Bundle 객체에 데이터를 넣으면 단말에 저장되고, onCreate() 메서드가 호출될 때 파라미터로 전달된다.
        super.onSaveInstanceState(outState);

        outState.putString("name", name);
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {  // 액티비티 소멸
        super.onDestroy();

        showToast("onDestroy 호출됨");
    }
}
