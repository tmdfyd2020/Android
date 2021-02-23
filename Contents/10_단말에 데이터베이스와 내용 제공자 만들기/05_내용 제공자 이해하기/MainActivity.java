package org.techtown.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPerson();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPerson();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });
    }

    public void insertPerson() {
        println("insertPerson 호출됨");

        String uriString = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriString);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
          // query() 메서드를 호출하면 Cursor 객체가 반횐된다.
        String[] columns = cursor.getColumnNames();
          // Cursor 객체를 이용해 결과 값을 조회할 수 있는데 결과 레코드에 들어가 있는 칼럼의 이름을 조회하고 싶다면 getColumnNames() 메서드를 사용할 수 있다.
        println("columns count -> " + columns.length);
        for (int i = 0; i < columns.length; i++) {
            println("#" + i + " : " + columns[i]);
        }

        ContentValues values = new ContentValues();  // 레코드를 추가할 떄는 ContentValues 객체가 사용된다.
        values.put("name", "john");
        values.put("age", 20);
        values.put("mobile", "010-1000-1000");

        uri = getContentResolver().insert(uri, values);
        println("insert 결과 -> " + uri.toString());
    }

    public void queryPerson() {
        try {
            String uriString = "content://org.techtown.provider/person";
            Uri uri = new Uri.Builder().build().parse(uriString);

            String[] columns = new String[] {"name", "age", "mobile"};
            Cursor cursor = getContentResolver().query(uri, columns, null, null, "name ASC");
              // query() 메서드를 호출할 때 URI 객체 외에 columns도 전달하고 있는데 여기에는 조회할 칼럼의 이름이 문자열의 배열 형태로 들어가 있다.
            println("query 결과 : " + cursor.getCount());

            int index = 0;
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(columns[0]));
                int age = cursor.getInt(cursor.getColumnIndex(columns[1]));
                String mobile = cursor.getString(cursor.getColumnIndex(columns[2]));

                println("#" + index + " -> " + name + ", " + age + ", " + mobile);
                index += 1;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePerson() {
        String uriString = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriString);

        String selection = "mobile = ?";  // ? 기호는 selectionArgs 배열 변수의 첫 번째 원소로 대체된다.
        String[] selectionArgs = new String[] {"010-1000-1000"};
        ContentValues updateValue = new ContentValues();
        updateValue.put("mobile", "010-2000-2000");

        int count = getContentResolver().update(uri, updateValue, selection, selectionArgs);
                                             // URI, ContentValues 객체, where 조건, where 조건의 ? 기호를 대체할 값
        println("update 결과 : " + count);
    }

    public void deletePerson() {
        String uriString = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriString);

        String selection = "name = ?";  // ? 기호는 selectionArgs 배열 변수의 첫 번째 원소로 대체된다.
        String[] selectionArgs = new String[] {"john"};

        int count = getContentResolver().delete(uri, selection, selectionArgs);
                                             // URI, where 조건, where 조건의 ? 기호를 대체할 값
        println("delete 결과 : " + count);
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
