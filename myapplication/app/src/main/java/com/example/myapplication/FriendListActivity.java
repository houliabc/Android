package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView rvFriendList;
    private FriendAdapter friendAdapter;
    private List<Friend> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // 初始化控件
        rvFriendList = findViewById(R.id.rv_friend_list);

        // 1. 初始化好友数据（模拟数据）
        initFriendData();

        // 2. 设置RecyclerView布局管理器（线性布局）
        rvFriendList.setLayoutManager(new LinearLayoutManager(this));

        // 3. 设置适配器
        friendAdapter = new FriendAdapter(friendList);
        rvFriendList.setAdapter(friendAdapter);
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