package org.techtown.capture.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    /*
    카메라로 사진을 찍기 위해 사용되는 방법 2가지
    ○ 인텐트로 단말의 카메라 앱을 실행한 후 결과 사진을 받아 처리하기
    ○ 앱 화면에 카메라 미리보기를 보여주고 직접 사진을 찍어 처리하기
    */
    ImageView imageView;

    File file;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();  // Go to down !!
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void takePicture() {
        try {
            file = createFile();  // Go to down !!
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, "org.techtown.capture.intent.fileprovider", file);
            // 카메라 앱에서 공유하여 사용할 수 있는 파일 정보를 Uri 객체로 만들 수 있다.
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  // 단말의 카메라 앱을 띄워달라는 액션 정보
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);  // Uri 객체는 MediaStore.EXTRA_OUTPUT 키를 사용해서 인텐트에 부가 데이터로 추가된다.

        if (intent.resolveActivity(getPackageManager()) != null) {  // resloveActivity() : Intent에서 지정한 액티비티가 있는지 확인할 때 사용한다.
            startActivityForResult(intent, 101);  // 사진 찍기 화면 띄우기
        }
    }

    private File createFile() {
        String filename = "capture.jpg";  // capture.jpg라는 이름으로 SD 카드에 파일을 만든다.

        File outFile = new File(getExternalCacheDir(), filename);
        // 이렇게 만든 파일을 카메라 앱이 사용할 때는 다른 앱에서 파일을 공유해야 하므로 내용 제공자(Content Provider)를 만들어 해당 폴더를 공유할 수 있게 한다.
        // 안드로이드 버전 7.0 이후부터는 file://로 시작하는 Uri 정보를 다른 앱에서 접근할 수 없으며, 반드시 content://로 시작하는 내용 제공자를 사용해야 한다.
        return outFile;
    }

    /*
    ○ 인텐트 객체를 만들어 카메라 앱을 실행한 후 사진을 찍고 나면 카메라 앱의 액티비티를 닫게 되는데 그 때 응답하는 부분이 onActivityResult() 메서드이다.
      onActivityResult가 호출되면 카메라 앱에서 찍은 사진을 파일에서 확인할 수 있다.
      파일은 capture.jpg라는 이름으로 지정했으므로 이 파일을 읽어 들여 이미지뷰에 설정한다.
    ○ 이미지 파일을 읽어 들여 이미지뷰에 설정하려면 먼저 파일을 비트맵 객체로 만든다. # 비트맵 객체 = 메모리에 만들어지는 이미지
    ○ 비트맵 객체를 만들 때는 어떤 비율로 축소하여 만들 것인지 지정할 수 있다.
      일반적으로 카메라 해상도가 높은 경우 비트맵 객체의 크기도 커지므로 적당한 비율로 축소하여 만들게 된다.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);  // 이미지뷰에 Bitmap 설정하기
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
