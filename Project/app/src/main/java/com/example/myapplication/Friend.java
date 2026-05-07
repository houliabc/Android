package com.example.myapplication;

// 好友数据实体类
public class Friend {
    // 好友昵称
    private String nickname;

    public Friend(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}