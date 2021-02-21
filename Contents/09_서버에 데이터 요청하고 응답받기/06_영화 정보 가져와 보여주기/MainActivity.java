package org.techtown.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;  // URL 입력창
    TextView textView;  // 영화 제목

    static RequestQueue requestQueue;  // 요청 큐는 한 번만 만들면 계속 사용할 수 있기 때문에 static 키워드로 클래스 변수를 선언

    // 선택 위젯 : 여러 개의 아이템 중에 하나를 선택할 수 있는 리스트 모양의 위젯
    // 선택 위젯은 데이터를 넣을 때 위젯이 아닌 Adapter에 설정해야 하며 화면에 보이는 뷰도 Adapter에서 만든다.
    RecyclerView recyclerView;
      // 리싸이클러뷰는 처음 만들어질 때부터 레이아웃을 유연하게 구성할 수 있도록 설계되었고
      //   각각의 아이템이 화면에 보일 때 메모리를 효율적으로 사용하도록 캐시 메커니즘이 구현되어 있다.
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());  // 요청 큐 생성
        }

        recyclerView = findViewById(R.id.recyclerView);  // XML 레이아웃에 정의한 리싸이클러뷰 객체 참조하기

        /* 리싸이클러뷰에 레이아웃 매니저 설정하기 */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // 레이아웃 매니저는 리싸이클러뷰가 보일 기본적인 형태를 설정할 때 사용하는데 자주 사용하는 형태는 세로 방향, 가로 방향, 격자 모양이다.
        // 가로 방향 : HORIZONTAL, 세로 방향 : VERTICAL
        // 격자 모양으로 보여주고 싶다면 GridLayoutManager 객체를 사용하면서 칼럼의 수를 지정하면 된다.
        // 세로 방향은 한 줄에 하나를 보여주고 하단으로 나열되는 방식이라면, 격자 모양은 한 줄에 하나 이상을 보여주는 방식이다.
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();  // 어댑터 생성
        recyclerView.setAdapter(adapter);  // 리싸이클러뷰에 어댑터 설정하기

    }


    public void makeRequest() {
        String url = editText.getText().toString();

        /* 요청을 보내기 위한 StringRequest 객체 생성하기 */
        // 요청 객체는 Volley 라이브러리 안에 있는 StringRequest로 문자열을 주고받기 위해 사용하는 요청 객체이다.
        /* 요청 객체를 new 연산자로 만들 때는 네 개의 파라미터를 전달할 수 있다.
           ○ parameter 1 : GET() 또는 POST() 메서드를 전달 -> 요청 방식을 지정한다.
           ○ parameter 2 : 웹 사이트 주소
           ○ parameter 3 : 응답 받을 리스너 객체를 전달한다.
             이 리스너의 onResponse() 메서드는 응답을 받았을 때 자동으로 호출된다.
           ○ parameter 4 : 에러가 발생했을 때 호출될 리스너 객체를 전달한다.
           □ 여기에서는 GET 방식을 사용했지만 POST 방식을 사용하면서 요청 파라미터를 전달하고자 하면
               getParams() 메서드에서 반환하는 HashMap 객체에 파라미터 값들을 넣어주면 된다.
        */
        StringRequest request = new StringRequest(
                Request.Method.GET,  // parameter 1
                url,                 // parameter 2
                new Response.Listener<String>() {  // parameter 3
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> " + response);

                        processResponse(response);  // 응답을 받았을 때 processResponse() 메서드 호출하기
                    }
                },
                new Response.ErrorListener() {  // parameter 4
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };

        request.setShouldCache(false);  // 요청 객체에 캐시 메커니즘을 지원하지 않는다. 즉, 이전 응답 결과를 사용하지 않는다.
        requestQueue.add(request);  // add() 메서드로 요청 객체를 넣으면 요청 큐가 자동으로 요청과 응답 과정을 진행한다.
        println("요청 보냄.");
    }

    public void println(String data) {
        Log.d("MainActivity", data);
    }

    public void processResponse(String response) {  // Gson을 이용해 (JSON 문자열 -> MovieList 객체)로 변환하며 그 안에 들어있는 Movie 객체들을 꺼내어 어댑터에 추가한다.
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);  // 응답받은 JSON 문자열을 MovieList로 변환하기

        println("영화정보의 수 : " + movieList.boxOfficeResult.dailyBoxOfficeList.size());

        for (int i = 0; i < movieList.boxOfficeResult.dailyBoxOfficeList.size(); i++) {
            Movie movie = movieList.boxOfficeResult.dailyBoxOfficeList.get(i);

            adapter.addItem(movie);
        }

        adapter.notifyDataSetChanged();  // 어댑터에 모두 추가했다면 notifyDataSetChanged() 메서드를 호출해야 변경 사항이 반영된다.
    }

}
