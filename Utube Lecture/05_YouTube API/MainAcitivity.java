// 사전 설정 1
// 1) Google : "android youtube api" 다운로드
// 2) app/libs/에 다운 받은 YouTubeAndroidPlayerApi.jar를 저장
// 3) File -> Project Structure의 Dependencies -> Jar Dependencies 추가 -> YouTubeAndroidPlayerApi.jar 추가 -> apply

// 사전 설정 2
// Google API 콘솔(https://console.developers.google.com/?hl=ko)에 접속
// 프로젝트 만들기
// 사용자 인증 정보 -> API 키 -> 복사
// 안드로이드 스튜디오 Gradle에서 app/tasks/android/signingReport를 Run 시키고 SHA1을 복사
// 키 제한 사항 : Android 앱에서 패키지 이름과 SHA-1 인증서 지문을 입력하고 저장한다.
   // 패키지 이름 : AndroidManifest.xml에 있는 package 풀 네임
   // SHA-1 인증서 지문 : singingReport에서 긁어온 SHA1

package org.techtown.tutorial7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.techtown.tutorial7.R;

public class MainActivity extends YouTubeBaseActivity { // ☆ AppCompatActivity를 상속받는 게 아닌 YouTubeBaseActivity를 상속받는 것에 주의하자.

    YouTubePlayerView youtubeView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.youtubeButton);
        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
        // 3. listener는 초기화가 이루어졌을 때
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("OBymFy24-SQ"); // 유튜브 동영상의 v= 이후를 적으면 된다.
            }   // 4. 해당("OBymFy24-SQ) 비디오를 youTubePlayer에 띄우도록 프로그램이 구성된다.

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener() { // 1. 버튼을 클릭했을 때 Listener가 받아져서
            public void onClick(View view) {
                youtubeView.initialize("AIzaSyB007dA1Yhd2duCdTY6H_N8F-j1iF9JNTE", listener);
            }   // 2. youtubeView의 해당 코드로 초기화가 이루어진 후에 listener로 전달한다.
        });

    }
}
