package org.techtown.video.player;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String VIDEO_URL = "https://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        // 동영상의 재생 상태를 보거나 동영상을 제어할 때 사용되는 미디어 컨트롤러
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);  // VideoView에 MediaController 설정하기

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoURI(Uri.parse(VIDEO_URL));  // VideoView에 재생할 대상 설정하고 재생 시작하기
                videoView.requestFocus();
                videoView.start();
            }
        });

    }
}
