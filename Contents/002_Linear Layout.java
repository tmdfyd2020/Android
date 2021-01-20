package org.techtown.samplelinearlayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LayoutCodeActivity extends AppCompatActivity {

    @Override // 상속 관계에 있는 두 클래스 중, 하위 클래스에서 상위 클래스의 메소드를 재정의하는 것을 의미합니다. 주로 오버라이딩 함수 철자 실수 점검을 위해 사용된다.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     // [ new 연산자로 리니어 레이아웃을 만들고 방향 설정 ]
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
      // □ this : Context 객체(객체의 정보를 담고 있는 객체)
      // □ new 연산자를 사용해서 뷰 객체를 코드에서 만들 때는 항상 Context 객체가 전달되어야 한다. 즉, 안드로이드의 모든 UI 객체들은 Context 객체를 전달받는다.
      // □ AppCompatActivity 클래스는 Context를 상속하므로 이 클래스 안에서는 this를 Context 객체로 사용할 수 있다.
        
     // [ new 연산자로 레이아웃 안에 추가될 뷰들에 설정할 파라미터 생성 ]
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(  // □ LayoutParams : 뷰를 만들어 뷰그룹에 추가할 때, 뷰 배치를 위한 속성을 설정.
                                                // □ 뷰의 속성 중에서 레이아웃과 관련된 속성을 담고 있음
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
        
     // [ 버튼에 파라미터 설정하고 레이아웃에 추가 ]
        Button button1 = new Button(this);
        button1.setText("Button1");
        button1.setLayoutParams(params);  // □ 레이아웃 설정을 위해 버튼 객체의 setLayoutParams() 메소드 사용
        mainLayout.addView(button1);      // □ 레이아웃에 버튼을 추가
        // # 화면 레이아웃을 만들 때 대부분은 XML 레이아웃 파일을 사용하지만, 가끔 소스코드에서 만드는 경우도 있기 때문에 알아두자!

     // [ 새로 만든 레이아웃을 화면에 설정 ]
        setContentView(mainLayout);
    }
}
