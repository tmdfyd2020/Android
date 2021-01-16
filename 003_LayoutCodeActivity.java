package org.techtown.samplelinearlayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // new 연산자로 리니어 레이아웃을 만들고 방향 설정
        LinearLayout mainLayout = new LinearLayout(this);  // this : Context 객체(객체의 정보를 담고 있는 객체)
            // AppCompatActivity 클래스는 Context를 상속
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // new 연산자로 레이아웃 안에 추가될 뷰들에 설정할 파라미터 생성
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(  // LayoutParams : 뷰를 만들어 뷰그룹에 추가할 때, 뷰 배치를 위한 속성을 설정.
                                                // 뷰의 속성 중에서 레이아웃과 관련된 속성을 담고 있음
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        // 버튼에 파라미터 설정하고 레이아웃에 추가
        Button button1 = new Button(this);
        button1.setText("Button1");
        button1.setLayoutParams(params);  // 레이아웃 설정을 위해 버튼 객체의 setLayoutParams() 메소드 사용
        mainLayout.addView(button1);  // 레이아웃에 버튼을 추가
            // 화면 레이아웃을 만들 때 대부분은 XML 레이아웃 파일을 사용하지만, 가끔 소스코드에서 만드는 경우도 있기 때문에 알아두자!

        // 새로 만든 레이아웃을 화면에 설정
        setContentView(mainLayout);
    }
}
