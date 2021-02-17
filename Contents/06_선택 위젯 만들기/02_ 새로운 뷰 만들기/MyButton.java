package org.techtown.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class MyButton extends AppCompatButton {

    public MyButton(Context context) {
        // 안드로이드는 UI 객체를 만들 때 Context 객체를 전달받도록 되어 있으므로 생성자에는 항상 Context 객체가 전달되어야 한다.
        super(context);

        init(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        // AttributeSet 객체는 XML 레이아웃에서 태그에 추가하는 속성을 전달받기 위한 것이다.
        super(context, attrs);

        init(context);
        // 생성자가 두 개이므로 이 뷰가 초기화될 때 필요한 코드는 init() 함수를 만들어 그 안에 정의한다.
    }

    /* [ 초기화를 위한 함수 정의하기 ] */
    private void init(Context context) {
        setBackgroundColor(Color.CYAN);  // 배경색 설정
        setTextColor(Color.BLACK);  // 글자색 설정

        float textSize = getResources().getDimension(R.dimen.text_size);  // /app/res/values 폴더 안에 dimens.xml 파일에 있는 글자 크기
        // 소스 코드에서 dimens.xml을 참조할 때는 Resources 객체의 getDimension() 함수를 사용한다.
        setTextSize(textSize);  // 글자 크기 설정
    }

    @Override
    protected void onDraw(Canvas canvas) {  // 뷰가 그려질 때 호출되는 함수에 기능 추가하기
        super.onDraw(canvas);

        Log.d("MyButton", "onDraw 호출됨");  // onDraw() 함수가 언제 호출되는지 알아보기 위해 Log.d() 함수 사용
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {  // 뷰가 터치될 때 호출되는 함수에 기능 추가하기
        Log.d("MyButton", "onTouchEvent 호출됨");  // onTouchEvent() 함수가 언제 호출되는지 알아보기 위해 Log.d() 함수 사용

        int action = event.getAction();  // getAction() 함수는 정수형 값으로 상태를 반환한다.
        switch (action) {
            case MotionEvent.ACTION_DOWN: // 손가락이 눌린 경우
                setBackgroundColor(Color.BLUE);
                setTextColor(Color.RED);
                break;

            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.CYAN);
                setTextColor(Color.BLACK);
                break;
        }

        invalidate();  // 손가락이 눌렸을 때 배경색과 글자색을 바꾸었다면 invalidate() 함수를 호 출하여 뷰를 다시 그린다.
                       // 이때, 뷰가 다시 그려진다면 onDraw() 함수가 동작한다.

        return true;
    }

}
