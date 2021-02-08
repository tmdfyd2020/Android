package org.techtown.parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "mike");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Intent intent = getIntent();  // Intent 객체가 반환되므로 이 객체 안의 Bundle 객체를 참조할 수 있음
        processIntent(intent);
    }

    public void processIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras(); // Intent 안의 부가 데이터를 관리하는 것이 bundle 객체
            // getExtras() : Bundle 자료형의 객체가 반환된다.
            SimpleData data = bundle.getParcelable("data");
            if (data != null) {
                Toast.makeText(this, "전달받은 객체 : " + data.code + ", " + data.message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
