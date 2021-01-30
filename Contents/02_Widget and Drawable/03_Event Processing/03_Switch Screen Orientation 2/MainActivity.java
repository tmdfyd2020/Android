package org.techtown.orientation2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {  // 방향이 변경되었을 때 이 함수가 호출되고, Configuration 객체에 정보를 넣어준다.
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {  // 가로 방향일 때
            showToast("가로 방향임");
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) { // 세로 방향일 때
            showToast("세로 방향임");
        }
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
