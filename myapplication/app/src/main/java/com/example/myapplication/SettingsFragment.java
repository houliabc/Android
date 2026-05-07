package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private CheckBox cbRememberPwd, cbAutoLogin;
    private Button btnBack, btnLogout;
    private SharedPreferences sp;
    private OnSettingsBackListener backListener;

    private static final String SP_NAME = "login_config";
    private static final String KEY_REMEMBER_PWD = "remember_pwd";
    private static final String KEY_AUTO_LOGIN = "auto_login";
    private static final String KEY_SAVED_ACCOUNT = "saved_account";
    private static final String KEY_SAVED_PASSWORD = "saved_password";

    // 返回按钮回调接口
    public interface OnSettingsBackListener {
        void onSettingsBack();
    }

    public void setOnSettingsBackListener(OnSettingsBackListener listener) {
        this.backListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // 初始化 SharedPreferences（与 LoginActivity 共用同一配置文件）
        sp = getActivity().getSharedPreferences(SP_NAME, getActivity().MODE_PRIVATE);

        // 绑定控件
        cbRememberPwd = view.findViewById(R.id.cb_settings_remember_pwd);
        cbAutoLogin = view.findViewById(R.id.cb_settings_auto_login);
        btnBack = view.findViewById(R.id.btn_settings_back);
        btnLogout = view.findViewById(R.id.btn_logout);

        // 加载当前设置状态
        loadSettings();

        // 自动登录联动：勾选自动登录时也勾选记住密码
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRememberPwd.setChecked(true);
                }
                saveSettings();
            }
        });

        // 记住密码状态变化时保存
        cbRememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // 如果取消记住密码，也取消自动登录
                    cbAutoLogin.setChecked(false);
                }
                saveSettings();
            }
        });

        // 返回按钮
        btnBack.setOnClickListener(v -> {
            saveSettings();
            if (backListener != null) {
                backListener.onSettingsBack();
            }
        });

        // 退出登录按钮
        btnLogout.setOnClickListener(v -> {
            // 清除自动登录和记住密码状态
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(KEY_AUTO_LOGIN, false);
            editor.putBoolean(KEY_REMEMBER_PWD, false);
            editor.remove(KEY_SAVED_ACCOUNT);
            editor.remove(KEY_SAVED_PASSWORD);
            editor.apply();

            // 跳转到登录页，清空 Activity 栈
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    // 从 SharedPreferences 加载设置
    private void loadSettings() {
        boolean rememberPwd = sp.getBoolean(KEY_REMEMBER_PWD, false);
        boolean autoLogin = sp.getBoolean(KEY_AUTO_LOGIN, false);
        cbRememberPwd.setChecked(rememberPwd);
        cbAutoLogin.setChecked(autoLogin);
    }

    // 保存设置到 SharedPreferences
    private void saveSettings() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_REMEMBER_PWD, cbRememberPwd.isChecked());
        editor.putBoolean(KEY_AUTO_LOGIN, cbAutoLogin.isChecked());

        // 如果取消记住密码，清除已保存的账号密码
        if (!cbRememberPwd.isChecked()) {
            editor.remove(KEY_SAVED_ACCOUNT);
            editor.remove(KEY_SAVED_PASSWORD);
        }

        editor.apply();
    }
}
