package org.techtown.tutorial6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) { // ☆
        super.onCreate(bundle);  // ☆
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_sub);
        TextView nameText = (TextView) findViewById(R.id.nameText);
        Intent intent = getIntent();  // ☆
        nameText.setText(intent.getStringExtra("nameText").toString());
    }
}

