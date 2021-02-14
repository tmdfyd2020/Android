package org.techtown.permission2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoPermissions.Companion.loadAllPermissions(this, 101);  // 위험 권한 자동부여 요청하기
          // 함수를 호출하면 권한 부여 요청 결과가 넘어오게 되는데 그 결과는 onRequestPermissionsResult 함수로 전달받는다.
    }

    // 위험 권한 부여에 대한 응답 처리하기
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
        // 권한 부여 결과가 승인 또는 거부로 나뉘면서 onGranted() 또는 onDenied() 함수가 호출된다.
        // 예를 들어, 권한이 여러 개인 경우에는 어떤 권하는 onGranted() 또는 어떤 권한은 onDenied로 나뉘어 호출된다.
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

}
