package org.techtown.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ImageDisplayView extends View implements View.OnTouchListener {
	// OnTouchListener 인터페이스를 구현하면 터치 이벤트가 발생할 때마다 이 메서드를 호출한다.
	// 두 손가락을 이용해 이미지를 크게 만들려고 하면 이 메서드 안에서 터치할 때의 화면 좌표 값을 받아 이미지의 크기를 바꿔야 한다.
	private static final String TAG = "ImageDisplayView";
	
	Context mContext;
	Canvas mCanvas;
	Bitmap mBitmap;
	Paint mPaint;

	int lastX;
	int lastY;

	Bitmap sourceBitmap;  // 메모리에 만들어지는 비트맵 이미지
	
	Matrix mMatrix;  // 비트맵 이미지를 확대/축소 또는 이동하기 위해 사용되는 매트릭스 객체
	
	float sourceWidth = 0.0F;
	float sourceHeight = 0.0F;
	 
	float bitmapCenterX;
	float bitmapCenterY;
	
	float scaleRatio;
	float totalScaleRatio;
	 
	float displayWidth = 0.0F;
	float displayHeight = 0.0F;
	
	int displayCenterX = 0;
	int displayCenterY = 0;

	public float startX;
    public float startY;
    
    public static float MAX_SCALE_RATIO = 5.0F;
    public static float MIN_SCALE_RATIO = 0.1F;
    
	float oldDistance = 0.0F;
	 
	int oldPointerCount = 0;
	boolean isScrolling = false;
	float distanceThreshold = 3.0F;

	public ImageDisplayView(Context context) {
		super(context);
		
		mContext = context;

		init();
	}

	public ImageDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		init();
	}

	// 매트릭스 객체를 초기화하고 이 클래스에서 구현하는 리스너인 OnTouchListener를 설정한다.
	// 메모리에 만들어지는 비트맵 이미지를 초기화하는 부분은 onSizeChanged() 메서드에 들어있는데
	// -> 뷰가 화면에 보이기 전에 onSizeChanged() 메서드가 호출되므로 이 메서드 안에서 비트맵 이미지를 만드는 것이 효율적이기 때문
	private void init() {

		mPaint = new Paint();
        mMatrix = new Matrix();
        
		lastX = -1;
		lastY = -1;
 
		setOnTouchListener(this);
	}

	// 뷰가 초기화되고 나서 화면에 보이기 전 크기가 정해지면 호출되는 메서드 안에서 메모리 상에 새로운 비트맵 객체 생성
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w > 0 && h > 0) {
			newImage(w, h);

	        redraw();
		}
	}

	public void newImage(int width, int height) {
		Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas();
		canvas.setBitmap(img);
		
		mBitmap = img;
		mCanvas = canvas;

		displayWidth = (float)width;
		displayHeight = (float)height;
		 
		displayCenterX = width/2;
    	displayCenterY = height/2;
	}	
 
	public void drawBackground(Canvas canvas) {
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
		}
	}	

	// 뷰가 화면에 그려지는 메서드 안에서 메모리 상의 비트맵 객체 그리기
	// 터치 이벤트를 지속적으로 처리해 다시 그려주는 과정이 필요하므로 메모리에 비트맵 이미지를 만들어 두고
	// 이 이미지에 미리 그린 후 onDraw() 메서드 안에서는 메모리에 만들어 둔 비트맵 이미지를 화면에 보여주는 '더블 버퍼링' 방식을 사용한다.
	// onDraw() 메서드는 단순히 메모리에 만들어져 있는 비트맵 이미지를 화면에 그려주는 역할만을 하게 된다.
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
	}

    public void setImageData(Bitmap image) {
    	recycle();
    	
    	sourceBitmap = image;
    	
    	sourceWidth = sourceBitmap.getWidth();
    	sourceHeight = sourceBitmap.getHeight();
    	 
    	bitmapCenterX = sourceBitmap.getWidth()/2;
    	bitmapCenterY = sourceBitmap.getHeight()/2;
        
        scaleRatio = 1.0F;
        totalScaleRatio = 1.0F;
    }
    
    public void recycle() {
    	if (sourceBitmap != null) {
    		sourceBitmap.recycle();
    	}
    }
    
	public void redraw() {
		if (sourceBitmap == null) {
			Log.d(TAG, "sourceBitmap is null in redraw().");
			return;
		}
		
		drawBackground(mCanvas);
		
		float originX = (displayWidth - (float)sourceBitmap.getWidth()) / 2.0F;
		float originY = (displayHeight - (float)sourceBitmap.getHeight()) / 2.0F;
		
		mCanvas.translate(originX, originY);
        mCanvas.drawBitmap(sourceBitmap, mMatrix, mPaint);
        mCanvas.translate(-originX, -originY);

		invalidate();
	}
	
	// 뷰를 터치할 때 호출되는 메서드 다시 정의
	public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();  // 손가락으로 눌렀는지, 움직이고 있는지, 떼어졌는지 구분
        
        int pointerCount = ev.getPointerCount(); /*
        ○ public final int getPointerCount() : 몇 개의 손가락이 터치되었는지를 알 수 있도록 해주는 메서드
		  ○ 반환된 값이 1이라면 한 개의 손가락, 2라면 두 개의 손가락이 터치된 상태이다.
		    getX()와 getY() 메서드는 손가락이 하나일 때 X와 Y의 좌표 값을 가져오지만
		    getX(int pointerIndex)와 getY(int pointerIndex) 메서드는 여러 개의 손가락이 터치되었을 때 각각의 손가락이 가지는 인덱스 값을 반환
		  ○ 예를 들어, 두 개의 손가락이 터치되면 첫 번째 손가락은 인덱스 0, 두 번째 손가락을 인덱스 1이 되고,
		    getPointerCount() 메서드를 호출했을 때 리턴 값은 2가 된다.
		  ○ 첫 번째 손가락으로 터치한 지점의 좌표 값을 알고 싶다면 getX(1)과 getY(1)을 호출하면 된다. */

        Log.d(TAG, "Pointer Count : " + pointerCount);
        
        switch (action) {
        	case MotionEvent.ACTION_DOWN:  // 손가락으로 눌렀을 때의 기능 추가
        		 
        		if (pointerCount == 1) {
        			// 하나의 손가락에 대한 좌표 값은 getX()와 getY()로 알 수 있다.
    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
        			startX = curX;
                    startY = curY;
                     
        		} else if (pointerCount == 2) {
        			oldDistance = 0.0F;
        			
        			isScrolling = true;
        		}
        		
        		return true;
        	case MotionEvent.ACTION_MOVE:  // 손가락으로 움직일 때의 기능 추가
        		
    	    	if (pointerCount == 1) {  // getAction() 값이 ACTION_MOVE이면서 getPointerCount 값이 1인 경우
    	    		// 한 손가락으로 움직이고 있는 상태이므로 이전에 움직였을 때의 좌표 값과 차이를 계산한 후 그 만큼 이미지를 이동시킬 수 있도록
					// moveImage() 메서드를 호출한다.

    	    		if (isScrolling) {
    	    			return true;
    	    		}
    	    		
    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
    	    		if (startX == 0.0F) {
    	    			startX = curX;
    	                startY = curY;
    	    			
    	    			return true;
    	    		}
    	    		
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;

                	if (oldPointerCount == 2) {
                		
                	} else {
                		Log.d(TAG, "ACTION_MOVE : " + offsetX + ", " + offsetY);

                		if (totalScaleRatio > 1.0F) {
                			moveImage(-offsetX, -offsetY);  // 한 손가락으로 움직이고 있을 때는 scaleImage() 메서드 호출
                		}
                		
    	                startX = curX;
    	                startY = curY;
                	}
	    		
    	    	} else if (pointerCount == 2) {
    	    		
    	    		float x1 = ev.getX(0);
    	    		float y1 = ev.getY(0);
    	    		float x2 = ev.getX(1);
    	    		float y2 = ev.getY(1);
    	    		
    	    		float dx = x1 - x2;
    	    		float dy = y1 - y2;
    	    		float distance = new Double(Math.sqrt(new Float(dx * dx + dy * dy).doubleValue())).floatValue();
    	    		
    	    		float outScaleRatio = 0.0F;
    	    		if (oldDistance == 0.0F) {
    	    			oldDistance = distance;
    	    			
    	    			break;
    	    		}
    	    		
    	    		if (distance > oldDistance) {
    	    			if ((distance-oldDistance) < distanceThreshold) {
    	    				return true;
    	    			}

    	    			outScaleRatio = scaleRatio + (oldDistance / distance * 0.05F);
    	    		} else if (distance < oldDistance) { 
    	    			if ((oldDistance-distance) < distanceThreshold) {
    	    				return true;
    	    			}

    	    			outScaleRatio = scaleRatio - (distance / oldDistance * 0.05F);
    	    		}

    	    		if (outScaleRatio < MIN_SCALE_RATIO || outScaleRatio > MAX_SCALE_RATIO) {
                        Log.d(TAG, "Invalid scaleRatio : " + outScaleRatio);
                    } else {
                        Log.d(TAG, "Distance : " + distance + ", ScaleRatio : " + outScaleRatio);
                        scaleImage(outScaleRatio);  // 두 손가락으로 움직이고 있을 때는 scaleImage() 메서드 호출
                    }
    	    		
    	    		oldDistance = distance;
    	    	}
        		
    	    	oldPointerCount = pointerCount;
    	    	
        		break;
        		
        	case MotionEvent.ACTION_UP:  // 손가락을 떼었을 때의 기능 추가
        		
        		if (pointerCount == 1) {

    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;
                    
                    if (oldPointerCount == 2) {
                    		 
                	} else {
                		moveImage(-offsetX, -offsetY);
                	}
	    	 
        		} else {
        			isScrolling = false;
        		}
        		
            	return true;
        }
        
        return true;
	 }

	 // 이미지의 크기를 확대 또는 축소하거나 이미지를 이동하기 위해 사용할 수 있는 쉬운 방버은 매트릭스(Matrix) 객체를 사용하는 것이다.
	 // 매트릭스는 수학적인 연산을 통해 비트맵 이미지의 각 픽셀 값을 변경해 주는데, 이 객체를 사용하기 쉽도록 아래와 같은 메서드들이 정의되어 있다.
	 /* ○ public boolean postScale(float sx, float sy, float px, float py) : 비트맵 이미지를 확대 또는 축소할 수 있다.
	      ○ para 1 : X축을 기준으로 확대하는 비율
	      ○ para 2 : Y축을 기준으로 확대하는 비율
	      ○ para 3, para 4 : 확대 또는 축소할 때 기준이 되는 위치, 일반적으로 비트맵 이미지의 중심점을 지정한다.

	    ○ public boolean postTranslate(float dx, float dy) : 비트맵 이미지를 이동시킬 때 사용한다.
	      ○ para 1, para 2 : 이동할 만큼의 X와 Y 좌표 값을 의미한다.

	    ○ public boolean postRotate(float degrees) : 비트맵 이미지를 회전시킬 때 사용한다.
	      ○ para : 회전 각도 */

	 // 매트릭스 객체를 사용해 이미지 크기 변경
     private void scaleImage(float inScaleRatio) {
    	Log.d(TAG, "scaleImage() called : " + inScaleRatio);
    	
    	mMatrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY);
    	mMatrix.postRotate(0);

    	totalScaleRatio = totalScaleRatio * inScaleRatio;

    	redraw();
     }

     // 매트릭스 객체를 사용해 이미지 이동
     private void moveImage(float offsetX, float offsetY) {
    	Log.d(TAG, "moveImage() called : " + offsetX + ", " + offsetY);

		// 매트릭스 객체의 postTranslate() 메서드를 호출한 후,
    	mMatrix.postTranslate(offsetX, offsetY);
		// 화면에 다시 그릴 수 있도록 정의한 redraw() 메서드를 호출한다.
    	redraw();
     }

}
