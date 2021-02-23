package org.techtown.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
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
                chooseContacts();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);  // 위험 권한 부여를 위한 코드
    }

    /* 위험 권한이 정상적으로 부여됐는지 확인하기 위한 3개의 메서드 */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    public void chooseContacts() {  // 연락처 가져오기라는 버튼 클릭 시 실행
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);  // 연락처 화면을 띄우기 위한 인텐트 만들기
        // parameter 1 : 액션 정보
        // parameter 2 : 연락처 정보를 조회하는데 사용되는 URI 값
        startActivityForResult(contactPickerIntent,101);  // 메서드가 호출되면 연락처를 선택할 수 있는 화면이 표시된다.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  // 사용자가 연락처를 하나 선택하면 메서드가 자동으로 호출된다.
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {  // 어떤 화면에서 넘어왔는지
                try {
                    Uri contactsUri = data.getData();  // 파라미터로 전달되는 인텐트 객체의 getData() 메서드를 호출하면 선택된 연락처 정보를 가리키는 URI 객체가 반환
                    String id = contactsUri.getLastPathSegment();  // 선택한 연락처의 id 값 확인하기
                    // id 값을 확인하는 이유는 선택한 연락처의 상세 정보가 다른 곳에 저장되어 있기 때문이다.

                    getContacts(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getContacts(String id) {
        Cursor cursor = null;
        String name = "";

        try {
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,  // 연락처의 상세 정보를 조회하는데 사용되는 Uri
                    null,
                    ContactsContract.Data.CONTACT_ID + "=?",  // id 칼럼의 이름과 함께 =?라는 글자 붙여서 전달
                    new String[] { id },
                    null);

            // query() 메서드가 반환하는 Cursor 객체를 참조했다면 결과 레코드를 화면에 출력한다.

            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));  // 이름 칼럼은 ContactsContract.Data.DISPLAY_NAME 상수로 확인할 수 있다.
                println("Name : " + name);

                // 모든 칼럼 이름 확인
                String columns[] = cursor.getColumnNames();
                for (String column : columns) {  // 모든 칼럼의 이름과 칼럼 값을 출력한다.
                    int index = cursor.getColumnIndex(column);
                    String columnOutput = ("#" + index + " -> [" + column + "] " + cursor.getString(index));
                    println(columnOutput);
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
