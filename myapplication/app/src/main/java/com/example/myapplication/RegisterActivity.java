package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNickname, etPwd, etPwdConfirm, etSignature;
    private Spinner spGender;
    private Button btnRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化数据库帮助类
        dbHelper = new DBHelper(this);

        // 绑定控件
        initView();

        // 注册按钮点击事件
        btnRegister.setOnClickListener(v -> checkRegisterForm());
    }

    // 初始化控件
    private void initView() {
        etNickname = findViewById(R.id.et_nickname);
        etPwd = findViewById(R.id.et_pwd);
        etPwdConfirm = findViewById(R.id.et_pwd_confirm);
        etSignature = findViewById(R.id.et_signature);
        spGender = findViewById(R.id.sp_gender);
        btnRegister = findViewById(R.id.btn_register);
    }

    // 注册表单校验 + 数据库存储
    private void checkRegisterForm() {
        String nickname = etNickname.getText().toString().trim(); // 账号用昵称替代
        String pwd = etPwd.getText().toString().trim();
        String pwdConfirm = etPwdConfirm.getText().toString().trim();
        String signature = etSignature.getText().toString().trim();
        String gender = spGender.getSelectedItem().toString();

        // 1. 不为空检查
        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称（账号）不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwdConfirm.isEmpty()) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. 密码二次确认匹配检查
        if (!pwd.equals(pwdConfirm)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. 检查账号是否已存在（SQLite 查重）
        if (dbHelper.isAccountExists(nickname)) {
            Toast.makeText(this, "该账号已存在，请更换昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. 写入 SQLite 数据库
        long result = dbHelper.insertUser(nickname, pwd, signature, gender);
        if (result == -1) {
            Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
            return;
        }

        // 5. 注册成功：回传信息到登录页
        Intent intent = new Intent();
        intent.putExtra("account", nickname); // 账号=昵称
        intent.putExtra("password", pwd);     // 密码
        setResult(RESULT_OK, intent); // 设置返回结果

        // 6. 提示+自动跳转回登录页
        Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
        finish(); // 关闭注册页，返回登录页
    }
}
