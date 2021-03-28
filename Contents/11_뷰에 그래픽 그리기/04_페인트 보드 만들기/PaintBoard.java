package org.techtown.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintBoard extends View {

    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;   // 그래픽을 그리기 위해 필요한 속성을 담고 있다.

    int lastX;
    int lastY;

    public PaintBoard(Context context) {  // 파라미터가 한 개인 생성자
        super(context);

        init(context);
    }

    public PaintBoard(Context context, AttributeSet attrs) {  // 파라미터가 두 개인 생성자
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.BLACK);  // 페인트 객체에 검정색으로 색상을 설정한다.

        this.lastX = -1;
        this.lastY = -1;
    }

    // 메모리에 Bitmap 객체를 만들고 Canvas 객체 설정
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas;

    }

    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        // 터치하고 있는 곳의 X, Y 좌표 값 정보
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;

                break;

            // 뷰 위에 선을 그리는 방법 : 이전 터치 떄의 좌표와 현재 터치 때의 좌표를 이용해서 선을 긋기
            // 터치 이벤트는 연속해서 전달되기 때문에 선을 그려나가면 손가락이 움직이는 대로 그려진다.

            // 손가락으로 누른 ACTION_DOWN 상태에서 좌표 값을 변수에 저장한다.
            case MotionEvent.ACTION_DOWN:
                if (lastX != -1) {
                    if (X != lastX || Y != lastY) {
                        mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                    }
                }

                // 이전에 터치했을 때의 좌표는 lastX, lastY라는 변수에 저장되었다가 현재 터치했을 때의 좌표와 함께 그리기 작업에 사용된다.
                lastX = X;
                lastY = Y;

                break;

            // ACTION_MOVE 상태에서 이전의 좌표 값과 현재의 좌표 값을 연결하여 선을 긋는다.
            case MotionEvent.ACTION_MOVE:
                if (lastX != -1) {
                    mCanvas.drawLine(lastX, lastY, X, Y, mPaint);
                }

                lastX = X;
                lastY = Y;

                break;
        }

        invalidate();  // 화면을 다시 그리도록 하면 이동 시에도 지속적으로 화면을 갱신할 수 있다.

        return true;
    }

}
