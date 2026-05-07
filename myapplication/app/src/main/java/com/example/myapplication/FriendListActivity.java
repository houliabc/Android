package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView rvFriendList;
    private FriendAdapter friendAdapter;
    private List<Friend> friendList;

    private LinearLayout llMainContent;
    private FrameLayout flFragmentContainer;
    private Button btnSettings;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // 初始化控件
        initView();

        // 1. 初始化好友数据（模拟数据）
        initFriendData();

        // 2. 设置RecyclerView布局管理器（线性布局）
        rvFriendList.setLayoutManager(new LinearLayoutManager(this));

        // 3. 设置适配器
        friendAdapter = new FriendAdapter(friendList);
        rvFriendList.setAdapter(friendAdapter);

        // 4. 设置按钮点击事件
        btnSettings.setOnClickListener(v -> showSettingsFragment());
    }

    private void initView() {
        rvFriendList = findViewById(R.id.rv_friend_list);
        llMainContent = findViewById(R.id.ll_main_content);
        flFragmentContainer = findViewById(R.id.fl_fragment_container);
        btnSettings = findViewById(R.id.btn_settings);
    }

    // 显示设置 Fragment
    private void showSettingsFragment() {
        // 隐藏主内容，显示 Fragment 容器
        llMainContent.setVisibility(View.GONE);
        flFragmentContainer.setVisibility(View.VISIBLE);

        // 创建或显示 SettingsFragment
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
            settingsFragment.setOnSettingsBackListener(this::hideSettingsFragment);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (settingsFragment.isAdded()) {
            transaction.show(settingsFragment);
        } else {
            transaction.add(R.id.fl_fragment_container, settingsFragment);
        }
        transaction.commit();
    }

    // 隐藏设置 Fragment，返回主界面
    private void hideSettingsFragment() {
        // 隐藏 Fragment 容器，显示主内容
        flFragmentContainer.setVisibility(View.GONE);
        llMainContent.setVisibility(View.VISIBLE);
    }

    // 模拟好友数据
    private void initFriendData() {
        friendList = new ArrayList<>();
        friendList.add(new Friend("张三"));
        friendList.add(new Friend("李四"));
        friendList.add(new Friend("王五"));
        friendList.add(new Friend("赵六"));
        friendList.add(new Friend("小明"));
        friendList.add(new Friend("小红"));
        friendList.add(new Friend("小李"));
        friendList.add(new Friend("小张"));
    }
}
