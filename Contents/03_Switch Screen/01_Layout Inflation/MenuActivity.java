package org.techtown.inflater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

// ○ 인플레이션(Inflation) : XML 레이아웃의 내용이 메모리에 객체화되는 과정
//   ○ setContentView(R.layout.activity_main) 메서드 : 화면에 표시할 XML 레이아웃을 지정하거나 화면에 표시할 뷰 객체를 지정하는 역할
//   ○ 액티비티 화면 전체(메인 레이아웃)를 설정하는 역할만을 수행하고, 부분 화면(부분 레이아웃)을 메모리에 객체화하려면 인플레이터를 사용해야 한다.

public class MenuActivity extends AppCompatActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        container = findViewById(R.id.container);  // 부분 화면을 넣을 레이아웃 안의 레이아웃

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayout();
            }
        });
    }

    public void addLayout() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  // LayoutInflater 객체를 참조
        inflater.inflate(R.layout.sub1, container, true);  // container을 id로 갖는 리니어 레이아웃 객체에 sub1.xml 파일의 레이아웃을 설정하는 것
        // para 1 : XML 레이아웃 리소스, para 2 : 부모 컨테이너           // 부분 레이아웃에 정의된 뷰들이 메모리에 로딩되며 객체화 과정을 거치게 된다.

        // 이제 부분 레이아웃 파일이 객체화되었으므로 부분 레이아웃에 들어있던 텍스트뷰와 버튼을 findeViewById() 메서드로 참조할 수 있다.
        // 단, 부분 레이아웃은 container 객체에 설정되었으므로 container.findViewById(...)와 같은 방법으로 참조해야 한다.
        Toast.makeText(this,"부분 화면이 추가되었습니다.", Toast.LENGTH_LONG).show();
    }
}
