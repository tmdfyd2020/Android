package org.techtown.request;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText editText;
    TextView textView;

    static RequestQueue requestQueue;  // 요청 큐는 한 번만 만들면 계속 사용할 수 있기 때문에 static 키워드로 클래스 변수를 선언

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

        /* RequestQueue 객체 생성하기 */
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());   // 요청 큐 생성
        }

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
        * */
        StringRequest request = new StringRequest(
                Request.Method.GET,  // para 1
                url,                 // para 2
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> " + response);

                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        ) {
            /* 요청 파라미터를 처리하는 메소드이다. 요청 파라미터란, 웹에서 서버로 데이터를 보내는 방법 */
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
        textView.append(data + "\n");
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);  // JSON 문자열을 MovieList 객체로 변환하기
          // parameter 1 : 문자열
          // parameter 2 : 어떤 클래스인지를 저장하기 위한 클래스 객체
          // 이렇게 변환하면 응답 JSON에 들어있는 영화 정보의 개수를 바로 확인할 수 있다.

        println("영화정보의 수 : " + movieList.movieListResult.movieList.size());
    }

}
