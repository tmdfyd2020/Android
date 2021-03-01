package org.techtown.graphics.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CustomView extends View {

    private Paint paint;  // 그래픽을 그리기 위해 필요한 속성을 담고 있다.

    public CustomView(Context context) {  // 파라미터가 한 개인 생성자
        super(context);

        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {  // 파라미터가 두 개인 생성자
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.RED);  // 페인트 객체에 빨간색으로 색상을 설정한다.
    }

    @Override
    protected void onDraw(Canvas canvas) {  // 뷰가 화면에 그려질 때 자동으로 호출된다.
        super.onDraw(canvas);

        canvas.drawRect(100, 100, 200, 200, paint);
        // drawRect() : 좌표값과 페인트 객체를 이용해서 사각형을 그린다.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {  // 터치 이벤트를 처리하는 일반적인 방법 제공
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(super.getContext(), "MotionEvent.ACTION_DOWN : " +
                    event.getX() + ", " + event.getY(), Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(event);
    }
}
