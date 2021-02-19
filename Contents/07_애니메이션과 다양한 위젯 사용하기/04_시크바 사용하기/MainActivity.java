package org.techtown.seekbar;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // 시크바에 리스너 설정하기
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {  // 사용자에 의해 변경된 progress 값을 전달받을 수 있다.
                setBrightness(i);
                textView.setText("변경된 값 : " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void setBrightness(int value) {
        if (value < 10) {
            value = 10;
        } else if (value > 100) {
            value = 100;
        }

        /* 윈도우 매니저를 이용해 화면 밝기 설정하기 */
        WindowManager.LayoutParams params = getWindow().getAttributes();
        // getWindow() 메서드를 사용해 참조한 객체의 윈도우 관련 정보를 getAttributes()로 확인하거나 새로 설정할 수 있다.
        params.screenBrightness = (float) value / 100; // 화면 밝기를 설정하는 속성 : screenBrightness
        getWindow().setAttributes(params);
    }

}
