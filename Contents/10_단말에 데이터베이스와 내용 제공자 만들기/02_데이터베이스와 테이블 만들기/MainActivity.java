package org.techtown.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    TextView textView;

    SQLiteDatabase database;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);  // 데이터베이스 만들기 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText.getText().toString();
                createDatabase(databaseName);
            }
        });

        Button button2 = findViewById(R.id.button2);  // 테이블 만들기 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName = editText2.getText().toString();
                createTable(tableName);  // 테이블 만들기

                insertRecord();  // 임의의 레코드를 삽입
            }
        });
    }


    private void createDatabase(String name) {
        println("createDatabase 호출됨.");

        database = openOrCreateDatabase(name, MODE_PRIVATE, null);  // 데이터베이스를 만들기 위한 메서드 실행하기
        // 설명 -> Contents.txt
        println("데이터베이스 생성함 : " + name);
    }

    private void createTable(String name) {
        println("createTable 호출됨.");

        if (database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        /* 테이블을 만들기 위한 SQL문 실행하기 */
        database.execSQL("create table if not exists " + name + "("
            + " _id integer PRIMARY KEY autoincrement, "  // 안드로이드는 앞에 _을 붙여 _id로 만드는 방법을 권장 / PRIMARY KEY autoincrement = 자동으로 1씩 증가하는 킷값
            + " name text, "  // 이름
            + " age integer, "  // 나이
            + " mobile text)");  // 휴대폰 번호

        println("테이블 생성함 : " + name);
    }

    private void insertRecord() {
        println("insertRecord 호출됨.");

        if (database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        if (tableName == null) {
            println("테이블을 먼저 생성하세요.");
            return;
        }

        database.execSQL("insert into " + tableName
                        + "(name, age, mobile) "
                        + " values "
                        + "('John', 20, '010-1000-1000')");

        println("레코드 추가함.");
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

}
