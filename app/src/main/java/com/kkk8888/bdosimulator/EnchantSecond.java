package com.kkk8888.bdosimulator;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnchantSecond extends Fragment implements View.OnClickListener, View.OnLongClickListener {


    SQLiteDatabase db;
    String search;
    SearchView searchView;
    RelativeLayout mainLayout;
    ListView itemlist;
    ArrayList<SearchItem> searcheditem = new ArrayList<>();
    StringBuffer buffer;
    ListViewAdapter adapter;
    TextView ap, dp, wap;
    View mainView;

    String classType; //
    ImageView gear_helmet, gear_body, gear_ring1, gear_ring2, gear_glove;
    ImageView gear_neak, gear_wea_sec, gear_wea_awake, gear_wea_pri;
    ImageView gear_ear1, gear_ear2, gear_shoes, gear_belt;

    ImageView gear_helmet_selected, gear_body_selected, gear_ring1_selected, gear_ring2_selected, gear_glove_selected;
    ImageView gear_neak_selected, gear_wea_sec_selected, gear_wea_awake_selected, gear_wea_pri_selected;
    ImageView gear_ear1_selected, gear_ear2_selected, gear_shoes_selected, gear_belt_selected;

    ImageView gear_helmet_grade, gear_body_grade, gear_ring1_grade, gear_ring2_grade, gear_glove_grade;
    ImageView gear_neak_grade, gear_wea_sec_grade, gear_wea_awake_grade, gear_wea_pri_grade;
    ImageView gear_ear1_grade, gear_ear2_grade, gear_shoes_grade, gear_belt_grade;

    OutlineTextView gear_helmet_nowgrade, gear_body_nowgrade, gear_ring1_nowgrade, gear_ring2_nowgrade, gear_glove_nowgrade;
    OutlineTextView gear_neak_nowgrade, gear_wea_sec_nowgrade, gear_wea_awake_nowgrade, gear_wea_pri_nowgrade;
    OutlineTextView gear_ear1_nowgrade, gear_ear2_nowgrade, gear_shoes_nowgrade, gear_belt_nowgrade;

    Button gotov, resetG; //돌파 버튼

    ImageView needItem, enchantItem;

    OutlineTextView enchantGrade; //재료 아이템 , 강화 시킬 아이템

    EnchantActivity mainActivity;
    EnchantItemRatio itemRate = new EnchantItemRatio();
    Random rnd = new Random();

    TextView tv_ratio, nowrate;

    EnchantItem focusView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle == null) {
            classType = "null char";
        } else classType = bundle.getString("classType");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_enchant_second, container, false);


        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.kkk8888.bdosimulator/databases/itemlist.db", null);

        settingView(mainView);

        loadSave();


        return mainView;

    }

    void settingView(View view) {
        searchView = (SearchView) view.findViewById(R.id.searchBtn);
        mainLayout = (RelativeLayout) view.findViewById(R.id.enchant_frag2);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.INVISIBLE);
                searchView.setQuery("", true);
                searchView.setIconified(true);
            }
        });


        tv_ratio = (TextView) view.findViewById(R.id.tv_ratio);

        searchView.setIconifiedByDefault(false);
        itemlist = (ListView) view.findViewById(R.id.itemlist);

        adapter = new ListViewAdapter(searcheditem, getContext());
        itemlist.setAdapter(adapter);

        ap = (TextView) view.findViewById(R.id.ap);
        dp = (TextView) view.findViewById(R.id.dp);
        wap = (TextView) view.findViewById(R.id.wap);

        gotov = (Button) view.findViewById(R.id.gotov);
        gotov.setOnClickListener(this);

        nowrate = (TextView) view.findViewById(R.id.nowrate);
//        resetG = (Button) view.findViewById(R.id.resetG);
//        resetG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                callGearItem(mainView);
//            }
//        });

        needItem = (ImageView) view.findViewById(R.id.needItem);
        enchantItem = (ImageView) view.findViewById(R.id.enchantItem);
        enchantGrade = (OutlineTextView) view.findViewById(R.id.enchantGrade);


        gear_helmet_selected = (ImageView) view.findViewById(R.id.gear_helmet_selected);
        gear_body_selected = (ImageView) view.findViewById(R.id.gear_body_selected);
        gear_ring1_selected = (ImageView) view.findViewById(R.id.gear_ring1_selected);
        gear_ring2_selected = (ImageView) view.findViewById(R.id.gear_ring2_selected);
        gear_glove_selected = (ImageView) view.findViewById(R.id.gear_glove_selected);
        gear_neak_selected = (ImageView) view.findViewById(R.id.gear_neck_selected);
        gear_wea_sec_selected = (ImageView) view.findViewById(R.id.gear_wea_second_selected);
        gear_wea_awake_selected = (ImageView) view.findViewById(R.id.gear_wea_awake_selected);
        gear_wea_pri_selected = (ImageView) view.findViewById(R.id.gear_wea_pri_selected);
        gear_ear1_selected = (ImageView) view.findViewById(R.id.gear_ear1_selected);
        gear_ear2_selected = (ImageView) view.findViewById(R.id.gear_ear2_selected);
        gear_shoes_selected = (ImageView) view.findViewById(R.id.gear_shoe_selected);
        gear_belt_selected = (ImageView) view.findViewById(R.id.gear_belt_selected);

        gear_helmet_grade = (ImageView) view.findViewById(R.id.gear_helmet_grade);
        gear_body_grade = (ImageView) view.findViewById(R.id.gear_body_grade);
        gear_ring1_grade = (ImageView) view.findViewById(R.id.gear_ring1_grade);
        gear_ring2_grade = (ImageView) view.findViewById(R.id.gear_ring2_grade);
        gear_glove_grade = (ImageView) view.findViewById(R.id.gear_glove_grade);
        gear_neak_grade = (ImageView) view.findViewById(R.id.gear_neck_grade);
        gear_wea_sec_grade = (ImageView) view.findViewById(R.id.gear_wea_second_grade);
        gear_wea_awake_grade = (ImageView) view.findViewById(R.id.gear_wea_awake_grade);
        gear_wea_pri_grade = (ImageView) view.findViewById(R.id.gear_wea_pri_grade);
        gear_ear1_grade = (ImageView) view.findViewById(R.id.gear_ear1_grade);
        gear_ear2_grade = (ImageView) view.findViewById(R.id.gear_ear2_grade);
        gear_shoes_grade = (ImageView) view.findViewById(R.id.gear_shoe_grade);
        gear_belt_grade = (ImageView) view.findViewById(R.id.gear_belt_grade);


        gear_helmet_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_helmet_nowgrade);
        gear_body_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_body_nowgrade);
        gear_ring1_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_ring1_nowgrade);
        gear_ring2_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_ring2_nowgrade);
        gear_glove_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_glove_nowgrade);
        gear_neak_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_neck_nowgrade);
        gear_wea_sec_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_wea_second_nowgrade);
        gear_wea_awake_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_wea_awake_nowgrade);
        gear_wea_pri_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_wea_pri_nowgrade);
        gear_ear1_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_ear1_nowgrade);
        gear_ear2_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_ear2_nowgrade);
        gear_shoes_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_shoe_nowgrade);
        gear_belt_nowgrade = (OutlineTextView) view.findViewById(R.id.gear_belt_nowgrade);

        ImageView tempView = null;
        for (int i = 0; i < 13; i++) {
            tempView = (ImageView) view.findViewById(R.id.gear_ear1 + i);
            tempView.setOnClickListener(this);
            tempView.setOnLongClickListener(this);
        }

        callGearItem(view);
//        gear_helmet = (ImageView) view.findViewById(R.id.gear_helmet);
//        gear_helmet.setTag(new EnchantItem("HELMET", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/13_hel/00011013.png", 3, 20, 0, 0, 19, "방어구", "HELMET", R.id.gear_helmet_selected, R.id.gear_helmet_grade, 11013));
//        gear_body = (ImageView) view.findViewById(R.id.gear_body);
//        gear_body.setTag(new EnchantItem("BODY", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/09_upperbody/00011017.png", 3, 20, 0, 0, 19, "방어구", "BODY", R.id.gear_body_selected, R.id.gear_body_grade, 11017));
//        gear_ring1 = (ImageView) view.findViewById(R.id.gear_ring1);
//        gear_ring1.setTag(new EnchantItem("RING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/16_ring/00012031.png", 3, 5, 0, 0, 3, "악세", "RING", R.id.gear_ring1_selected, R.id.gear_ring1_grade, 12031));
//        gear_ring2 = (ImageView) view.findViewById(R.id.gear_ring2);
//        gear_ring2.setTag(new EnchantItem("RING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/16_ring/00012031.png", 3, 5, 0, 0, 3, "악세", "RING", R.id.gear_ring2_selected, R.id.gear_ring2_grade, 12031));
//        gear_glove = (ImageView) view.findViewById(R.id.gear_glove);
//        gear_glove.setTag(new EnchantItem("GLOVES", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/11_hand/00011015.png", 3, 20, 0, 0, 19, "방어구", "GLOVES", R.id.gear_glove_selected, R.id.gear_glove_grade, 11015));
//        gear_neak = (ImageView) view.findViewById(R.id.gear_neck);
//        gear_neak.setTag(new EnchantItem("NECKLACE", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/15_necklace/00011607.png", 3, 5, 0, 0, 3, "악세", "NECKLACE", R.id.gear_neck_selected, R.id.gear_neck_grade, 11607));
//        gear_wea_sec = (ImageView) view.findViewById(R.id.gear_wea_second);
//        gear_wea_sec.setTag(new EnchantItem("W_SECOND", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/08_subweapon/00010138.png", 3, 20, 0, 0, 19, "무기", "SHIELD", R.id.gear_wea_second_selected, R.id.gear_wea_second_grade, 10138));
//        gear_wea_awake = (ImageView) view.findViewById(R.id.gear_wea_awake);
//        gear_wea_awake.setTag(new EnchantItem("W_AWAKE", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/01_weapon/00014702.png", 3, 20, 0, 0, 19, "무기", "GREATSWORD", R.id.gear_wea_awake_selected, R.id.gear_wea_awake_grade, 14702));
//        gear_wea_pri = (ImageView) view.findViewById(R.id.gear_wea_pri);
//        gear_wea_pri.setTag(new EnchantItem("W_PRI", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/01_weapon/00010010.png", 3, 20, 0, 0, 19, "무기", "SWORD", R.id.gear_wea_pri_selected, R.id.gear_wea_pri_grade, 10010));
//        gear_ear1 = (ImageView) view.findViewById(R.id.gear_ear1);
//        gear_ear1.setTag(new EnchantItem("EARRING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/17_earring/00011828.png", 3, 5, 0, 0, 3, "악세", "null", R.id.gear_ear1_selected, R.id.gear_ear1_grade, 11828));
//        gear_ear2 = (ImageView) view.findViewById(R.id.gear_ear2);
//        gear_ear2.setTag(new EnchantItem("EARRING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/17_earring/00011828.png", 3, 5, 0, 0, 3, "악세", "null", R.id.gear_ear2_selected, R.id.gear_ear2_grade, 11828));
//        gear_shoes = (ImageView) view.findViewById(R.id.gear_shoe);
//        gear_shoes.setTag(new EnchantItem("SHOES", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/12_foot/00011016.png", 3, 20, 0, 0, 19, "방어구", "SHOES", R.id.gear_shoe_selected, R.id.gear_shoe_grade, 11016));
//        gear_belt = (ImageView) view.findViewById(R.id.gear_belt);
//        gear_belt.setTag(new EnchantItem("BELT", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/18_belt/00012230.png", 3, 5, 0, 0, 3, "악세", "BELT", R.id.gear_belt_selected, R.id.gear_belt_grade, 12230));
//
//
//        ImageView tempView = null;
//        EnchantItem tempItem = null;
//
//        for (int i = 0; i < 13; i++) {
//            tempView = (ImageView) view.findViewById(R.id.gear_ear1 + i);
//            tempView.setOnClickListener(this);
//            tempView.setOnLongClickListener(this);
//            tempItem = (EnchantItem) (tempView.getTag());
//            reloadData(tempItem);
//            Picasso.with(getContext()).load(tempItem.getImgUrl()).resize(125, 125).into(tempView);
//
//            ImageView iv = (ImageView) mainView.findViewById(tempItem.getGradeID());
//            switch (tempItem.getGrade()) {
//                case 0:
//                    iv.setImageResource(R.drawable.item_grade_0);
//                    break;
//                case 1:
//                    iv.setImageResource(R.drawable.item_grade_1);
//                    break;
//                case 2:
//                    iv.setImageResource(R.drawable.item_grade_2);
//                    break;
//                case 3:
//                    iv.setImageResource(R.drawable.item_grade_3);
//                    break;
//
//            }
//            iv = null;
//        }
//
//        setGrade();

        mainActivity = (EnchantActivity) getActivity();


    }


    void callGearItem(View view) {
        gear_helmet = (ImageView) view.findViewById(R.id.gear_helmet);
        gear_helmet.setTag(new EnchantItem("HELMET", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/13_hel/00011013.png", 3, 20, 0, 0, 19, "방어구", "HELMET", R.id.gear_helmet_selected, R.id.gear_helmet_grade, 11013));
        gear_body = (ImageView) view.findViewById(R.id.gear_body);
        gear_body.setTag(new EnchantItem("BODY", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/09_upperbody/00011017.png", 3, 20, 0, 0, 19, "방어구", "BODY", R.id.gear_body_selected, R.id.gear_body_grade, 11017));
        gear_ring1 = (ImageView) view.findViewById(R.id.gear_ring1);
        gear_ring1.setTag(new EnchantItem("RING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/16_ring/00012031.png", 3, 5, 0, 0, 3, "악세", "RING", R.id.gear_ring1_selected, R.id.gear_ring1_grade, 12031));
        gear_ring2 = (ImageView) view.findViewById(R.id.gear_ring2);
        gear_ring2.setTag(new EnchantItem("RING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/16_ring/00012031.png", 3, 5, 0, 0, 3, "악세", "RING", R.id.gear_ring2_selected, R.id.gear_ring2_grade, 12031));
        gear_glove = (ImageView) view.findViewById(R.id.gear_glove);
        gear_glove.setTag(new EnchantItem("GLOVES", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/11_hand/00011015.png", 3, 20, 0, 0, 19, "방어구", "GLOVES", R.id.gear_glove_selected, R.id.gear_glove_grade, 11015));
        gear_neak = (ImageView) view.findViewById(R.id.gear_neck);
        gear_neak.setTag(new EnchantItem("NECKLACE", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/15_necklace/00011607.png", 3, 5, 0, 0, 3, "악세", "NECKLACE", R.id.gear_neck_selected, R.id.gear_neck_grade, 11607));
        gear_wea_sec = (ImageView) view.findViewById(R.id.gear_wea_second);
        gear_wea_sec.setTag(new EnchantItem("W_SECOND", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/08_subweapon/00010138.png", 3, 20, 0, 0, 19, "무기", "SHIELD", R.id.gear_wea_second_selected, R.id.gear_wea_second_grade, 10138));
        gear_wea_awake = (ImageView) view.findViewById(R.id.gear_wea_awake);
        gear_wea_awake.setTag(new EnchantItem("W_AWAKE", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/01_weapon/00014702.png", 3, 20, 0, 0, 19, "무기", "GREATSWORD", R.id.gear_wea_awake_selected, R.id.gear_wea_awake_grade, 14702));
        gear_wea_pri = (ImageView) view.findViewById(R.id.gear_wea_pri);
        gear_wea_pri.setTag(new EnchantItem("W_PRI", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/01_weapon/00010010.png", 3, 20, 0, 0, 19, "무기", "SWORD", R.id.gear_wea_pri_selected, R.id.gear_wea_pri_grade, 10010));
        gear_ear1 = (ImageView) view.findViewById(R.id.gear_ear1);
        gear_ear1.setTag(new EnchantItem("EARRING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/17_earring/00011828.png", 3, 5, 0, 0, 3, "악세", "null", R.id.gear_ear1_selected, R.id.gear_ear1_grade, 11828));
        gear_ear2 = (ImageView) view.findViewById(R.id.gear_ear2);
        gear_ear2.setTag(new EnchantItem("EARRING", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/17_earring/00011828.png", 3, 5, 0, 0, 3, "악세", "null", R.id.gear_ear2_selected, R.id.gear_ear2_grade, 11828));
        gear_shoes = (ImageView) view.findViewById(R.id.gear_shoe);
        gear_shoes.setTag(new EnchantItem("SHOES", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/12_foot/00011016.png", 3, 20, 0, 0, 19, "방어구", "SHOES", R.id.gear_shoe_selected, R.id.gear_shoe_grade, 11016));
        gear_belt = (ImageView) view.findViewById(R.id.gear_belt);
        gear_belt.setTag(new EnchantItem("BELT", "http://bddatabase.net/items/new_icon/06_pc_equipitem/00_common/18_belt/00012230.png", 3, 5, 0, 0, 3, "악세", "BELT", R.id.gear_belt_selected, R.id.gear_belt_grade, 12230));


        ImageView tempView = null;
        EnchantItem tempItem = null;

        for (int i = 0; i < 13; i++) {
            tempView = (ImageView) view.findViewById(R.id.gear_ear1 + i);
            tempItem = (EnchantItem) (tempView.getTag());
            reloadData(tempItem);
            Picasso.with(getContext()).load(tempItem.getImgUrl()).resize(125, 125).into(tempView);

            ImageView iv = (ImageView) mainView.findViewById(tempItem.getGradeID());
            switch (tempItem.getGrade()) {
                case 0:
                    iv.setImageResource(R.drawable.item_grade_0);
                    break;
                case 1:
                    iv.setImageResource(R.drawable.item_grade_1);
                    break;
                case 2:
                    iv.setImageResource(R.drawable.item_grade_2);
                    break;
                case 3:
                    iv.setImageResource(R.drawable.item_grade_3);
                    break;

            }
            iv = null;
        }

        setGrade();
    }


    int[] initAllStat() {

        int[] ints = new int[3];
        EnchantItem item;
        int ap = 0, dp = 0, wap = 0;
        int pri = 0;
        int awake = 0;


        ImageView img = null;
        for (int i = 0; i < 13; i++) {
            img = (ImageView) mainView.findViewById(R.id.gear_ear1 + i);
            item = (EnchantItem) img.getTag();

            ap += item.getMaxDMG();
            dp += item.getMinDMG();
            wap += item.getMaxDMG();

            if (item.getItemType().equals("W_PRI")) {
                pri = item.getMaxDMG();
            } else if (item.getItemType().equals("W_AWAKE")) {
                awake = item.getMaxDMG();
            }

        }

        wap -= pri;
        ap -= awake;

        ints[0] = ap;
        ints[1] = dp;
        ints[2] = wap;


        return ints;

    }

    void setGrade() {


        ImageView iv;
        EnchantItem item;
        for (int i = 0; i < 13; i++) {
            iv = (ImageView) mainView.findViewById(R.id.gear_ear1 + i);
            item = (EnchantItem) iv.getTag();
            OutlineTextView textView = (OutlineTextView) mainView.findViewById(R.id.gear_ear1_nowgrade + i);
            if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() == 0) continue;
            if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 15 && item.getNowGrade() > 0)
                textView.setText("+" + item.getNowGrade());
            else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 16)
                textView.setText("I");
            else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 17)
                textView.setText("II");
            else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 18)
                textView.setText("III");
            else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 19)
                textView.setText("IV");
            else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() > 19)
                textView.setText("V");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 0)
                textView.setText("");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 1)
                textView.setText("I");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 2)
                textView.setText("II");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 3)
                textView.setText("III");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 4)
                textView.setText("IV");
            else if (item.getTableName().equals("악세") && item.getNowGrade() == 5)
                textView.setText("V");


        }


    }

    void loadSave() {
        String dir = getActivity().getApplicationContext().getFilesDir().getPath();
        File save = new File(dir + "/" + classType + "2.dat");
        StringBuffer sb = null;

        if (!save.exists()) {
            Toast.makeText(mainActivity, "기어2 의 저장 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            InputStream is = getActivity().openFileInput(classType + "2.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            sb = new StringBuffer();

            while (line != null) {
                sb.append(line);
                line = br.readLine();

            }

            //Log.i("제이슨", sb.toString());

            Gson gson = new Gson();

            JSONArray obj = new JSONArray(sb.toString());

            for (int i = 0; i < obj.length(); i++) {
                JSONObject sibal = obj.getJSONObject(i);
                ImageView temp = (ImageView) mainView.findViewById(R.id.gear_ear1 + i);
                EnchantItem temp2 = gson.fromJson(sibal.toString(), EnchantItem.class);
                temp.setTag(temp2);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageView tempView = null;
        EnchantItem tempItem = null;

        for (int i = 0; i < 13; i++) {
            tempView = (ImageView) mainView.findViewById(R.id.gear_ear1 + i);
            tempItem = (EnchantItem) (tempView.getTag());
            reloadData(tempItem);
            if (tempItem.getImgUrl().equals("null")) continue;
            Picasso.with(getContext()).load(tempItem.getImgUrl()).resize(125, 125).into(tempView);

            ImageView iv = (ImageView) mainView.findViewById(tempItem.getGradeID());
            switch (tempItem.getGrade()) {
                case 0:
                    iv.setImageResource(R.drawable.item_grade_0);
                    break;
                case 1:
                    iv.setImageResource(R.drawable.item_grade_1);
                    break;
                case 2:
                    iv.setImageResource(R.drawable.item_grade_2);
                    break;
                case 3:
                    iv.setImageResource(R.drawable.item_grade_3);
                    break;

            }
            iv = null;
        }

        setGrade();


    }

    @Override
    public boolean onLongClick(final View v) {

        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnchantItem item = (EnchantItem) v.getTag();
                ImageView image = (ImageView) v;
                String loadImg;
                String sql = "select * from db_items_base where `NAME_KR` = '" + searcheditem.get(position).getItemName() + "'";

                if (db.isOpen()) {

                    Cursor cursor = db.rawQuery(sql, null);
                    loadImg = "http://bddatabase.net/items/";


                    while (cursor.moveToNext()) {
                        loadImg += cursor.getString(cursor.getColumnIndex("ICONIMAGEFILE"));
                        loadImg = loadImg.replace(".dds", ".png");
                        item.setImgUrl(loadImg);
                        item.setGrade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("GRADE"))));
                        item.setMaxGrade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MAXENCHANTLVL"))));
                        item.setSubType(cursor.getString(cursor.getColumnIndex("SUBTYPE")));
                        searcheditem.get(position).set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                        item.setItemId(searcheditem.get(position).get_id());

                        adapter.notifyDataSetChanged();
                    }


                }


                Picasso.with(getContext()).load(item.getImgUrl()).resize(125, 125).into(image);
                Toast.makeText(getContext(), item.getTableName(), Toast.LENGTH_SHORT).show();
                ImageView iv = (ImageView) mainView.findViewById(item.getGradeID());
                switch (item.getGrade()) {
                    case 0:
                        iv.setImageResource(R.drawable.item_grade_0);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.item_grade_1);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.item_grade_2);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.item_grade_3);
                        break;

                }
                iv = null;


                if (item.getTableName().equals("방어구")) {

                    String sql2 = "select * from Blunt where `_id` = '" + searcheditem.get(position).get_id() + "' and `Enchant` = '" + item.getNowGrade() + "'";
                    if (db.isOpen()) {

                        Cursor cursor = db.rawQuery(sql2, null);

                        while (cursor.moveToNext()) {


                            item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));
                            item.setNeedItemID(cursor.getInt(cursor.getColumnIndex("NeedEnchantItemID")));


                        }

                    }

                } else if (item.getTableName().equals("무기")) {

                    String sql2 = "select * from LongSword where `_id` = '" + searcheditem.get(position)._id + "'  and `Enchant` = '" + item.getNowGrade() + "'";
                    //String sql2 = "select * from LongSword where `_id` = " +
                    // searcheditem.get(position).get_id() + "";

                    if (db.isOpen()) {

                        Cursor cursor = db.rawQuery(sql2, null);

                        // Log.i("빵구", searcheditem.get(position)._id + "," + cursor.getCount() + ", " + item.getNowGrade());

                        while (cursor.moveToNext()) {

                            //워리어시작.. 힘들다

                            if (item.getSubType().equals("SWORD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                            } else if (item.getSubType().equals("GREATSWORD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                            } else if (item.getSubType().equals("SHIELD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                                //워리어 끝


                                //소서러 시작

                            } else if (item.getSubType().equals("TALISMAN")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("SCYTHE")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("AMULET")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                                //소서 끝
                                //레인저 시작
                            } else if (item.getSubType().equals("BOW")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("RDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("ELEMENTALSWORD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("RDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("DAGGER")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                                //레인저 끝
                                //자이언트 시작
                            } else if (item.getSubType().equals("AXE")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("IRONGLOVES")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                item.setBaseAp(5);
                                String[] sibal2 = sibal.split("\\+");
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("ORNAMENT")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                                //자이언트 끝
                                //금수랑 시작

                            } else if (item.getSubType().equals("SHORTSWORD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("SKYBLUNT")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("TOY")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                                //금수랑 끝
                                //발키리 시작

                            } else if (item.getSubType().equals("LANCIA")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                                //발키리끝
                                //무사 시작
                            } else if (item.getSubType().equals("BLADE")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                            } else if (item.getSubType().equals("MUSINDO")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                            } else if (item.getSubType().equals("SHORTBOW")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                            } else if (item.getSubType().equals("WOLCHANG")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("CHAKRAM")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                            } else if (item.getSubType().equals("SURADO")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("STAFF")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                            } else if (item.getSubType().equals("SPHERE")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("SPHERE2")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                            } else if (item.getSubType().equals("KUNAISHURIKEN")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                                String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                                if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                                else if (sibal2.equals("1D1")) item.setBaseAp(1);
                                else if (sibal2.equals("1D2")) item.setBaseAp(1);
                                else if (sibal2.equals("1D3")) item.setBaseAp(2);
                                else if (sibal2.equals("1D4")) item.setBaseAp(2);
                                else if (sibal2.equals("1D5")) item.setBaseAp(3);
                                else if (sibal2.equals("1D6")) item.setBaseAp(3);
                                else if (sibal2.equals("1D7")) item.setBaseAp(4);
                                else if (sibal2.equals("1D8")) item.setBaseAp(4);
                                else if (sibal2.equals("1D9")) item.setBaseAp(5);
                                else if (sibal2.equals("1D10")) item.setBaseAp(5);
                                else if (sibal2.equals("1D11")) item.setBaseAp(6);
                                else if (sibal2.equals("1D12")) item.setBaseAp(6);
                                else if (sibal2.equals("1D13")) item.setBaseAp(7);
                                else if (sibal2.equals("1D14")) item.setBaseAp(7);
                                else item.setBaseAp(0);
                                item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                                item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                            } else if (item.getSubType().equals("ELFSWORD")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                                String[] sibal2 = sibal.split("\\+");
                                if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                                else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                                else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                                else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                                else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                                else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                                else item.setBaseAp(3);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            } else if (item.getSubType().equals("VEDIANT")) {
                                String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                                String[] sibal2 = sibal.split("\\+");
                                item.setBaseAp(5);
                                item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                            }

                            item.setNeedItemID(cursor.getInt(cursor.getColumnIndex("NeedEnchantItemID")));

                        }//

                    }


                } else if (item.getTableName().equals("악세")) {

                    String sql2 = "select * from TwoHandedSword where `_id` = '" + searcheditem.get(position)._id + "' and `Enchant` = '" + item.getNowGrade() + "'";
                    ;
                    if (db.isOpen()) {

                        Cursor cursor = db.rawQuery(sql2, null);


                        while (cursor.moveToNext()) {

                            item.setMaxDMG(cursor.getInt(cursor.getColumnIndex("AddedMDD")));

                            item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                            item.setNeedItemID(cursor.getInt(cursor.getColumnIndex("NeedEnchantItemID")));

                        }


                    }

                }
                int[] stat = new int[3];


                stat = initAllStat();
                setGrade();

                ap.setText("공격력\n" + stat[0]);
                dp.setText("방어력\n" + stat[1]);
                wap.setText("각성 공격력\n" + stat[2]);


            }


        });


        searchView.requestFocus();

        TextView text = ((TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));

        text.setBackgroundColor(Color.WHITE);

        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EnchantItem item = (EnchantItem) v.getTag();
                String tagName = item.getItemType();

                GetDataFromDB dataFromDB = new GetDataFromDB();
                dataFromDB.execute(classType, tagName);
                search = query;
                searchView.setVisibility(View.INVISIBLE);
                searchView.setQuery("", true);
                searchView.setIconified(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EnchantItem item = (EnchantItem) v.getTag();
                String tagName = item.getItemType();


                searcheditem.clear();

                GetDataFromDB dataFromDB = new GetDataFromDB();
                dataFromDB.execute(classType, tagName);
                search = newText;


                return false;
            }
        });


        return true;
    }

    public void reloadData(EnchantItem item) {


        if (item.getTableName().equals("방어구")) {

            String sql2 = "select * from Blunt where `_id` = '" + item.getItemId() + "' and `Enchant` = '" + item.getNowGrade() + "'";
            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql2, null);


                while (cursor.moveToNext()) {


                    item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));


                }

            }

        } else if (item.getTableName().equals("무기")) {

            String sql2 = "select * from LongSword where `_id` = '" + item.getItemId() + "' and `Enchant` = '" + item.getNowGrade() + "'";
            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql2, null);

                Log.i("이거다", item.getSubType());

                while (cursor.moveToNext()) {

                    //워리어시작.. 힘들다

                    if (item.getSubType().equals("SWORD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                    } else if (item.getSubType().equals("GREATSWORD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                    } else if (item.getSubType().equals("SHIELD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                        //워리어 끝


                        //소서러 시작

                    } else if (item.getSubType().equals("TALISMAN")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("SCYTHE")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("AMULET")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                        //소서 끝
                        //레인저 시작
                    } else if (item.getSubType().equals("BOW")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("RDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("ELEMENTALSWORD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("RDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("DAGGER")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                        //레인저 끝
                        //자이언트 시작
                    } else if (item.getSubType().equals("AXE")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("IRONGLOVES")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        item.setBaseAp(5);
                        String[] sibal2 = sibal.split("\\+");
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("ORNAMENT")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                        //자이언트 끝
                        //금수랑 시작

                    } else if (item.getSubType().equals("SHORTSWORD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("SKYBLUNT")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("TOY")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedMDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("MDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));

                        //금수랑 끝
                        //발키리 시작

                    } else if (item.getSubType().equals("LANCIA")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                        //발키리끝
                        //무사 시작
                    } else if (item.getSubType().equals("BLADE")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                    } else if (item.getSubType().equals("MUSINDO")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                    } else if (item.getSubType().equals("SHORTBOW")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                    } else if (item.getSubType().equals("WOLCHANG")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("CHAKRAM")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                    } else if (item.getSubType().equals("SURADO")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("STAFF")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());


                    } else if (item.getSubType().equals("SPHERE")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("SPHERE2")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());

                    } else if (item.getSubType().equals("KUNAISHURIKEN")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("AddedDDD"));
                        String sibal2 = cursor.getString(cursor.getColumnIndex("DDD"));
                        if (sibal2.equals("1D1-1")) item.setBaseAp(0);
                        else if (sibal2.equals("1D1")) item.setBaseAp(1);
                        else if (sibal2.equals("1D2")) item.setBaseAp(1);
                        else if (sibal2.equals("1D3")) item.setBaseAp(2);
                        else if (sibal2.equals("1D4")) item.setBaseAp(2);
                        else if (sibal2.equals("1D5")) item.setBaseAp(3);
                        else if (sibal2.equals("1D6")) item.setBaseAp(3);
                        else if (sibal2.equals("1D7")) item.setBaseAp(4);
                        else if (sibal2.equals("1D8")) item.setBaseAp(4);
                        else if (sibal2.equals("1D9")) item.setBaseAp(5);
                        else if (sibal2.equals("1D10")) item.setBaseAp(5);
                        else if (sibal2.equals("1D11")) item.setBaseAp(6);
                        else if (sibal2.equals("1D12")) item.setBaseAp(6);
                        else if (sibal2.equals("1D13")) item.setBaseAp(7);
                        else if (sibal2.equals("1D14")) item.setBaseAp(7);
                        else item.setBaseAp(0);
                        item.setMaxDMG(Integer.parseInt(sibal) + item.getBaseAp());
                        item.setMinDMG(cursor.getInt(cursor.getColumnIndex("DDV")) + cursor.getInt(cursor.getColumnIndex("DPV")));
                    } else if (item.getSubType().equals("ELFSWORD")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("MDD"));
                        String[] sibal2 = sibal.split("\\+");
                        if (sibal2[0].equals("1D3")) item.setBaseAp(2);
                        else if (sibal2[0].equals("1D5")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D6")) item.setBaseAp(3);
                        else if (sibal2[0].equals("1D7")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D8")) item.setBaseAp(4);
                        else if (sibal2[0].equals("1D9")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D10")) item.setBaseAp(5);
                        else if (sibal2[0].equals("1D15")) item.setBaseAp(8);
                        else if (sibal2[0].equals("1D19")) item.setBaseAp(10);
                        else item.setBaseAp(3);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    } else if (item.getSubType().equals("VEDIANT")) {
                        String sibal = cursor.getString(cursor.getColumnIndex("DDD"));
                        String[] sibal2 = sibal.split("\\+");
                        item.setBaseAp(5);
                        item.setMaxDMG(Integer.parseInt(sibal2[1]) + item.getBaseAp());
                    }
                }//

            }


        } else if (item.getTableName().equals("악세")) {

            String sql2 = "select * from TwoHandedSword where `_id` = '" + item.getItemId() + "' and `Enchant` = '" + item.getNowGrade() + "'";
            ;
            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql2, null);


                while (cursor.moveToNext()) {

                    item.setMaxDMG(cursor.getInt(cursor.getColumnIndex("AddedMDD")));

                    item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));


                }


            }

        }
        int[] stat = new int[3];


        stat = initAllStat();
        setGrade();

        ap.setText("공격력\n" + stat[0]);
        dp.setText("방어력\n" + stat[1]);
        wap.setText("각성 공격력\n" + stat[2]);
    }


    public boolean weaponPowerUp(EnchantItem item) {

        boolean isUp = false;
        int nowStack = mainActivity.seleceted;
        int nowGrade = item.getNowGrade();
        int maxStack;
        double nowRatio;
        double bonusRatio;
        double baseRatio;
        double maxRatio;
        double currentRatio;
        String temprate;
        double balks;

        switch (item.getGrade()) {
            case 0:
                //일반템
                baseRatio = itemRate.whiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.whiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.whiteItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.stackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;


                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;

            case 1:
                //녹템
                baseRatio = itemRate.greenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.greenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.greenItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.stackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;
            case 2:
                //파템

                baseRatio = itemRate.blueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.blueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.blueItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.stackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;
                break;

            case 3:
                //보스템

                baseRatio = itemRate.yellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.yellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.yellowItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.stackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;


                break;
        }

        return isUp;

    }


    public boolean bodyPowerUp(EnchantItem item) {

        boolean isUp = false;
        int nowStack = mainActivity.seleceted;
        int nowGrade = item.getNowGrade();
        int maxStack;
        double nowRatio;
        double bonusRatio;
        double baseRatio;
        double maxRatio;
        double currentRatio;
        double balks;

        switch (item.getGrade()) {
            case 0:
                //일반템
                baseRatio = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.bodystackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;

            case 1:
                //녹템
                baseRatio = itemRate.bodyGreenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyGreenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyGreenItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.bodystackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;
            case 2:
                //파템

                baseRatio = itemRate.bodyBlueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyBlueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyBlueItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.bodystackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;

            case 3:
                //보스템

                baseRatio = itemRate.bodyYellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyYellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyYellowItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.bodystackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;


                break;
        }

        return isUp;

    }

    public boolean accePowerUp(EnchantItem item) {

        boolean isUp = false;
        int nowStack = mainActivity.seleceted;
        int nowGrade = item.getNowGrade();
        int maxStack;
        double nowRatio;
        double bonusRatio;
        double baseRatio;
        double maxRatio;
        double currentRatio;
        double balks;

        switch (item.getGrade()) {

            case 0:
                //일반템
                baseRatio = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.accestackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;

            case 1:
                //녹템
                baseRatio = itemRate.AcceGreenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceGreenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceGreenItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.accestackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;

                break;
            case 2:
                //파템

                baseRatio = itemRate.AcceBlueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceBlueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceBlueItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.accestackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;
                break;

            case 3:
                //보스템

                baseRatio = itemRate.AcceYellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceYellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceYellowItemMaxRate.get(nowGrade).getMaxStack();

                nowRatio = rnd.nextDouble() * 100;
                bonusRatio = itemRate.accestackRatio[nowGrade];

                if (nowStack >= maxStack) nowStack = maxStack;

                balks = getBalks();

                currentRatio = baseRatio + bonusRatio * nowStack;

                tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));

                nowrate.setText("현재수치 : " + Math.floor(nowRatio) + " 목표수치 : " + Math.floor(currentRatio+balks));

                if (nowRatio <= currentRatio + balks) isUp = true;
                else isUp = false;


                break;
        }

        return isUp;

    }

    public void previewRate(EnchantItem item) {

        int nowGrade = item.getNowGrade();
        double currentRatio = 0;
        double baseRatio = 0;
        double bonusRatio = 0;
        double maxRatio = 0;
        int maxStack = 0;
        int nowStack = mainActivity.seleceted;

        if (item.getTableName().equals("무기")) {

            bonusRatio = itemRate.stackRatio[nowGrade];


            if (item.getGrade() == 0) {
                baseRatio = itemRate.whiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.whiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.whiteItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 1) {
                baseRatio = itemRate.greenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.greenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.greenItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 2) {
                baseRatio = itemRate.blueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.blueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.blueItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 3) {
                baseRatio = itemRate.yellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.yellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.yellowItemMaxRate.get(nowGrade).getMaxStack();
            }


            if (nowStack >= maxStack) nowStack = maxStack;

        } else if (item.getTableName().equals("방어구")) {

            bonusRatio = itemRate.bodystackRatio[nowGrade];
            if (item.getGrade() == 0) {
                baseRatio = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyWhiteItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 1) {
                baseRatio = itemRate.bodyGreenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyGreenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyGreenItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 2) {
                baseRatio = itemRate.bodyBlueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyBlueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyBlueItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 3) {
                baseRatio = itemRate.bodyYellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.bodyYellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.bodyYellowItemMaxRate.get(nowGrade).getMaxStack();
            }

            if (nowStack >= maxStack) nowStack = maxStack;


        } else if (item.getTableName().equals("악세")) {

            bonusRatio = itemRate.accestackRatio[nowGrade];
            if (item.getGrade() == 0) {
                baseRatio = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceWhiteItemMaxRate.get(nowGrade).getMaxStack();
            } else if (item.getGrade() == 1) {
                baseRatio = itemRate.AcceGreenItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceGreenItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceGreenItemMaxRate.get(nowGrade).getMaxStack();

            } else if (item.getGrade() == 2) {
                baseRatio = itemRate.AcceBlueItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceBlueItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceBlueItemMaxRate.get(nowGrade).getMaxStack();

            } else if (item.getGrade() == 3) {
                baseRatio = itemRate.AcceYellowItemMaxRate.get(nowGrade).getBaseRate();
                maxRatio = itemRate.AcceYellowItemMaxRate.get(nowGrade).getMaxRate();
                maxStack = itemRate.AcceYellowItemMaxRate.get(nowGrade).getMaxStack();

            }

            if (nowStack >= maxStack) nowStack = maxStack;


        }

        double balks = getBalks();

        currentRatio = baseRatio + bonusRatio * nowStack;

        Log.i("씨발년", currentRatio + " , " + balks);
        tv_ratio.setText("성공률 : " + (currentRatio + balks) + " 최대 성공률 :" + (maxRatio + balks));


    }

    public double getBalks() {

        return Stack.balks / 2;
    }


    public void onClick(final View v) {

        searchView.setVisibility(View.INVISIBLE);
        searchView.setQuery("", true);
        searchView.setIconified(true);


        EnchantItem item = (EnchantItem) v.getTag();

        // Log.i("키값", item.getSubType() + classType);

        if (item == null) return;

        if (item.getImgUrl().equals("null")) {
            onLongClick(v);
            return;
        }
        gotov.setTag(item);
        enchantGrade.setText("");


        focusView = item;

        previewRate(item);


        if (v instanceof Button) {

            if (gotov.getTag() == null) return;

            EnchantItem temp = (EnchantItem) gotov.getTag();
            if (temp.getNowGrade() == temp.getMaxGrade()) {
                //return;
            } else {

                switch (temp.getTableName()) {

                    case "무기":

                        if (weaponPowerUp(temp)) {
                            temp.success();
                            Stack.balks = 0;

                            Vibrator vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            if (temp.getNowGrade() > 7) mainActivity.seleceted = 0;

                        } else {

                            if (temp.getNowGrade() >= 0 && temp.getNowGrade() <= 14)
                                mainActivity.seleceted++;
                            else if (temp.getNowGrade() == 15) mainActivity.seleceted += 2;
                            else if (temp.getNowGrade() == 16) mainActivity.seleceted += 3;
                            else if (temp.getNowGrade() == 17) mainActivity.seleceted += 4;
                            else if (temp.getNowGrade() == 18) mainActivity.seleceted += 5;
                            else if (temp.getNowGrade() == 19) mainActivity.seleceted += 6;
                            temp.fail();

                        }

                        break;

                    case "방어구":

                        if (bodyPowerUp(temp)) {
                            temp.success();
                            Stack.balks = 0;

                            Vibrator vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            if (temp.getNowGrade() > 5) mainActivity.seleceted = 0;
                        } else {

                            if (temp.getNowGrade() >= 0 && temp.getNowGrade() <= 14) {
                                mainActivity.seleceted++;
                            } else if (temp.getNowGrade() == 15)
                                mainActivity.seleceted += 2;
                            else if (temp.getNowGrade() == 16) mainActivity.seleceted += 3;
                            else if (temp.getNowGrade() == 17) mainActivity.seleceted += 4;
                            else if (temp.getNowGrade() == 18) mainActivity.seleceted += 5;
                            else if (temp.getNowGrade() == 19) mainActivity.seleceted += 6;
                            temp.fail();
                        }

                        break;

                    case "악세":

                        if (accePowerUp(temp)) {
                            temp.success();
                            Stack.balks = 0;

                            Vibrator vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            mainActivity.seleceted = 0;
                        } else {

                            temp.setNowGrade(0);
                            mainActivity.seleceted++;
                            temp.fail();

                        }


                        break;


                }


                mainActivity.stackChanged();
                mainActivity.reloadStack();

                if (item != null) {
                    reloadData(temp);
                    previewRate(temp);

                }
            }
        }


        Gson gson = new Gson();

        String dir = getActivity().getApplicationContext().getFilesDir().getPath();
        File save = new File(dir + "/" + classType + "2.dat");
        Log.i("저작경로", save.getAbsolutePath());
        try {
            save.createNewFile();
            FileOutputStream fos = new FileOutputStream(save);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            osw.append('[');
            for (int i = 0; i < 13; i++) {
                ImageView temp = (ImageView) mainView.findViewById(R.id.gear_ear1 + i);
                osw.append(gson.toJson(temp.getTag()));
                if (i < 12) {
                    osw.append(',');
                }


            }
            osw.append(']');

            osw.flush();
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mainActivity, "자료 저장에 실패하였습니다. 오류코드 : " + e.toString(), Toast.LENGTH_SHORT).show();
        }


        ImageView imageView;
        for (int i = 0; i < 13; i++) {
            imageView = (ImageView) mainView.findViewById(R.id.gear_ear1_selected + i);
            imageView.setVisibility(View.INVISIBLE);
        }

        imageView = (ImageView) mainView.findViewById(item.idForSelect);
        imageView.setVisibility(View.VISIBLE);
        imageView = null;

        Picasso.with(getContext()).load(item.getImgUrl()).resize(100, 100).into(enchantItem);


        if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 15 && item.getNowGrade() > 0)
            enchantGrade.setText("+" + item.getNowGrade());
        else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() <= 16 && item.getNowGrade() > 15)
            enchantGrade.setText("I");
        else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() == 17)
            enchantGrade.setText("II");
        else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() == 18)
            enchantGrade.setText("III");
        else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() == 19)
            enchantGrade.setText("IV");
        else if ((!(item.getTableName().equals("악세"))) && item.getNowGrade() == 20)
            enchantGrade.setText("V");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 0)
            enchantGrade.setText("");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 1)
            enchantGrade.setText("I");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 2)
            enchantGrade.setText("II");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 3)
            enchantGrade.setText("III");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 4)
            enchantGrade.setText("IV");
        else if (item.getTableName().equals("악세") && item.getNowGrade() == 5)
            enchantGrade.setText("V");


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class GetDataFromDB extends AsyncTask<String, String, ImageView> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            itemlist.setAdapter(null);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if (search.equals("")) itemlist.setVisibility(View.GONE);
            else itemlist.setVisibility(View.VISIBLE);

            adapter.notifyDataSetChanged();

        }

        @Override
        protected ImageView doInBackground(String... params) {

            String subType = params[0]; //클래스 명
            String Type = params[1]; // 눌린 아이템 종류


            String itemType = "";

            if (subType.equals("war")) {
                if (Type.equals("W_PRI")) {
                    itemType = "SWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "GREATSWORD";
                } else itemType = "SHIELD";
            } else if (subType.equals("sorc")) {
                if (Type.equals("W_PRI")) {
                    itemType = "TALISMAN";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "SCYTHE";
                } else itemType = "AMULET";
            } else if (subType.equals("ranger")) {
                if (Type.equals("W_PRI")) {
                    itemType = "BOW";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "ELEMENTALSWORD";
                } else itemType = "DAGGER";
            } else if (subType.equals("giant")) {
                if (Type.equals("W_PRI")) {
                    itemType = "AXE";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "IRONGLOVES";
                } else itemType = "ORNAMENT";
            } else if (subType.equals("tamer")) {
                if (Type.equals("W_PRI")) {
                    itemType = "SHORTSWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "SKYBLUNT";
                } else itemType = "TOY";
            } else if (subType.equals("valki")) {
                if (Type.equals("W_PRI")) {
                    itemType = "SWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "LANCIA";
                } else itemType = "SHIELD";
            } else if (subType.equals("blader")) {
                if (Type.equals("W_PRI")) {
                    itemType = "BLADE";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "MUSINDO";
                } else itemType = "SHORTBOW";
            } else if (subType.equals("fblader")) {
                if (Type.equals("W_PRI")) {
                    itemType = "BLADE";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "WOLCHANG";
                } else itemType = "SHORTBOW";
            } else if (subType.equals("kuno")) {
                if (Type.equals("W_PRI")) {
                    itemType = "SHORTSWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "CHAKRAM";
                } else itemType = "KUNAISHURIKEN";
            } else if (subType.equals("ninza")) {
                if (Type.equals("W_PRI")) {
                    itemType = "SHORTSWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "SURADO";
                } else itemType = "KUNAISHURIKEN";
            } else if (subType.equals("witch")) {
                if (Type.equals("W_PRI")) {
                    itemType = "STAFF";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "SPHERE";
                } else itemType = "DAGGER";
            } else if (subType.equals("wizard")) {
                if (Type.equals("W_PRI")) {
                    itemType = "STAFF";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "SPHERE2";
                } else itemType = "DAGGER";
            } else if (subType.equals("darkelf")) {
                if (Type.equals("W_PRI")) {
                    itemType = "ELFSWORD";
                } else if (Type.equals("W_AWAKE")) {
                    itemType = "VEDIANT";
                } else itemType = "ORNAMENT";
            }

            Log.i("씨발뭐지", itemType);


            String sql = "";
            if (Type.equals("W_PRI") || Type.equals("W_AWAKE") || Type.equals("W_SECOND")) {
                //검색하려는 아이템 종류가 무기류 인경우..
                sql = "select * from db_items_base where `TYPE` = 'WEAPON' and `SUBTYPE` = '" + itemType + "' and `NAME_KR` LIKE '%" + search +
                        "%'";
            } else {
                //검색하려는 아이템 종류가 무기가 아닌경우..
                sql = "select * from db_items_base where `SUBTYPE` = '" + Type + "' and `NAME_KR` LIKE '%" + search +
                        "%'";
            }
            buffer = new StringBuffer();
            int _id = 0;

            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql, null);

                while (cursor.moveToNext()) {
                    buffer.append(cursor.getString(cursor.getColumnIndex("NAME_KR")));
                    buffer.append("\n");

                }
            }

            String[] tlqkf = buffer.toString().split("\n");


            searcheditem.clear();
            for (int i = 0; i < tlqkf.length; i++) {
                if (tlqkf[i].contains(search)) {
                    searcheditem.add(new SearchItem(tlqkf[i], 0));


                }
            }


            publishProgress();


            return null;


        }

        @Override
        protected void onPostExecute(ImageView imageView) {
            super.onPostExecute(imageView);
            itemlist.setAdapter(adapter);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
