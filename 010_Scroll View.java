package org.techtown.samplescrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

// □ 스크롤뷰는 추가된 뷰의 영역이 한눈에 다 보이지 않을 때 사용한다.
public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;
    ImageView imageView;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);
        imageView = findViewById(R.id.imageView); // □ 레이아웃에 정의된 뷰 객체 참조
        scrollView.setHorizontalScrollBarEnabled(true); // □ 수평 스크롤바 사용 기능 설정

        // □ 리소스 이미지 참조
        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.image01);
        // □ 주의! 이미지 파일 이름에는 대문자를 사용할 수 없다! 특수 문자 또한 _ 기호 정도만 넣을 수 있고, 첫 글자는 숫자가 아닌 알파벳이어야 한다.
        // □ 프로젝트에 추가된 이미지들은 getDrawable 메소드를 이용해 코드에서 BitmapDrawable 객체로 만들어진다.
        // □ getDrawable 메소드는 Resources 객체에 정의되어 있으며 액티비티에 정의된 getRosources() 메소드를 이용하면 객체를 참조할 수 있다.
        int bitmapWidth = bitmap.getIntrinsicWidth(); // □ 원본 이미지의 가로 크기
        int bitmapHeight = bitmap.getIntrinsicHeight(); // □ 원본 이미지의 세로 크기

        // □ 이미지 리소스와 이미지 크기 설정
        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;
    }

    public void onButton1Clicked(View v) {
        changeImage();
    }

    // □ 다른 이미지 리소스로 변경
    private void changeImage() {
        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.image02);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;
    }
}
