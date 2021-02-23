package org.techtown.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

public class PersonProvider extends ContentProvider {

    private static final String AUTHORITY = "org.techtown.provider";
    private static final String BASE_PATH = "person";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );
      // 내용 제공자를 만들기 위해서는 고유한 값을 가진 content URI를 만들어야 한다.
      // content:// -> 내용 제공자에 의해 제어되는 데이터라는 의미로 항상 content:// 로 시작한다.
      // AUTHORITY -> 특정 내용 제공자를 구분하는 고유의 값
      // Base Path -> 요청할 데이터의 자료형을 결정한다.
      // ID -> 맨 뒤의 숫자를 가리키며 요청할 데이터 레코드를 지정한다.

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);  // UriMatcher 객체는 URI를 매칭하는데 사용된다.
    // 두 개의 Uri를 비교하여 해당하는 값을 출력해주는 기능을 하는 class 이다.
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PERSONS);  // content://com.provider/students
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PERSON_ID);  // content://com.provider/students/1
        // addURI(String authority, String path, int code) : Match를 위한 URI와 URI가 일치할 때 리턴할 코드를 추가한다.
        // parameter 1 : 컨텐트 프로바이더 중에서 어느 authority에 해당하는지 구분할 때 사용하는 지정된 값이다.
        // parameter 2 : authority 중에서 다음 path에 해당한다.
        //               path/# 는 path 다음에 오는 모든 숫자를 지칭
        //               path/* 의 경우 숫자와 문자를 포함한다.
        // parameter 3 : match() 메소드 사용 시 일치한다면 반환하는 정수를 의미한다.
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        // 내용 제공자를 이용해 값을 조회하고 싶다면 query() 메서드를 사용한다.
        // parameter 1 : URI
        // parameter 2 : 어떤 칼럼들을 조회할 것인지 지정한다. null 값을 지정하면 모든 칼럼을 조회한다.
        // parameter 3 : SQL에서 where 절에 들어갈 조건을 지정한다. null 값을 지정하면 where 절이 없다고 처리한다.
        // parameter 4 : parameter 3에 값이 있을 경우 그 안에 들어갈 조건 값을 대체하기 위해 사용된다.
        // parameter 5 : 정렬 칼럼을 지정하며 null 값이면 정렬이 적용되지 않는다.

        Cursor cursor;  // Cursor는 데이터베이스에 저장되어 있는 테이블의 행을 참조하여 데이터를 가져오는 역할을 한다.
        switch (uriMatcher.match(uri)) {  // match() 메서드 : addURI로 등록된 URI와 입력된 URI를 비교하여 지정된 code를 리턴한다.
            case PERSONS:  // code가 PERSONS이면
                cursor =  database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.ALL_COLUMNS,
                        s,null,null,null,DatabaseHelper.PERSON_NAME +" ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
          // 내용 제공자에 접근하기 위하여 ContentResolver 객체도 사용된다.
          // 이 객체에는 내용 제공자의 URI를 파라미터로 전달하면서 데이터를 조회, 추가, 수정, 삭제하는 일이 가능하다.

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // MIME 타입이 무엇인지 알고 싶을 때 getType() 메서드를 사용한다.
          // MIME 타입이란 어떤 형식의 데이터인지 설명하는 이름으로 MIME 타입에는 text, image, audio, video 등이 있다.
        // parameter : URI
        // Return 값으로는 MIME 타입이 반환된다. 만약 MIME 타입을 알 수 없는 경우 null 값이 반횐된다.
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // 내용 제공자를 이용해 값을 추가하고 싶다면 insert() 메서드를 사용한다.
        // parameter 1 : URI
        // parameter 2 : 저장할 칼럼명과 들어간 ContentValues 객체
        // Return 값으로는 새로 추가된 값의 Uri 정보가 반환된다.

        long id = database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
              // notifyChange() 메서드 : 레코드가 추가, 수정, 삭제되었을 때 변경이 일어났음으 알려주는 역할
            return _uri;
        }

        throw new SQLException("추가 실패 -> URI :" + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        // 내용 제공자를 이용해 값을 삭제하고 싶다면 delete() 메서드를 사용한다.
        // parameter 1 : URI
        // parameter 2 : SQL에서 where 절에 들어갈 조건. null값을 지정하면 where 절이 없다고 처리한다.
        // parameter 3 : parameter 2에 값이 있을 경우 그 안에 들어갈 조건 값을 대체하기 위해 사용된다.
        // Return 값으로는 영향을 받은 레코드의 개수가 반환된다.

        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count =  database.delete(DatabaseHelper.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        // 내용 제공자를 이용해 값을 수정하고 싶다면 update() 메서드를 사용한다.
        // parameter 1 : URI
        // parameter 2 : 저장할 칼럼명과 값들이 들어간 ContentValues 객체
        // parameter 3 : SQL에서 where 절에 들어갈 조건을 지정한다. null 값을 지정하면 where 절이 없다고 처리한다.
        // parameter 4 : parameter 3에 값이 있을 경우 그 안에 들어갈 조건 값을 대체하기 위해 사용된다.
        // Return 값으로는 영향을 받은 레코드의 개수가 반환된다.

        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count =  database.update(DatabaseHelper.TABLE_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
