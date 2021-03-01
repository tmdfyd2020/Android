package org.techtown.graphics.custom.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewStyle extends View {
    Paint paint;

    public CustomViewStyle(Context context) {
        super(context);

        init(context);
    }

    public CustomViewStyle(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);  // 첫 번째 사각형을 Fill 스타일로 설정
        paint.setColor(Color.RED);
        canvas.drawRect(10, 10, 100, 100, paint);

        paint.setStyle(Paint.Style.STROKE);  // 첫 번째 사각형을 Stroke 스타일로 설정
        paint.setStrokeWidth(2.0F);  // 선의 두께를 설정
        paint.setColor(Color.GREEN);
        canvas.drawRect(10, 10, 100, 100, paint);

        paint.setStyle(Paint.Style.FILL);  // 두 번째 사각형을 Fill 스타일로 설정
        paint.setARGB(128, 0, 0, 255);  // 투명도를 조절
        canvas.drawRect(120, 10, 210, 100, paint);

        DashPathEffect dashEffect = new DashPathEffect(new float[]{5,5}, 1);  // 두 번째 사각형을 Stroke 스타일로 설정하고 PathEffect 적용
          // 점선으로 그리고 싶을 때
          // 선이 그려지는 부분과 선이 그려지지 않는 부분이 각각 5의 크기로 지정되었다.
          // 일반적인 선을 그릴 때는 drawLine() 메서드
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0F);
        paint.setPathEffect(dashEffect);
        paint.setColor(Color.GREEN);
        canvas.drawRect(120, 10, 210, 100, paint);

        paint = new Paint();

        paint.setColor(Color.MAGENTA);  // 첫 번째 원에 색상 적용
        canvas.drawCircle(50, 160, 40, paint);  // 원을 그릴 때

        paint.setAntiAlias(true);  // 두 번째 원에 AntiAlias 설정
        canvas.drawCircle(160, 160, 40, paint);

        paint.setStyle(Paint.Style.STROKE);  // 첫 번째 텍스트를 Stroke 스타일로 설정
        paint.setStrokeWidth(1);
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(50);
        canvas.drawText("Text (Stroke)", 20, 260, paint);  // 두 번째 텍스트를 Fill 스타일로 설정

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        canvas.drawText("Text", 20, 320, paint);  // 텍스트
    }

}
