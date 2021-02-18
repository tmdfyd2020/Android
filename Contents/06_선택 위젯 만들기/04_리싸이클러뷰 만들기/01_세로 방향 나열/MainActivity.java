package org.techtown.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        /* 리싸이클러뷰에 레이아웃 매니저 설정하기 */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // 레이아웃 매니저는 리싸이클러뷰가 보일 기본적인 형태를 설정할 때 사용하는데 자주 사용하는 형태는 세로 방향, 가로 방향, 격자 모양이다.
        // 가로 방향 : HORIZONTAL, 세로 방향 : VERTICAL
        // 격자 모양으로 보여주고 싶다면 GridLayoutManager 객체를 사용하면서 칼럼의 수를 지정하면 된다.
          // 세로 방향은 한 줄에 하나를 보여주고 하단으로 나열되는 방식이라면, 격자 모양은 한 줄에 하나 이상을 보여주는 방식이다.
        recyclerView.setLayoutManager(layoutManager);

        PersonAdapter adapter = new PersonAdapter();

        adapter.addItem(new Person("김민수", "010-1000-1000"));
        adapter.addItem(new Person("김하늘", "010-2000-2000"));
        adapter.addItem(new Person("홍길동", "010-3000-3000"));

        recyclerView.setAdapter(adapter);  // 리싸이클러뷰에 Adapter 설정하기

    }
}
