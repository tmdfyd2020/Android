package org.techtown.capture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    CameraSurfaceView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout previewFrame = findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(this);
        previewFrame.addView(cameraView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture();  // Go to down !
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void takePicture() {
        cameraView.capture(new Camera.PictureCallback() {  // CameraSurfaceView의 capture() 메서드 호출하기
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  // 전달받은 바이트 배열을 Bitmap 객체로 만들기
                    String outUriStr = MediaStore.Images.Media.insertImage(  // 비트맵을 미디어 앨범에 추가한다.
                            getContentResolver(),  // para 1 : ContentResolver cr
                            bitmap,  // para 2 : Bitmap source : 메모리에 만들어진 비트맵 객체
                            "Captured Image",  // para 3 : String title : 비트맵 이미지의 제목
                            "Captured Image using Camera.");  // para 4 : String descrption : 비트맵 이미지의 내용

                    if (outUriStr == null) {
                        Log.d("SampleCapture", "Image insert failed.");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }

                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // SurfaceView 클래스를 상속하고 Callback 인터페이스를 구현하는 새로운 CameraSurfaceView 클래스 정의
    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            // 생성자에서 서피스홀더 객체 참조 후 설정
            mHolder = getHolder();  // 서피스홀더 객체를 참조
            mHolder.addCallback(this);  // 이 클래스에서 구현된 Callback 객체를 지정
        }

        // 서피스뷰 상태가 변경될 때 자동 호출되는 메서드 1
        // 서피스뷰가 만들어질 때 카메라 객체를 참조한 후 미리보기 화면으로 홀더 객체 설정
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();  // 카메라를 오픈한다.

            setCameraOrientation();  // Go to down !!

            try {
                camera.setPreviewDisplay(mHolder);  // 카메라 객체에 서피스홀더 객체를 지정
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 서피스뷰 상태가 변경될 때 자동 호출되는 메서드 2
        // 서피스뷰 화면 크기가 바뀌는 등의 변경 시점에 미리보기 시작
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // 서피스뷰의 크기가 변경되거나 할 때 호출
            camera.startPreview();  // 미리보기 시작
        }

        // 서피스뷰 상태가 변경될 때 자동 호출되는 메서드 3
        // 서피스뷰가 없어질 때(리소스를 해제) 미리보기 중지
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();  // 미리보기를 끝낸다.
            camera.release();
            camera = null;
        }

        // 카메라 객체의 takePicture() 메서드를 호출하여 사진 촬영
        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }

        public void setCameraOrientation() {
            if (camera == null) {
                return;
            }

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            camera.setDisplayOrientation(result);
        }

    }

    @Override  
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override  
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override  
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
