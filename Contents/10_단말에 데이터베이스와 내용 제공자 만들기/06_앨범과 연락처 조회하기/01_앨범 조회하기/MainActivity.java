package org.techtown.album;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void openGallery() {
        Intent intent = new Intent();  // 인텐트 객체 생성하기 why? 다른 앱의 화면을 보여줘야 하니까 ☆☆☆
        intent.setType("image/*");  // MIME 타입이 image로 시작하는 데이터 가져오기
        intent.setAction(Intent.ACTION_GET_CONTENT);  // 액션 정보 설정하기

        startActivityForResult(intent, 101);  // 앨범에서 사진을 선택할 수 있는 화면을 띄워주게 된다.
    }
    // 단말의 앨범 앱에서 사진을 선택한 후에는 onActivityResult() 메서드로 그 결과 값을 전달받을 수 있다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                Uri fileUri = data.getData();  // getData() 메서드 호출 시 Uri 자료형 값 반환
                                               // 이 값은 ContentResolver를 이용해 참조할 수 있는 이미지 파일을 가리킨다.

                ContentResolver resolver = getContentResolver();  // ContentResolver 객체 참조하기

                try {
                    InputStream instream = resolver.openInputStream(fileUri);  // ContentResolver 객체의 openInputStream() 메서드로 파일 읽어 들이기
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);  // BitmapFactory.decodeStream() : Bitmap 객체로 만들기
                    imageView.setImageBitmap(imgBitmap);  // Bitmap 객체를 이미지뷰에 설정하면 사진이 보이게 된다.

                    instream.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
