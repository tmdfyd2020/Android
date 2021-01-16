package org.techtown.sampleframelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView2;

    int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        // ID를 사용하여 뷰를 찾는 메소드, id가 인자로 들어간다.
    }
    // XML 레이아웃 파일에서 id 지정할 때 -> @+id/아이디
    // 자바 소스 코드에서 참조할 때 -> R.id.아이디

    public void onButton1Clicked(View v) {
        changeImage();
    }

    private void changeImage() {
        if (imageIndex == 0) {  // 첫 번째 이미지뷰가 화면에 보일 때
            imageView.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.INVISIBLE);

            imageIndex = 1;
        }
        else if (imageIndex == 1) {  // 두 번째 이미지뷰가 화면에 보일 때
            imageView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.VISIBLE);

            imageIndex = 0;
        }
    }
}

// 하나의 화면은 XML 파일과 소스 파일로 나누어져 있다. 이 두 개의 파일이 쌍을 이루면서 하나의 화면을 만든다.

// 소스 파일에 있는 setContentView 메소드가 파라미터로 XML 파일을 넘겨줄 수 있는데, 이 파라미터를 이용해
// 소스 파일과 XML 파일이 연결된다.

// 화면에서 이미지가 바뀌어 보이는 것처럼 보이지만 실제로는 중첩된 이미지 상태에서 하나를 보이게 하거나
// 하나를 보이지 않게 하는 방법을 사용한 것이다.