package com.example.myqq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText etAccount, etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);

        findViewById(R.id.btn_go_register).setOnClickListener(v ->
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 100));

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, FriendListActivity.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            etAccount.setText(data.getStringExtra("account"));
            etPwd.setText(data.getStringExtra("pwd"));
        }
    }
}