package org.techtown.spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String[] items = {"mike", "angel", "crow", "john", "ginnie", "sally", "cohen", "rice"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Spinner spinner = findViewById(R.id.spinner);
        // 스피너 = 윈도우의 콤보 박스

        /* API에서 제공하는 기본 어댑터들이 있기 때문에 어댑터를 직접 정의하지 않고 두 줄의 코드만 사용 */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
          // ArrayAdapter : 배열로 된 데이터를 아이템으로 추가할 때 사용한다.
          // public ArrayAdapter (Context context, int textViewResourceId, T[] objects)
          // android.R.layout.simple_spinner_item, items : 문자열을 아이템으로 보여주는 단순 스피너 아이템의 레이아웃이다.
          // items : 아이템으로 보일 문자열 데이터들의 배열이다.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          // setDropDownViewResource() : 스피너는 항목을 선택하기 위한 창이 따로 있기 때문에 항목을 선택하는 창을 위한 레이아웃도 설정해줘야 한다.
          // 이 메서드는 스피너의 각 아이템들을 보여줄 뷰에 사용되는 레이아웃을 지정하는데 사용되며
          // 여기서는 안드로이드에서 미리 정의한 리소스인 android.R.layout.simple_spinner_dropdown_item 값을 전달하면 가장 단순한 형태의 뷰가 보이게 된다.
        spinner.setAdapter(adapter);  // 스피너 객체도 선택 위젯이므로 setAdapter() 메서드의 파라미터로 어댑터 객체를 전달해야 한다.

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  // 스피너에 리스너 설정하기
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {  // 스피너 객체가 아이템 선택 이벤트를 처리할 수 있또록 사용
                textView.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });
    }
}
