package com.kkk8888.bdosimulator;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.usermgmt.response.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by alfo06-18 on 2017-07-26.
 */

public class EnchantTip extends Fragment {

    ImageView tip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enchant_tip, container, false);
        tip = (ImageView) view.findViewById(R.id.tip);

        Picasso.with(getContext()).load(R.drawable.cg_tip).into(tip);


        getDBfile();

        return view;
    }

    void getDBfile() {
        AssetManager am = this.getResources().getAssets();


        File folder = new File("data/data/com.kkk8888.bdosimulator/databases");
        if (!folder.exists()) {
            folder.mkdir();
            Toast.makeText(getContext(), "폴더생성", Toast.LENGTH_SHORT).show();
        }

        File file = new File("data/data/com.kkk8888.bdosimulator/databases/itemlist.db");
        try {

            file.createNewFile();

            InputStream is = am.open("data.sqlite");
            long filesize = is.available();

            byte[] tempdata = new byte[(int) filesize];

            is.read(tempdata);

            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(tempdata);
            fos.close();
            Toast.makeText(getContext(), "Load OK..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getContext(), "데이터베이스 로드 에러", Toast.LENGTH_SHORT).show();
        }

    }
}
