package org.techtown.snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스낵바 보여주기
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "스낵바입니다", Snackbar.LENGTH_LONG).show();  // 스낵바 보여주기
            }
        });

        // 알림 대화상자 보여주기
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });
    }

    public void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  // 대화상자를 만들기 위한 빌더 객체 생성
        builder.setTitle("안내");  // 타이틀
        builder.setMessage("종료하시겠습니까?");  // 내용
        builder.setIcon(android.R.drawable.ic_dialog_alert);  // 아이콘

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {  // 예 버튼 추가
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "예 버튼 눌림", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {  // 아니오 버튼 추가
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "아니오 버튼 눌림", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();  // 대화상자 객체 생성 후 보여주기
        dialog.show();
    }
}
