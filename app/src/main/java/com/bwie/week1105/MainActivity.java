package com.bwie.week1105;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import fragment.BlankFragment;
import fragment.MineFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpContent;

    private List<String> title;
    private List<Fragment> fragmentList;
    private TabLayout tb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        title = new ArrayList<>();
        addData();

        tb1.setTabMode(TabLayout.MODE_FIXED);

        fragmentList = new ArrayList<>();
        for (int i = 0; i < title.size(); i++) {
            if (i != title.size() - 1) {
                BlankFragment blankFragment = BlankFragment.newInstance(title.get(i));
                fragmentList.add(blankFragment);
            } else {
                MineFragment mineFragment = new MineFragment();
                fragmentList.add(mineFragment);
            }
        }

        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });

        tb1.setupWithViewPager(vpContent);
    }

    private void addData() {
        title.add("首页");
        title.add("分类");
        title.add("我的");
    }

    private void initView() {
        vpContent = findViewById(R.id.vp_content);
        tb1 = findViewById(R.id.tb1);
    }
}
