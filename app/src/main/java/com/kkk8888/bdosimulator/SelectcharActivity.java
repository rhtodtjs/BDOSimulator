package com.kkk8888.bdosimulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.kakaotalk.response.KakaoTalkProfile;

import java.util.ArrayList;

public class SelectcharActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<CharInfomation> charType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectchar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gridView = (GridView) findViewById(R.id.grid_view);
        CharAdapter adapter = new CharAdapter(this, charType);
        charType.add(new CharInfomation("워리어", "war", R.drawable.slide_warrior));
        charType.add(new CharInfomation("소서러", "sorc", R.drawable.slide_sorc));
        charType.add(new CharInfomation("레인저", "ranger", R.drawable.slide_ranger));
        charType.add(new CharInfomation("자이언트", "giant", R.drawable.slide_giant));
        charType.add(new CharInfomation("금수랑", "tamer", R.drawable.slide_tamer));
        charType.add(new CharInfomation("발키리", "valki", R.drawable.slide_valkiry));
        charType.add(new CharInfomation("무사", "blader", R.drawable.slide_blader));
        charType.add(new CharInfomation("매화", "fblader", R.drawable.slide_plumy));
        charType.add(new CharInfomation("쿠노이치", "kuno", R.drawable.slide_kunoichi));
        charType.add(new CharInfomation("닌자", "ninza", R.drawable.slide_ninja));
        charType.add(new CharInfomation("위치", "witch", R.drawable.slide_witch));
        charType.add(new CharInfomation("위자드", "wizard", R.drawable.slide_wizard));


        gridView.setAdapter(adapter);
    }




}
