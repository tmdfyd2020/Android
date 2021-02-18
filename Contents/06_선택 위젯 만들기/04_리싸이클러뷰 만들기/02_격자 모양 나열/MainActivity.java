package org.techtown.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        /* 리싸이클러뷰에 GridLayoutManager를 레이아웃 매니저로 설정하기 */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);  // spanCount : 칼럼의 개수
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PersonAdapter();

        adapter.addItem(new Person("김민수", "010-1000-1000"));
        adapter.addItem(new Person("김하늘", "010-2000-2000"));
        adapter.addItem(new Person("홍길동", "010-3000-3000"));
        adapter.addItem(new Person("내이름1", "010-4000-4000"));
        adapter.addItem(new Person("내이름2", "010-4000-4000"));
        adapter.addItem(new Person("내이름3", "010-4000-4000"));
        adapter.addItem(new Person("내이름4", "010-4000-4000"));
        adapter.addItem(new Person("내이름5", "010-4000-4000"));
        adapter.addItem(new Person("내이름6", "010-4000-4000"));
        adapter.addItem(new Person("내이름7", "010-4000-4000"));
        adapter.addItem(new Person("내이름8", "010-4000-4000"));
        adapter.addItem(new Person("내이름9", "010-4000-4000"));
        adapter.addItem(new Person("내이름10", "010-4000-4000"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnPersonItemClickListener() {  // 어댑터에 리스터 설정하기
            @Override                       // 아이템이 클릭되었을 때 이 리스너의 onItemClick() 메서드가 호출된다.
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                Person item = adapter.getItem(position);  // 아이템 클릭 시 어댑터에서 해당 아이템의 Person 객체 가져오기

                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
