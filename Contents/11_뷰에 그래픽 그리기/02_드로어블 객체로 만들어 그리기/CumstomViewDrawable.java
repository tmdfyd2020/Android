package org.techtown.graphics.custom.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class CustomViewDrawable extends View {

    private ShapeDrawable upperDrawable;
    private ShapeDrawable lowerDrawable;

    public CustomViewDrawable(Context context) {  // 매개변수가 한 개인 생성자
        super(context);

        init(context);  // Go to down !!
    }

    public CustomViewDrawable(Context context, AttributeSet attrs) {  // 매개변수가 두 개인 생성자
        super(context, attrs);

        init(context);  // Go to down !!
    }

    private void init(Context context) {
        /* 윈도우 매니저를 이용해 뷰의 폭과 높이 확인 */
        // 뷰가 채워지는 화면의 크기를 알아오기 위해 시스템 서비스 객체인 윈도우 매니저를 참조한다.
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        /* 리소스에 정의된 색상 값을 변수에 설정 */
        Resources curRes = getResources();
        int blackColor = curRes.getColor(R.color.color01);  // Go to colors.xml !!
        int grayColor = curRes.getColor(R.color.color02);  // Go to colors.xml !!
        int darkGrayColor = curRes.getColor(R.color.color03);  // Go to colors.xml !!

        upperDrawable = new ShapeDrawable();  // Drawable 객체 생성

        RectShape rectangle = new RectShape();
        rectangle.resize(width, height*2/3);
        upperDrawable.setShape(rectangle);
        upperDrawable.setBounds(0, 0, width, height*2/3);

        /* LinearGradient 객체 생성 */
        // 뷰 영역의 위쪽 2/3와 아래쪽 1/3을 따로 채워줌으로써 위쪽에서부터 아래쪽으로 색상이 조금씩 변하는 배경 화면을 만들 수 있다.
        // para 1, para 2 : 시작점 (x1, y1)의 좌표
        // para 3, para 4 : 끝점 (x2, y2)의 좌표
        // 그러데이션 축에 색상과 타일 처리 방식으로 지정한 영역을 채워준다.
        LinearGradient gradient = new LinearGradient(0, 0, 0, height*2/3,
                grayColor, blackColor, Shader.TileMode.CLAMP);

        Paint paint = upperDrawable.getPaint();

        paint.setShader(gradient);  // Paint 객체에 새로 생성한 LinearGradient 객체를 Shader로 설정

        lowerDrawable = new ShapeDrawable();

        RectShape rectangle2 = new RectShape();
        rectangle2.resize(width, height*1/3);
        lowerDrawable.setShape(rectangle2);
        lowerDrawable.setBounds(0, height*2/3, width, height);

        LinearGradient gradient2 = new LinearGradient(0, 0, 0, height*1/3,
                blackColor, darkGrayColor, Shader.TileMode.CLAMP);

        Paint paint2 = lowerDrawable.getPaint();
        paint2.setShader(gradient2);

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        upperDrawable.draw(canvas);  // onDraw() 메서드 안에서 Drawable 객체 그리기
        lowerDrawable.draw(canvas);

        /* Cap.BUTT와 Join.MITER를 페인트 객체에 적용 */
        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);  // 선을 그릴 때 사용되는 Paint 객체는 Stroke 스타일로 만든 후에 SetAntiAlias() 메서드를 호출한다.
        pathPaint.setColor(Color.YELLOW);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(16.0F);  // Stroke의 폭을 설정한다.
        pathPaint.setStrokeCap(Paint.Cap.BUTT);  // Stroke의 시작과 끝 부분의 모양을 설정한다. 설정할 수 있는 값은 Cap.Butt, Cap.ROUND, Cap.SQUARE이다. 디폴트 값은 Cap.BUTT이다.
        pathPaint.setStrokeJoin(Paint.Join.MITER);  // Stroke 폭의 절반에 대한 Miter 길의의 비율 제한을 설정한다.

        /* Path 객체 생성 */
        Path path = new Path();
        path.moveTo(20, 20);  // Path.moveTo() : 단순히 좌표 값을 추가하는 역할
        path.lineTo(120, 20);  // Path.lineTo() : 이전 좌표 값과 선으로 연결되는 좌표 값을 추가하는 역할
        path.lineTo(160, 90);
        path.lineTo(180, 80);
        path.lineTo(200, 120);
        // 5개의 점을 이용해 선을 그리는 경우에는 moveTo() 메서드를 한 번 호출하고난 후 lineTo() 메서드를 네 번 호출하여 Path 객체에 좌표 값을 추가한다.

        canvas.drawPath(path, pathPaint);  // Path 객체 그리기

        /* Cap.ROUND와 Join.ROUND를 페인트 객체에 적용 */
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);

        /* offset을 주어 좌표를 이동한 뒤 Path 객체 그리기 */
        path.offset(30, 120);
        canvas.drawPath(path, pathPaint);

        /* Cap.SQUARE와 Join.BEVEL을 페인트 객체에 적용 */
        pathPaint.setColor(Color.CYAN);
        pathPaint.setStrokeCap(Paint.Cap.SQUARE);
        pathPaint.setStrokeJoin(Paint.Join.BEVEL);

        /* offset을 주어 좌표를 이동한 뒤 Path 객체 그리기 */
        path.offset(30, 120);
        canvas.drawPath(path, pathPaint);

    }

}
