package org.techtown.anim;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);  // 리소스에 정의한 애니메이션 액션 로딩
                v.startAnimation(anim);  // 뷰의 애니메이션 시작
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale2); // 리소스에 정의한 애니메이션 액션 로딩
                v.startAnimation(anim);  // 뷰의 애니메이션 시작
            }
        });

    }
}
