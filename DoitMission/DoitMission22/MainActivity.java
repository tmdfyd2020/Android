package org.techtown.doitmission_22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements fragment1.sendDataListener {

    TabLayout tabs;

    fragment1 fragment1;
    fragment2 fragment2;

    Book book;
    ArrayList<Book> books = new ArrayList<Book>();

    SQLiteDatabase database;
    String tablename = "bookrecords";
    DatabaseHelper databaseHelper;

    int beforeposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);

        fragment1 = new fragment1();
        fragment2 = new fragment2();

        createDatabase("library");  // Go to down
        createTable(tablename);  // Go to down

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();  // 첫 프레그먼트 호출

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;

                if(position == 0) {  // 입력
                    selected = fragment1;
                }
                else if(position == 1) {  // 조회
                    selected = fragment2;
                    exequteQuery();  // Go to down
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();  // 화면 전환의 좋은 예시 코드
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void sendData(Book book) {  // fragment1.sendDataListener 인터페이스 내부 함수 구현
        this.book = book;
        Toast.makeText(getApplicationContext(), book.getName() + " ", Toast.LENGTH_LONG).show();

        insertRecord();  // Go to down
    }

    public void exequteQuery() {  // 데이터베이스 조회하기
        println("exequteQuery 호출됨");

        Cursor cursor = database.rawQuery("select _id, name, writer, contents from " + tablename , null);
        int recordCount = cursor.getCount();  // 데이터 개수
        println("레코드 갯수 : " + recordCount);

        for(int i = beforeposition; i < recordCount; i++) {  // 데이터 개수만큼
            cursor.moveToPosition(i);

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String writer = cursor.getString(2);
            String contents =cursor.getString(3);

            println("레코드 #" + i + " : " + id + ", " + name + ", " + writer + ", " + contents);
            books.add(new Book(name, writer, contents));
        }
        beforeposition = recordCount; // 여기까지 읽었다고 표시
        cursor.close();
        sendData2();  // Go to down
    }

    public void sendData2() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("booklist", books);
        fragment2.setArguments(bundle);
    }

    private void createDatabase(String name) {  // 데이터베이스 생성하기
        println("createDatabase 호출됨.");

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        println("데이터베이스 생성됨 : " + name);
    }

    private void createTable(String name) {  // 데이터베이스 테이블 생성하기
        println("createTable 호출됨.");

        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        database.execSQL("create table if not exists " + name + "(" + "_id integer PRIMARY KEY autoincrement, " + " name text, " + "writer text, " + "contents text)");
        println("테이블 생성함 : " + name);
    }

    private void insertRecord() {
        println("insertRecord 호출됨.");

        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        if(tablename == null) {
            println("테이블을 먼저 생성하세요.");
            return;
        }

        database.execSQL("insert into " + tablename + "(name, writer, contents) " + " values " + "('" + book.getName() + "' , '"+ book.getWriter() + "' , '" + book.getContents() + "')" );
        println("레코드 추가됨");
    }

    public void println(String data) {
        Log.d("MainAcitivity", data);
    }
}
