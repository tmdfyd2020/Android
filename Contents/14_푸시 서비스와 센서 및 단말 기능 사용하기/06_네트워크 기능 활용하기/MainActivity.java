package org.techtown.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    WiFiReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);  // 연결상태 확인 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();  // Go to down !!
            }
        });

        wifiReceiver = new WiFiReceiver();
    }

    public void checkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  // ConnectivityManager 객체 확인하기

        NetworkInfo info = manager.getActiveNetworkInfo();
        // getActiveNetworkInfo() 메서드를 호출하면 NetworkInfo 객체가 반환되고, 이 객체에는 인터넷 연결 여부와 연결 방식에 대한 정보가 들어있다.

        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                println("WiFi로 설정됨");
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                println("일반망으로 설정됨");
            }

            println("연결 여부 : " + info.isConnected());  // 연결 여부 확인하기
        } else {
            println("데이터통신 불가");
        }

    }

    public void println(String data) {
        textView.append(data + "\n");
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(wifiReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();  // 수신자가 받을 메시지를 지정하기 위해 IntentFilter 객체를 만들었다.
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);  // 액션 추가
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);  // 액션 추가
        // 이렇게 하면 무선랜의 상태와 네트워크 상태를 전달받을 수 있다.
        registerReceiver(wifiReceiver, filter);  // 코드에서 수신자 등록하기
    }

    class WiFiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {  // WiFi 상태 체크하기
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiManager.WIFI_STATE_ENABLED) {
                    println("WiFi enabled");
                } else if (state == WifiManager.WIFI_STATE_DISABLED) {
                    println("WiFi disabled");
                }
            } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                String ssid = manager.getConnectionInfo().getSSID();

                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    println("Connected : " + ssid);
                } else if (info.getState() == NetworkInfo.State.DISCONNECTED) {
                    println("Disconnected : " + ssid);
                }
            }
        }
    }

}
