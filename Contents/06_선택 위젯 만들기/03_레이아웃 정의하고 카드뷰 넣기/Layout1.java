package org.techtown.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Layout1 extends LinearLayout {
    /* 밑에서 findViewById() 함수로 찾아낼 뷰들을 이 클래스 안의 어느 코드에서건 접근할 수 있도록 클래스 상단에 변수를 선언한 후 그 변수에 할당한다. */
    ImageView imageView;
    TextView textView;
    TextView textView2;

    public Layout1(Context context) {
        super(context);

        init(context);
    }

    public Layout1(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  // 인플레이션 진행하기
        // 이 객체는 시스템 서비스로 제공되므로 getSystemService() 함수를 호출하면서 파라미터로 Context.LAYOUT_INFLATER_SERVICE 상수를 전달하면 객체가 반환된다.
        // ☆ Inflation : XML 레이아웃에 정의된 내용이 메모리에 로딩된 후, 객체화되는 과정 ☆
        inflater.inflate(R.layout.layout1, this, true);
        // 이 객체의 inflate() 함수를 호출하면서 XML 레이아웃 파일을 파라미터로 전달하면 인플레이션이 진행되면서 이 소스 파일에 저장된다.

        /* XML 레이아웃에서 정의했던 뷰 참조하기 */
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }

    /* 뷰에 데이터 설정하기 */
    public void setImage(int resId) {
        imageView.setImageResource(resId); // 이미지 뷰에 보이는 이미지를 바꿀 수 있는 함수
                                           // /app/res/drawable 폴더 안에 들어있는 이미지 파일을 참조하는 정수 값을 파라미터로 전달받는다.
    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setMobile(String mobile) {
        textView2.setText(mobile);
    }

}
