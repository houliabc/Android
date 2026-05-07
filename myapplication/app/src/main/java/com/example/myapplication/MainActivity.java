package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // 声明控件
    private EditText etName, etPhone, etPwd, etPwdConfirm;
    private RadioGroup rgGender;
    private Button btnSubmit;

    // 手机号正则表达式（验证11位国内手机号）
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        initView();

        // 提交按钮点击事件
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForm(); // 校验表单
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etPwd = findViewById(R.id.et_pwd);
        etPwdConfirm = findViewById(R.id.et_pwd_confirm);
        rgGender = findViewById(R.id.rg_gender);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    /**
     * 表单验证核心逻辑
     */
    private void checkForm() {
        // 1. 获取输入内容
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String pwdConfirm = etPwdConfirm.getText().toString().trim();

        // 2. 非空验证
        if (name.isEmpty()) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwdConfirm.isEmpty()) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. 手机号格式验证
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            Toast.makeText(this, "请输入正确的11位手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. 密码长度验证
        if (pwd.length() < 6) {
            Toast.makeText(this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        // 5. 密码一致性验证
        if (!pwd.equals(pwdConfirm)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        // 6. 性别必选验证
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }

        // 7. 所有验证通过 → 执行提交逻辑
        String gender = rgGender.getCheckedRadioButtonId() == R.id.rb_male ? "男" : "女";
        Toast.makeText(this,
                "注册成功！\n姓名：" + name + "\n手机号：" + phone + "\n性别：" + gender,
                Toast.LENGTH_LONG).show();

        // 这里可以添加提交到服务器、本地存储等业务逻辑
    }
}