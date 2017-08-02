package com.kkk8888.bdosimulator;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class EnchantActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    EnchantPager pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enchant);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String classType = intent.getStringExtra("class");


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setSelectedTabIndicatorColor((Color.GRAY));
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        tabLayout.addTab(tabLayout.newTab().setText("탭"));
        tabLayout.addTab(tabLayout.newTab().setText("탭"));
        tabLayout.addTab(tabLayout.newTab().setText("탭"));
        tabLayout.addTab(tabLayout.newTab().setText("탭"));
        pagerAdapter = new EnchantPager(getSupportFragmentManager(), classType);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);


    }
}
