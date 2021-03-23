package org.techtown.graphics.custom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewImage extends View {  // View를 상속하여 새로운 뷰를 정의한다.

    Bitmap cacheBitmap;     // 메모리에 만들어질 Bitmap 객체 선언
    Canvas cacheCanvas;     // 메모리에 만들어질 Bitmap 객체에 그리기 위한 Canvas 객체 선언
    Paint mPaint;

    public CustomViewImage(Context context) {
        super(context);

        init(context);
    }

    public CustomViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
    }

    // 뷰가 화면에 보이기 전에 Bitmap 객체 만들고 그 위에 그리기
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        createCacheBitmap(w, h);  // Go to down !
        testDrawing();  // Go to down !
    }

    // 메모리에 Bitmap 객체를 만들고 Canvas 객체 설정
    private void createCacheBitmap(int w, int h) {
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    // 노란 사각형 그리기
    private void testDrawing() {
        cacheCanvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.BLACK);
        cacheCanvas.drawRect(100, 100, 200, 200, mPaint);

        // BitmapFactory : 비트맵 이미지를 만들기 위한 클래스 메서드들을 제공하며, 이 메서드들은 이미지를 메모리에 비트맵 객체로 만들어 줄 수 있는 방법을 제공한다.

        // 리소스에 있는 이미지 파일을 읽어 들여 화면에 그리기
        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.waterdrop);
        cacheCanvas.drawBitmap(srcImg, 30, 30, mPaint);

        // 매트릭스 객체를 이용해 좌우 대칭이 되는 비트맵 이미지를 만들어 그리기
        Matrix horInverseMatrix = new Matrix();
        /* Matrix 객체 : 이미지 변환
           setScale() : 확대/축소, setTranslate() : 이동, setRotate() : 회전, setSkew() : 뒤틀림 */
        horInverseMatrix.setScale(-1, 1);  // setScale() : 대칭 이미지 만들기, (-1, 1) : 좌우 대칭, (1, -1) : 상하 대칭
        Bitmap horInverseImg = Bitmap.createBitmap(srcImg, 0, 0,
                srcImg.getWidth(), srcImg.getHeight(), horInverseMatrix, false);
        cacheCanvas.drawBitmap(horInverseImg, 30, 130, mPaint);

        // 매트릭스 객체를 이용해 상하 대칭이 되는 비트맵 이미지를 만들어 그리기
        Matrix verInverseMatrix = new Matrix();
        verInverseMatrix.setScale(1, -1);
        Bitmap verInverseImg = Bitmap.createBitmap(srcImg, 0, 0,
                srcImg.getWidth(), srcImg.getHeight(), verInverseMatrix, false);
        cacheCanvas.drawBitmap(verInverseImg, 30, 230, mPaint);

        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        // 마스크 : 이미지에 다양한 효과. BlurMaskFilter : 번짐 효과를 낼 수 있는 마스크 필터로 페인트 객체에 설정하여 사용
        Bitmap scaledImg = Bitmap.createScaledBitmap(srcImg,  // createScaledBitmap() : 비트맵 이미지를 확대
                srcImg.getWidth()*3, srcImg.getHeight()*3, false);
        cacheCanvas.drawBitmap(scaledImg, 30, 300, mPaint);
    }

    // 메모리의 Bitmap을 이용해 화면에 그리기
    protected void onDraw(Canvas canvas) {
        if (cacheBitmap != null) {
            canvas.drawBitmap(cacheBitmap, 0, 0, null);
        }
    }

}
