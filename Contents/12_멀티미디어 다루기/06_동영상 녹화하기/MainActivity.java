package org.techtown.video.recorder;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    // 동영상 녹화가 오디오 녹음과 다른 점은
    //   동영상을 녹음하기 위한 입력 소스로 카메라를 지정하여 사용자가 카메라 미리보기를 할 수 있도록 만들어 줘야 한다는 점이다.

    MediaPlayer player;
    MediaRecorder recorder;

    String filename;

    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surface = new SurfaceView(this);  // SurfaceView 객체 만들기
        holder = surface.getHolder();  // 서피스 뷰에서 서피스 홀더 객체를 참조한 후
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  // 카메라 미리보기 화면이 보이도록 타입을 SURFACE_TYPE_PUSH_BUFFERS로 지정

        FrameLayout frame = findViewById(R.id.container);
        frame.addView(surface);

        Button button = findViewById(R.id.button);  // 녹화시작 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();  // Go to down !!
            }
        });

        Button button2 = findViewById(R.id.button2);  // 녹화중지 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();  // Go to down !!
            }
        });

        Button button3 = findViewById(R.id.button3);  // 재생시작 버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();  // Go to down !!
            }
        });

        Button button4 = findViewById(R.id.button4);  // 재생중지 버튼
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();  // Go to down !!
            }
        });

        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename = sdcard + File.separator + "recorded.mp4";

        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);  // 비디오 입력 정보 설정 : 비디오 입력이 카메라가 된다.
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);  // 비디오 인코더 정보 설정
        recorder.setOutputFile(filename);

        recorder.setPreviewDisplay(holder.getSurface());  // 카메라 미리보기를 MediaRecorder에서 사용
        // para로 설정할 미리보기 화면에 대한 정보를 서피스홀더 객체의 getSurface() 메서드를 사용

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();

            recorder.release();
            recorder = null;
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
        values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Audio.Media.DATA, filename);

        Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        // 미디어 앨범에 녹화된 동영상을 저장하고 싶을 때는 오디오의 경우처럼 내용 제공자를 사용

        if (videoUri == null) {
            Log.d("SampleVideoRecorder", "Video insert failed.");
            return;
        }

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));
        // 미디어 앨범에 저장되었다는 정보를 다른 앱에도 알려주고 싶다면 Intent.ACTION_MEDIA_SCANNER_SCAN_FILE 액션

    }

    public void startPlay() {
        if (player == null) {
            player = new MediaPlayer();
        }

        try {
            player.setDataSource(filename);
            player.setDisplay(holder);

            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
