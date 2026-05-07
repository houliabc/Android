package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vpGuide;
    // 引导页数据：标题+背景色
    private List<GuideBean> guideList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // 检查自动登录状态：如果开启了自动登录，直接跳转到好友列表页
        SharedPreferences sp = getSharedPreferences("login_config", MODE_PRIVATE);
        boolean autoLogin = sp.getBoolean("auto_login", false);
        if (autoLogin) {
            Intent intent = new Intent(GuideActivity.this, FriendListActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 初始化控件
        vpGuide = findViewById(R.id.vp_guide);

        // 初始化引导页数据
        initGuideData();

        // 设置ViewPager适配器
        vpGuide.setAdapter(new GuidePagerAdapter());
    }

    // 初始化引导页数据（3个引导页）
    private void initGuideData() {
        guideList = new ArrayList<>();
        // 引导页1：紫色背景
        guideList.add(new GuideBean(getString(R.string.guide_title1), 0xFF6A1B9A));
        // 引导页2：蓝色背景
        guideList.add(new GuideBean(getString(R.string.guide_title2), 0xFF0099FF));
        // 引导页3：浅蓝色背景 + 进入按钮
        guideList.add(new GuideBean(getString(R.string.guide_title3), 0xFF81D4FA));
    }

    // 引导页数据实体类
    private static class GuideBean {
        String title;
        int bgColor;

        public GuideBean(String title, int bgColor) {
            this.title = title;
            this.bgColor = bgColor;
        }
    }

    // ViewPager适配器
    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guideList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // 加载引导页子布局
            View view = LayoutInflater.from(GuideActivity.this)
                    .inflate(R.layout.item_guide, container, false);

            // 获取控件
            LinearLayout llRoot = view.findViewById(R.id.ll_guide_root);
            TextView tvTitle = view.findViewById(R.id.tv_guide_title);
            Button btnEnter = view.findViewById(R.id.btn_guide_enter);

            // 设置数据
            GuideBean guideBean = guideList.get(position);
            llRoot.setBackgroundColor(guideBean.bgColor);
            tvTitle.setText(guideBean.title);

            // 只有最后一个引导页显示“立即进入”按钮
            if (position == guideList.size() - 1) {
                btnEnter.setVisibility(View.VISIBLE);
                // 按钮点击：跳转到登录页
                btnEnter.setOnClickListener(v -> {
                    Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // 关闭引导页（避免返回键回到引导页）
                    finish();
                });
            } else {
                btnEnter.setVisibility(View.GONE);
            }

            // 添加到ViewPager
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}