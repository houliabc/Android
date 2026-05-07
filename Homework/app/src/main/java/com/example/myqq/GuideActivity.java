package com.example.myqq;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findViewById(R.id.btn_enter).setOnClickListener(v -> {
            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
            finish();
        });
    }
}