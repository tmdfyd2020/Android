package org.techtown.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위험 권한을 부여할 권한 지정하기
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        checkPermissions(permissions);  // 정해준 권한들에 대해서 그 권한이 부여되어 있는지를 먼저 확인한다.

    }

    public void checkPermissions(String[] permissions) {
        ArrayList<String> targetList = new ArrayList<String>();
 
        for (int i = 0; i < permissions.length; i++) {
            String curPermission = permissions[i];
            int permissionCheck = ContextCompat.checkSelfPermission(this, curPermission);  // 권한이 부여되어 있는지 확인

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, curPermission + " 권한 있음.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, curPermission + " 권한 없음.", Toast.LENGTH_LONG).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, curPermission)) {
                    Toast.makeText(this, curPermission + " 권한 설명 필요함.", Toast.LENGTH_LONG).show();
                } else {
                    targetList.add(curPermission);
                }
            }
        }

        String[] targets = new String[targetList.size()];
        targetList.toArray(targets);

        ActivityCompat.requestPermissions(this, targets, 101);  // 위험 권한 부여 요청하는 대화상자 띄워주기
          // 사용자가 수락했는지 거부했는지 여부를 콜백 메서드로 받아 확인하는 것이 필요하다.

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {  // 요청 코드와 함께 사용자가 권한을 수락했는지 여부가 인수로 전달됨
                                                            // 여러 권한을 한 번에 요청할 수도 있으니 grantResults 배열 변수 안에 수락 여부를 넣어 전닿함
                                                            // 앞서 위험 권한 2개를 요청했으므로 grantResults 배열이 길이는 2
        switch (requestCode) {  // 요청 코드가 맞는지 확인함
            // 사용자가 권한을 수락했는지 여부를 확인함
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 사용자가 권한을 수락했다면 PackageManager.PERMISSION_GRANTED 상수가 결과 값으로 확인된다.
                    Toast.makeText(this, "첫번째 권한을 사용자가 승인함.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "첫번째 권한 거부됨.", Toast.LENGTH_LONG).show();
                }

                return;
            }
        }
    }

}
