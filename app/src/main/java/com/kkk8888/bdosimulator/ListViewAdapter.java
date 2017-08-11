package com.kkk8888.bdosimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo06-18 on 2017-08-01.
 */

public class ListViewAdapter extends BaseAdapter {

    ArrayList<SearchItem> listitem;
    Context mContext;

    public ListViewAdapter(ArrayList<SearchItem> listitem, Context mContext) {
        this.listitem = listitem;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.item_name);
        tv.setText(listitem.get(position).itemName);



        return convertView;
    }
}
