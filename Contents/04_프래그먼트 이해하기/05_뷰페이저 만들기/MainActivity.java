package org.techtown.pager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);  // 미리 로딩해 놓을 아이템의 개수 : 3개

        MyPagerAdapter adapter = new MyPagerAdapter(this);
        // FragmentActivity는 이미 Activity 형이므로 getActivity를 사용할 수 없다. 따라서 단지 this를 불러서 작동하게 할 수 있다.
        /*
        문제 해결 능력 : 교재에서는 getSupportFragmentManager을 사용하여 Adapter를 불러왔는데 ViewPager2로 바꾸고 나서
        생성자 매개변수에서 FragmentManger 대신 FragmentActivity를 쓰면서 더 이상 getSupportFragmentManager를 사용할 수 없게 되면서 난감
        */
        Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);  // Adapter 객체에 추가
        Fragment2 fragment2 = new Fragment2();
        adapter.addItem(fragment2);
        Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);  // Adapter 객체가 설정되고 이때부터 뷰페이저는 Adapter에 있는 프래그먼트들을 화면에 보여줄 수 있다.

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });

    }

    class MyPagerAdapter extends FragmentStateAdapter { // 뷰페이저 안에 있는 각각의 화면을 Fragment로 구성한다. Fragment를 관리하는 것을 Adapter로 구성한다.
        // Version 2 : 많거나 알 수 없는 수의 프래그먼트를 통해 페이징하는 경우 extends FragmentStatePagerAdapter -> extedns FragmentStateAdapter
        ArrayList<Fragment> items = new ArrayList<Fragment>();  // 프래그먼트들을 담아둘 ArrayList

        public MyPagerAdapter(FragmentActivity fa) {  // Version 2 : FragmentManager -> FragmentActivity
            super(fa);
        }

        @NonNull
        @Override  // Implement Method 1
        public Fragment createFragment(int position) {  // Version 2 : getItem(int position) -> createFragment(int position)
            return items.get(position);
        }

        @Override  // Implement Method 2
        public int getItemCount() {  // Version 2 : getCount() -> getItemCount()
            return items.size();
        }

        public void addItem(Fragment item) {  // ArrayList에 추가
            items.add(item);
        }
    }
}
