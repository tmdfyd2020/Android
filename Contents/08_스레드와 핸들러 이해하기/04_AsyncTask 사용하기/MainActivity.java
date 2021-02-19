package org.techtown.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    BackgroundTask task;
    int value;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        Button button = findViewById(R.id.button);  // 실행 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 테스크 객체를 만들어 실행하기 */
                task = new BackgroundTask();
                task.execute();
            }
        });

        Button button2 = findViewById(R.id.button2);  // 취소 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
            }
        });
    }

    // 백그라운드 작업을 수행할 클래스는 BackgroundTask라는 이름의 클래스로 정의하고 AsyncTask 클래스를 상속받는다.
    // AsyncTask 클래스는 추상 클래스이므로 새로 정의한 클래스에서는 필요한 메서드를 다시 정의하여 사용한다.
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        // 세 개의 자료형은 각각 doInBackground(), onProgressUpdate(), onPostExecute() 메서드의 파라미터를 결정한다.
        protected void onPreExecute() {  // 초기화 단계에서 사용되므로 값을 저장하기 위해
            value = 0;                   // 메인 액티비티에 정의한 value 값을 0으로 초기화하고
            progressBar.setProgress(value); // 프로그레스바도 0으로 초기화
        }

        /* 테스크 객체 안에서 백그라운드 작업 수행하도록 하기 */
        protected Integer doInBackground(Integer ... values) {
            while (isCancelled() == false) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    publishProgress(value);  // 중간중간 진행 상태를 UI에 업데이트하도록 만들기 위해 publishProgress() 메서드 호출
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {}
            }

            return value;
        }

        protected void onProgressUpdate(Integer ... values) {  // doInBackground() 메서드 안에서 publishProgress() 메서드가 호출될 때마다 자동으로 호출됨
            progressBar.setProgress(values[0].intValue());  // 프로그레스바의 값을 전달된 파라미터의 값으로 설정한다.
        }

        protected void onPostExecute(Integer result) {  // 프로그레스바의 끝까지 가면 0으로 초기화
            progressBar.setProgress(0);
        }

        protected void onCancelled() {  // 취소 버튼을 눌러서 취소하면 onCancelled() 메서드가 호출되고 0으로 초기화
            progressBar.setProgress(0);
        }
    }
}
