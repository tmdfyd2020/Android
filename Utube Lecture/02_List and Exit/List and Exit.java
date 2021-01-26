package org.techtown.tutorial3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] items = {"망고 쥬스", "토마토 쥬스", "포도 쥬스"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button listButton = (Button) findViewById(R.id.listButton);  // xml 파일로부터 listButton 받아오기
        listButton.setOnClickListener(new View.OnClickListener() {  // 버튼에 이벤트 부여
            @Override
            public void onClick(View v){  // 버튼을 클릭했을 때 함수 처리
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("리스트");  // 팝업 창의 제목 부분 명시
                builder.setItems(items, new DialogInterface.OnClickListener() {  // 팝업 창에 해당하는 리스트 내용 넣기
                    // 리스트 안에 있는 내용을 클릭했을 때 이벤트 처리
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), items[i], Toast.LENGTH_SHORT).show();
                    }  // 토스트 띄우기, 인덱스를 받아서 인덱스 내용 띄우기
                });
               AlertDialog alertDialog = builder.create();
               alertDialog.show();
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitButton);  // xml에서 exitButton 받아오기
        exitButton.setOnClickListener(new View.OnClickListener() {  // 버튼에 이벤트 부여
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("정말로 종료하시겠습니까?");
                builder.setTitle("종료 알림창")
                        .setCancelable(false) // 사용자가 임의적으로 종료할 수 없도록 한다.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {  // Yes 버튼 클릭 시
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();  // 어플을 종료
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {  // No 버튼 클릭 시
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();  // dialog 창이 종료
                            }
                        });
                AlertDialog alert = builder.create();  // builder에서 Dialog 객체 생성
                alert.setTitle("종료 알림창");
                alert.show();
            }
        });

    }
}
