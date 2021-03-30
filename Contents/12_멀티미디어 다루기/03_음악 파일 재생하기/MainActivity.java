package org.techtown.audio.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer mediaPlayer;
    int position = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);  // 재생
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playAudio(AUDIO_URL);
                Toast.makeText(getApplicationContext(),"음악 파일 재생 시작됨.", Toast.LENGTH_LONG).show();
            }
        });

        Button button2 = findViewById(R.id.button2);  // 중지
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 중지됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button3 = findViewById(R.id.button3);  // 일시정지
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    position = mediaPlayer.getCurrentPosition();  // 현 지점의 위치를 알아오고
                    mediaPlayer.pause();
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 일시정지됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button4 = findViewById(R.id.button4);  // 재시작
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);  // 중지했을 때의 지점에서부터 재생
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 재시작됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Mediaplayer 객체를 이용해 음악을 재생하는 playAudio() 메서드
    private void playAudio(String url) {
        killMediaPlayer();  // Go to down !!

        try {
            // MediaPlayer 객체 만들어 시작하기
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    // 미디어플레이어 객체가 이미 리소스를 사용하고 있을 경우 release() 메서드를 호출하여 리소스를 해제하는 역할
    // why? 미디어플레이어를 앱 내에서 재사용하려면 기존에 사용하던 리소스를 먼저 해제해야 하기 때문
    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();  // MediaPlayer 객체의 리소스 해제하기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
