package org.techtown.tab;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;  // Alt + Enter -> androidx.appcompat.widget 패키지 안에 들어 있는 Toolbar를 import

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // style.xml에서 NoActionBar로 지정하였기 때문에 새로 설정해줘야 한다.

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
        // FrameLayout으로 지정된 공간에 fragment1을 넣는다.

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("통화기록"));  // 탭에 메뉴 추가
        tabs.addTab(tabs.newTab().setText("스팸기록"));
        tabs.addTab(tabs.newTab().setText("연락처"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // [탭] 버튼이 선택될 때마다 리스터 안에 있는 onTabSelected() 함수가 호출
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                        int position = tab.getPosition();

                        if (position == 0) { // 첫번째 탭버튼
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                        }
                        else if (position == 1) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                        }
                        else if (position == 2) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
