package org.techtown.optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        actionBar.setLogo(R.drawable.home);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_USE_LOGO);
        /*
        ○ 디스플레이 옵션 상수
          ○ DISPLAY_USE_LOGO    | 홈 아이콘 부분에 로고 아이콘을 사용
          ○ DISPLAY_SHOW_HOME   | 홈 아이콘을 표시
          ○ DISPLAY_HOME_AS_UP  | 홈 아이콘에 뒤로 가기 모양의 < 아이콘을 같이 표시
          ○ DISPLAY_SHOW_TITLE  | 타이틀을 표시
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // 액티비티가 만들어질 때 미리 자동 호출되어 화면에 메뉴 기능을 추가할 수 있도록 해준다.
        getMenuInflater().inflate(R.menu.menu_main, menu);  // XML로 정의된 메뉴 정보를 인플레이션하여 메모리에 로딩

        View v = menu.findItem(R.id.menu_search).getActionView();  // 메뉴 아이템 중에서 검색을 위해 정의한 아이템을 뷰 객체로 참조
        if (v != null) {
            EditText editText = v.findViewById(R.id.editText);

            if(editText != null) {
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // 입력상자 객체에 리스너 설정
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Toast.makeText(getApplicationContext(), "입력됨.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  // 메뉴 아이템이 클릭되었을 때 호출되는 함수
        int curId = item.getItemId();

        switch(curId) {
            case R.id.menu_refresh:
                showToast("새로고침 메뉴 선택됨");
                break;
            case R.id.menu_search:
                showToast("검색 메뉴 선택됨");
                break;
            case R.id.menu_settings:
                showToast("설정 메뉴 선택됨");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    ○ 화면이 띄워진 후에 메뉴를 바꾸고 싶다면 onPrepareOptionsMenu() 메서드를 재정의하여 사용하면 된다.
      ○ 이 함수는 메뉴가 새로 보일 때마다 호출되므로 메뉴 항목을 추가하거나 뺄 수 있어 메뉴 아이템들을 변경할 수 있다.
      ○ 특히 메뉴의 속성을 바꿀 수 있으므로 메뉴를 활성화하거나 비활성화하여 사용자에게 앱의 상태에 따라 메뉴를 사용하거나 사용하지 못하도록 만들 수도 있다.
    */

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}
