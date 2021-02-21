package org.techtown.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {  // SQLiteOpenHelper 클래스를 상속하여 새로운 클래스 정의하기
    public static String NAME = "employee.db";
    public static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {  // 데이터베이스가 생성될 때 호출
        println("onCreate 호출됨");

        /* 테이블 만들기 */
        String sql = "create table if not exists emp("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " mobile text)";

        // 이렇게 만들어진 헬퍼 클래스는 메인 액티비티에서 데이터베이스를 사용할 경우 코드가 더 간단해진다.

        db.execSQL(sql);  // onCreate() 메서드 안에서 SQL문 실행하기
    }

    public void onOpen(SQLiteDatabase db) {  // 데이터베이스를 열 때 호출
        println("onOpen 호출됨");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // 데이터베이스를 업그레이드할 때 호출
        println("onUpgrade 호출됨 : " + oldVersion + " -> " + newVersion);

        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

    public void println(String data) {
        Log.d("DatabaseHelper", data);
    }
}
