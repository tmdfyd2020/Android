package org.techtown.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toastView = Toast.makeText(getApplicationContext(), "토스트 메시지입니다.", Toast.LENGTH_LONG);
                toastView.setGravity(Gravity.TOP | Gravity.LEFT, 400, 1000);  // 토스트 위치 바꾸기
                toastView.show();
            }
        });

    }

    public void onButton2Clicked(View v) {
        LayoutInflater inflater = getLayoutInflater(); // LayoutInflater 객체를 사용해 XML로 정의된 레이아웃을 메모리에 객체화하고 있음
                                                       // 이것은 XML 레이아웃을 메모리에 로딩하는데 사용된다.
        View layout = inflater.inflate(  // 토스트를 위한 레이아웃 인플레이션
                R.layout.toastborder,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = layout.findViewById(R.id.text);

        Toast toast = new Toast(this);  // 토스트 객체 생성
        text.setText("모양 바꾼 토스트");
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);  // 토스트가 보이는 뷰 설정
        toast.show();
    }
}
