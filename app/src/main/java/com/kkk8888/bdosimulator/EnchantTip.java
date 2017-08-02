package com.kkk8888.bdosimulator;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.usermgmt.response.model.UserProfile;

/**
 * Created by alfo06-18 on 2017-07-26.
 */

public class EnchantTip extends Fragment {

    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enchant_tip, container, false);
        textView = (TextView) view.findViewById(R.id.tv_tip);
        UserProfile userProfile = UserProfile.loadFromCache();



        textView.setText(userProfile.getNickname());


        return view;
    }
}
