package com.example.myqq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etNick, etPwd, etConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNick = findViewById(R.id.et_reg_nick);
        etPwd = findViewById(R.id.et_reg_pwd);
        etConfirm = findViewById(R.id.et_reg_confirm);

        findViewById(R.id.btn_register).setOnClickListener(v -> {
            String nick = etNick.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();

            if (nick.isEmpty() || pwd.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pwd.equals(confirm)) {
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("account", nick);
            intent.putExtra("pwd", pwd);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}