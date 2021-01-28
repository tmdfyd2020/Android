package org.techtown.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        GestureDetector detector;  // 제스처 이벤트를 처리해주는 클래스 GestureDetector
                                   // 이 객체를 만들고 터치 이벤트를 전달하면 GestureDetector 객체에서 각 상황에 맞는 메서드를 호출한다.

        View view = findViewById(R.id.view);  // View를 activity_main에서 찾아와서 참조한다.
        view.setOnTouchListener(new View.OnTouchListener() {  // setOnTouchListener() 메소드를 호출하여 리스너를 등록한다. 메소드를 호출할 때
            @Override                                         // 인수로 리스터 객체를 new 연산자를 이용해 OnTouchListener 객체를 생성하면서 전달한다.
                                                              // 그러면 뷰가 터치되었을 때 이 리스터 객체의 onTouch() 메서드가 자동으로 호출된다.
            public boolean onTouch(View v, MotionEvent event) {  // MotionEvent 객체에는 액션 정보나 터치한 곳의 좌표 등이 들어있다.
                int action = event.getAction();  // Action 정보는 getAction() 메서드로 확인할 수 있으며 손가락이 눌렸는지 눌린 상태로 움직이는지, 손가락이 떼졌는지를 알 수 있다.
                                                 // getAction() 메서드를 호출하면 정수 자료형의 값이 반환된다. 그 값과 MotionEvent 클래스에 정의된 상수 값과 비교하면 터치 상태를 알 수 있다. (밑에 구현)
                float curX = event.getX();
                float curY = event.getY();

                if (action == MotionEvent.ACTION_DOWN) {  // MotionEvent.ACTION_DOWN : 손가락이 눌렸을 때
                    println("손가락 눌림 : ( " + curX + " ," + curY + " )");
                }
                else if (action == MotionEvent.ACTION_MOVE) {  // MotionEvent.ACTION_MOVE : 손가락이 눌린 상태로 움직일 때
                    println("손가락 움직임 : ( " + curX + " ," + curY + " )");
                }
                else if (action == MotionEvent.ACTION_UP) {  // MotionEvent.ACTION_UP : 손가락이 떼졌을 때
                    println("손가락 뗌 : ( " + curX + " ," + curY + " )");
                }
                return true;
            }
        });

        // ###################################################################################################################

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                println("onDown 호출됨");

                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;  // 스크롤(Scroll) : 손가락으로 드래그하는 경우 / 이동한 거리 값이 중요하게 처리
            }

            @Override
            public void onLongPress(MotionEvent e) {  // 오랫동안 손가락으로 누르고 있을 때 호출출
               println("onLongPress 호출됨");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                println("onFling 호출됨 : ( " + velocityX + ", " + velocityY + " )");  // 플링(Fling) : 빠른 속도로 스크롤 / 이동한 속도 값이 중요하게 처리

                return true;
            }
        });

        View view2 = findViewById(R.id.view2);  // View2를 activity_main에서 찾아와서 참조한다.
        view2.setOnTouchListener(new View.OnTouchListener() {  // 두 번째 뷰 객체를 터치했을 때 자동으로 onTouch 메서드가 호출된다.
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);  // Gesture Detector 객체의 onTouchEvent() 메서드를 호출하면서 MotionEvent 객체를 전달한다.
                                               // 이렇게 하면 GestureDetector 객체가 터치 이벤트를 처리한 후 GestureDetector 객체에 정의된 메서드를 호출한다.
                return true;
            }
        });

    }

    // ###################################################################################################################

    // 마우스 오른쪽 버튼 -> Generate -> Override Methods -> onKeyDown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 키가 눌려졌을 때 처리된다
                                                            // keyCode : 어떤 키가 사용되는지 구별할 때 사용
                                                            // KeyEvent : 키 입력 이벤트에 대한 정보를 알고 싶을 때 사용된다.
        if (keyCode == KeyEvent.KEYCODE_BACK) {  // KEYCODE_BACK : 네비게이션 바에 있는 뒤로가기 버튼
            println("시스템 [BACK] 버튼이 눌렸어요.");

            return true;  // return true를 하면 뒤로가기 버튼을 눌렀을 때 이후의 동작이 취소된다.
        }

        return false;
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
