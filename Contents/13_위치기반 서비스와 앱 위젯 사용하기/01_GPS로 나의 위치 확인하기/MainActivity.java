package org.techtown.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

        Button button = findViewById(R.id.button);  // 내 위치 확인하기 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();  // Go to down !!
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);  // 위험 권한을 자동으로 부여하기 위한 코드
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  // LocationManager 객체 참조하기
        // 위치 관리자는 시스템 서비스이므로 객체를 참조하기 위해서 getSystemService() 메서드를 사용한다.
        // 위치 관리자를 위해 정의한 상수 이름 : Context.LOCATION_SERVICE
        //   이 상수로 시스템 서비스 객체를 참조한 후 최근 위치 정보를 확인해보는 코드를 넣으면 나의 최근 위치를 확인할 수 있다.

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);  // 이전에 확인했던 위치 정보 가져오기
            // getLastKnownLocation() : 최근 위치 정보를 확인
            // para : 위치 제공자 정보
            // 안드로이드는 위치 제공자를 크게 GPS_PROVIDER와 NETWORK_PROVIDER로 구분하고 있으며, 이 두 개의 값 중 하나를 파라미터로 전달하면 된다.
            // Location 객체는 위도와 경도 값을 가지고 있으며, getLatitude()와 getLongitude() 메서드로 그 값을 확인할 수 있다.

            if (location != null) {
              double latitude = location.getLatitude();
              double longitude = location.getLongitude();
              String message = "최근 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;

              textView.setText(message);
            }

            GPSListener gpsListener = new GPSListener();  // 리스너 객체 생성
            long minTime = 10000;  // 최소 시간 10초마다 위치 정보를 전달받음
            float minDistance = 0;  // 최소 거리 0마다 위치 정보를 전달받음

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);  // 위치 요청하기
            // 위치 관리자는 일정한 시간 간격으로 위치 정보를 확인하거나 일정 거리 이상을 이동했을 때 위치 정보를 전달하는 기능을 제공한다.
            // 위치 관리자에게 현재 위치를 알려달라고 요청하기 위해서는 requestLocationUpdates() 메서드를 호출해야 하는데
            //   파라미터로는 최소 시간과 최소 거리 그리고 위치 리스너 객체가 전달되어야 한다.

            Toast.makeText(getApplicationContext(), "내 위치확인 요청함", Toast.LENGTH_SHORT).show();

        } catch(SecurityException e) {
                e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        // 위치 리스너(LocationListener)는 위치 관리자에서 전달하는 위치 정보를 받기 위해 정의된 인터페이스이다.
        // 즉, 위치 관리자가 위치 정보를 전달할 때 호출되므로 위치 정보를 받아 처리하려면 이 리스너의 onLocationChanged() 메서드를 구현해야 한다.
        public void onLocationChanged(Location location) {
            // 위치가 확인되었을 때 자동으로 호출되는 onLocationChanged() 메서드
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
            textView.setText(message);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    // 위험 권한을 자동으로 부여하기 위한 코드 추가
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
