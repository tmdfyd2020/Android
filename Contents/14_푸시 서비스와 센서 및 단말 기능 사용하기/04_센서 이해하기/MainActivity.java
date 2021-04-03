package org.techtown.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    SensorManager manager;
    List<Sensor> sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);  // 센서 리스트 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSensorList();  // Go to down !!
            }
        });

        Button button2 = findViewById(R.id.button2);  // 첫 번째 센서 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFirstSensor();  // Go to down !!
            }
        });
    }

    public void getSensorList() {  // 센서 리스트를 확인하여 화면에 출력한다.
        // 센서를 사용할 때는 센서 매니저 객체를 시스템 서비스로 참조하는 것부터 시작한다.
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);  // 단말에서 지원하는 모든 센서 리스트를 가져온다.
        // 센서 유형을 상수로 전달하면서 호출하면 Sensor 객체를 담고 있는 List 객체가 리턴된다.
        // public List<Sensor> getSensorList(int type)
        // 모든 센서 정보를 받기 위해 Sensor.TYPE_ALL 상수를 전달하였다.

        int index = 0;
        for (Sensor sensor : sensors) {
            println("#" + index + " : " + sensor.getName());
        }
    }

    public void registerFirstSensor() {  // 첫 번째 센서의 값을 확인하여 화면에 출력한다.
        manager.registerListener(  // 센서를 위한 리스너 설정하기
                new SensorEventListener() {
                     @Override
                     public void onSensorChanged(SensorEvent event) {  // 센서의 데이터 값이 변할 때마다 호출
                         String output = "Sensor Timestamp : " + event.timestamp + "\n\n";
                         // timestamp : 센서에서 값을 확인한 시간을 알려준다.
                         // accuracy : 값을 확인할 때의 센서 정확도를 알려준다.
                         // values : float 타입의 배열로 되어 있는데 센서의 종류에 따라 여러 개의 값을 담고 있다.
                         for(int index = 0; index < event.values.length; ++index) {
                             output += ("Sensor Value #" + (index + 1) + " : " + event.values[index] + "\n");
                         }
                         println(output);
                     }

                     @Override
                     public void onAccuracyChanged(Sensor sensor, int accuracy) {  // 센서의 정확도 값이 변할 때마다 호출

                     }
                },
                sensors.get(0),
                SensorManager.SENSOR_DELAY_UI);
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}
