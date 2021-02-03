package org.techtown.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        // 프래그먼트는 뷰가 아니라서 Activity 클래스에 있는 findViewById() 메서드로 찾을 수 없다.
        // 프래그먼트를 관리하는 FragmentManager 객체의 findFragmentById() 메서드를 사용해서 찾을 수 있다.
        menuFragment = new MenuFragment();
    }

    public void onFragmentChanged(int index) {
        if (index == 0) {  // 인수로 전달된 정수의 값이 0이면 메인 프래그먼트가 보이게 한다.
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
            // 메인 액티비티에서 프래그먼트를 다루기 위해서는 먼저 getSupportFragmentManager() 메서드를 호출하여 매니저 객체를 참조한다.
            // 프래그먼트 매니저는 프래그먼트를 다루는 작업을 해주는 객체로 프래그먼트 추가, 삭제 또는 교체의 작업을 할 수 있게 한다.
            // 이런 작업들은 프래그먼트를 변경할 때 오류가 생기면 다시 원래 상태로 돌릴 수 있어야 하므로 트랜잭션 객체를 만들어 실행한다.
            // 트랜잭션 객체는 beginTransaction() 메서드를 호출하면 시작되고 commit() 메소드를 호출하면 실행한다.
        }
        else if (index == 1) {  // 1이면 메뉴 프래그먼트가 보이게 한다.
            getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).commit();
        }
    }
}
