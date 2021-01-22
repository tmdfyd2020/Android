package org.techtown.switchimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.techtown.switchimage.R;

public class MainActivity extends AppCompatActivity {
    ImageView topImage, bottomImage;
    BitmapDrawable bitmap;

    static int imageSwitch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topImage = (ImageView)findViewById(R.id.topImage) ;  // □ 레이아웃에 정의된 뷰 객체 참조
        bottomImage = (ImageView)findViewById(R.id.bottomImage);  // □ 레이아웃에 정의된 뷰 객체 참조

        // □ 리소스 이미지 참조
        Resources res = getResources();
        bitmap = (BitmapDrawable)res.getDrawable(R.drawable.image01);
        // □ getDrawable : Resources 객체에 정의되어 있으며 액티비티에 정의된 getRosources() 메소드를 이용하면 객체를 참조할 수 있다.
        setImage(null, topImage);
    }

    // □ oldImageView의 이미지를 지우고, newImageView에 이미지를 띄운다.
    // □ 단, oldImageView에 이미지가 없는 경우는 oldImageView의 이미지를 지우는 작업은 하지 않는다.
    public void setImage(ImageView oldImageView, ImageView newImageView){
        if (oldImageView != null) {
            oldImageView.setImageDrawable(null);
        }
        // □ 이미지 리소스와 이미지 크기 설정
        newImageView.setImageDrawable(bitmap);
        newImageView.getLayoutParams().width = bitmap.getIntrinsicWidth();  // □ 원본 이미지의 가로 크기
        newImageView.getLayoutParams().height = bitmap.getIntrinsicHeight();  // □ 원본 이미지의 가로 크기
    }

    public void onDownButtonClicked(View v){
        if (imageSwitch == 0){
            setImage(topImage, bottomImage);
            imageSwitch = 1;
        }
    }

    public void onUpButtonClicked(View v){
        if (imageSwitch == 1){
            setImage(bottomImage, topImage);
            imageSwitch = 0;
        }
    }
}
