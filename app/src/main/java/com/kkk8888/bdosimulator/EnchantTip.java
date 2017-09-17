package com.kkk8888.bdosimulator;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


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



        return view;
    }


}
