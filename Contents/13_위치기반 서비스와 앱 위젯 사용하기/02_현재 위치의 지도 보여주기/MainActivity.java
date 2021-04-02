package org.techtown.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    SupportMapFragment mapFragment;
    GoogleMap map;

    MarkerOptions myLocationMarker;

    // 지도를 보여주기 위해 XML 레이아웃에 추가한 프래그먼트에는 class 속성으로 SupportMapFragment라는 이름의 클래스가 할당되어 있다.
    // 이 객체는 소스 코드에서 참조할 수 있으며 SupportMapFragment 안에 들어있는 GoogleMap 객체 위에 지도가 표시된다.
    // GoogleMap 객체는 프래그먼트가 초기화된 후에 참조할 수 있는데, 레이아웃에 정의한 mapFragment 객체를 소스 코드에서 참조한 후
    //   getMapAsync() 메서드를 호출하면 GoogleMap 객체를 참조할 수 있다.
    // getMapAsync() 메서드는 내부적으로 지도를 다루는 GoogleMap 객체를 초기화하는데 비동기 방식으로 처리된다.
    // 따라서 콜백 객체를 파라미터로 전달한 후 초기화가 완료될 때 콜백 객체 안의 함수가 자동으로 호출되도록 만든다.
    // 그런 다음 추가로 몇 가지 코드를 입력하면 지도가 나타나게 된다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // XML 레이아웃에 추가했던 프래그먼트 객체 참조하고 getMapAsync() 메서드 호출하기
        mapFragment.getMapAsync(new OnMapReadyCallback() {  // 비동기 방식으로 동작하기 때문에 지도가 사용 가능하게 된 후에 onMapReady() 메서드가 자동으로 호출된다.
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("Map", "지도 준비됨.");
                map = googleMap;

            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;

                Log.d("Map", message);
            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "내 위치확인 요청함",
                    Toast.LENGTH_SHORT).show();

        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.d("Map", message);

            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        // showCurrentLocation() 메서드로 전달된 위도와 경고듸 값은 LatLng 객체로 만들면 지도 위에 표시할 수 있다.
        // LatLng 객체는 경위도 좌표로 구성된 위치를 지도에 표시할 수 있도록 정의된 객체이다.
        // 이렇게 만든 LatLng 객체로 지구상의 특정 위치를 표현할 수 있으며,
        // 구글맵 객체의 animateCamera() 메서드를 이용하여 그 위치를 중심으로 지도를 보여줄 수 있다.
        LatLng curPoint = new LatLng(latitude, longitude);  // 현재 위치의 좌표로 LatLng 객체 생성하기
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));  // 지정한 위치의 지도 영역 보여주기
        // animateCamera() 메서드는 지도의 축척(Scale)을 지정할 수 있으며, 앞서 살펴본 코드에서는 일반적으로 지도를 볼 때 사용하는 축척을 지정하였다.
        // 축척의 값이 클수록 가까이서 본 것처럼 확대되는데 만약 그 값이 1로 설정되면 가장 멀리서 보는 모습으로 보이며 값이 점점 커질수록 확대된다.

        showMyLocationMarker(curPoint);
    }

    private void showMyLocationMarker(LatLng curPoint) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);
            myLocationMarker.title("● 내 위치\n");
            myLocationMarker.snippet("● GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(curPoint);
        }
    }

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
