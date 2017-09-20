package com.kkk8888.bdosimulator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo06-18 on 2017-07-24.
 */

public class CharAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<CharInfomation> charType;


    public CharAdapter(Context mContext, ArrayList<CharInfomation> charType) {
        this.mContext = mContext;
        this.charType = charType;
    }

    @Override
    public int getCount() {
        return charType.size();
    }

    @Override
    public Object getItem(int position) {
        return charType.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


       //if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.char_item, parent, false);

            TextView charName = (TextView) convertView.findViewById(R.id.charName);
            ImageView charFace = (ImageView) convertView.findViewById(R.id.charFace);
            Glide.with(mContext).load(charType.get(position).getImg()).into(charFace);
            charName.setText(charType.get(position).getName());


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EnchantActivity.class);
                    intent.putExtra("class", charType.get(position).getDESC());
                    mContext.startActivity(intent);
                }
            });
       // }
        return convertView;
    }
}
