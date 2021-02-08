 package org.techtown.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import static java.sql.DriverManager.println;

 public class MainActivity extends AppCompatActivity {
     EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Log.d("Main", "onCreate 호출됨");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Main", "onStart 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Main", "onStop 호출됨");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Main", "onDestroy 호출됨");
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveState();

        Log.d("Main", "onPause 호출됨");
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadState();

        Log.d("Main", "onResume 호출됨");
    }

    // Life Cycle
     // onCreate -> onStart -> onResume -> onPause -> onStop -> onDestroy
    // 화면 전환시에는 onDestroy 호출 x
     // onResume() 메서드가 다시 호출된다.

    public void saveState() {  // 현재 입력상자에 입력된 데이터를 저장한다.
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        // 앱 안에서 간단한 데이터를 저장하거나 복원할 때는 SharedPreferences 사용
        // SharedPreferences 객체를 사용하려면 getSharedPreferences() 메서드로 참조한다.
        SharedPreferences.Editor editor = pref.edit();
        // SharedPreferences.Editor 객체는 데이터를 저장할 수 있도록 edit() 메서드를 제공하는데 edit() 메서드를 호출한 후
        editor.putString("name", editText.getText().toString()); // putOOO 메서드로 저장하려는 데이터를 설정할 수 있다.
        editor.commit();  // 데이터를 저장한 후에는 commit() 메서드를 호출해야 실제로 저장된다.
    }

    public void loadState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {
            String name = pref.getString("name", "");
            editText.setText(name);
        }
    }

}
