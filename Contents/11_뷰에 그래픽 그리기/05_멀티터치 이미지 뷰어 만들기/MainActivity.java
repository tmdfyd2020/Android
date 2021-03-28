package org.techtown.multitouch;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.container);
        // 사진 이미지를 보여줄 뷰를 XML 레이아웃에 추가하지 않고 소스 코드에서 만든 후 추가하는 방식을 사용했다.

        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.beach);  // 사진 이미지는 decodeResource() 메서드를 이용해 리소스에 저장된 것을 로딩한다.

        ImageDisplayView view = new ImageDisplayView(this);  // ImageDisplayView 객체 생성하기
        view.setImageData(bitmap);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        container.addView(view, params);
    }

}
