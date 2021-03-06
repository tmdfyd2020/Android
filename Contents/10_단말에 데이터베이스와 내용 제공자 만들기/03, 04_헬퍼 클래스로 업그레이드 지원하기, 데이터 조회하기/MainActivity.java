package org.techtown.database;

import android.database.Cursor;
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

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);  // 데이터베이스 만들기
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText.getText().toString();
                createDatabase(databaseName);
            }
        });

        Button button2 = findViewById(R.id.button2);  // 테이블 만들기
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName = editText2.getText().toString();
                createTable(tableName);

                insertRecord();
            }
        });

        Button button3 = findViewById(R.id.button3);  // 데이터 조회하기
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeQuery();
            }
        });
    }


    private void createDatabase(String name) {

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        println("데이터베이스가 생성되었습니다.\n데이터베이스의 이름은 " + name + "입니다.\n");
    }

    private void createTable(String name) {

        if (database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        database.execSQL("create table if not exists " + name + "("
            + " _id integer PRIMARY KEY autoincrement, "
            + " name text, "
            + " age integer, "
            + " mobile text)");

        println("테이블이 생성되었습니다.\n테이블의 이름은 " + name + "입니다\n");
    }

    private void insertRecord() {

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
                        + "('Ryong', 27, '010-1000-1000')");

        println("레코드가 추가되었습니다.");
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

    public void executeQuery() {

        Cursor cursor = database.rawQuery("select _id, name, age, mobile from emp", null); // SQL 실행하고 Cursor 객체 반환하기
          // select 구문을 사용해서 반환되는 Cursor 객체를 받기 위해 rawQuery() 메서드를 실행한다.
          // rawQuery() : 결과 값을 Cursor 객체로 받을 수 있는 SQL 실행 방법
          // public Cursor rawQuery (String sql, String[] selectionArgs)
        int recordCount = cursor.getCount();  // 레코드의 전체 개수
        println("레코드 개수 : " + recordCount);

        for (int i = 0; i < recordCount; i++) {  // Cursor 객체는 처음에는 아무런 레코드를 가리키지 않으며
            cursor.moveToNext();  // 다음 결과 레코드로 넘어가기

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);

            println("레코드 #" + i + " : " + id + ", " + name + ", " + age + ", " + mobile);
        }

        cursor.close();
    }

}
