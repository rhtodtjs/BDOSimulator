package com.kkk8888.bdosimulator;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnchantFirst extends Fragment implements View.OnClickListener, View.OnLongClickListener {


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

    OutlineTextView gear_helmet_nowgrade, gear_body_nowgrade, gear_ring1_nowgrade, gear_ring2_nowgrade, gear_glove_nowgrade;
    OutlineTextView gear_neak_nowgrade, gear_wea_sec_nowgrade, gear_wea_awake_nowgrade, gear_wea_pri_nowgrade;
    OutlineTextView gear_ear1_nowgrade, gear_ear2_nowgrade, gear_shoes_nowgrade, gear_belt_nowgrade;

    Button gotov; //돌파 버튼

    ImageView needItem, enchantItem; //재료 아이템 , 강화 시킬 아이템


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

        View view = inflater.inflate(R.layout.fragment_enchant_first, container, false);
        mainView = view;

        getDBfile();


        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.kkk8888.bdosimulator/databases/itemlist.db", null);

        settingView(mainView);


        return view;

    }

    void settingView(View view) {
        searchView = (SearchView) view.findViewById(R.id.searchBtn);
        mainLayout = (RelativeLayout) view.findViewById(R.id.enchant_frag1);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.INVISIBLE);
                searchView.setQuery("", true);
                searchView.setIconified(true);
            }
        });


        searchView.setIconifiedByDefault(false);
        itemlist = (ListView) view.findViewById(R.id.itemlist);

        adapter = new ListViewAdapter(searcheditem, getContext());
        itemlist.setAdapter(adapter);

        ap = (TextView) view.findViewById(R.id.ap);
        dp = (TextView) view.findViewById(R.id.dp);
        wap = (TextView) view.findViewById(R.id.wap);

        gotov = (Button) view.findViewById(R.id.gotov);
        gotov.setOnClickListener(this);

        needItem = (ImageView) view.findViewById(R.id.needItem);
        enchantItem = (ImageView) view.findViewById(R.id.enchantItem);


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


        gear_helmet = (ImageView) view.findViewById(R.id.gear_helmet);
        gear_helmet.setTag(new EnchantItem("HELMET", "null", 0, 0, 0, 0, 0, "방어구", "null", R.id.gear_helmet_selected));
        gear_body = (ImageView) view.findViewById(R.id.gear_body);
        gear_body.setTag(new EnchantItem("BODY", "null", 0, 0, 0, 0, 0, "방어구", "null", R.id.gear_body_selected));
        gear_ring1 = (ImageView) view.findViewById(R.id.gear_ring1);
        gear_ring1.setTag(new EnchantItem("RING", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_ring1_selected));
        gear_ring2 = (ImageView) view.findViewById(R.id.gear_ring2);
        gear_ring2.setTag(new EnchantItem("RING", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_ring2_selected));
        gear_glove = (ImageView) view.findViewById(R.id.gear_glove);
        gear_glove.setTag(new EnchantItem("GLOVES", "null", 0, 0, 0, 0, 0, "방어구", "null", R.id.gear_glove_selected));
        gear_neak = (ImageView) view.findViewById(R.id.gear_neck);
        gear_neak.setTag(new EnchantItem("NECKLACE", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_neck_selected));
        gear_wea_sec = (ImageView) view.findViewById(R.id.gear_wea_second);
        gear_wea_sec.setTag(new EnchantItem("W_SECOND", "null", 0, 0, 0, 0, 0, "무기", "null", R.id.gear_wea_second_selected));
        gear_wea_awake = (ImageView) view.findViewById(R.id.gear_wea_awake);
        gear_wea_awake.setTag(new EnchantItem("W_AWAKE", "null", 0, 0, 0, 0, 0, "무기", "null", R.id.gear_wea_awake_selected));
        gear_wea_pri = (ImageView) view.findViewById(R.id.gear_wea_pri);
        gear_wea_pri.setTag(new EnchantItem("W_PRI", "null", 0, 0, 0, 0, 0, "무기", "null", R.id.gear_wea_pri_selected));
        gear_ear1 = (ImageView) view.findViewById(R.id.gear_ear1);
        gear_ear1.setTag(new EnchantItem("EARRING", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_ear1_selected));
        gear_ear2 = (ImageView) view.findViewById(R.id.gear_ear2);
        gear_ear2.setTag(new EnchantItem("EARRING", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_ear2_selected));
        gear_shoes = (ImageView) view.findViewById(R.id.gear_shoe);
        gear_shoes.setTag(new EnchantItem("SHOES", "null", 0, 0, 0, 0, 0, "방어구", "null", R.id.gear_shoe_selected));
        gear_belt = (ImageView) view.findViewById(R.id.gear_belt);
        gear_belt.setTag(new EnchantItem("BELT", "null", 0, 0, 0, 0, 0, "악세", "null", R.id.gear_belt_selected));


        ImageView tempView = null;

        for (int i = 0; i < 13; i++) {
            tempView = (ImageView) view.findViewById(R.id.gear_ear1 + i);
            tempView.setOnClickListener(this);
            tempView.setOnLongClickListener(this);
        }


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
            Toast.makeText(getContext(), "데이터베이스 로드 완료", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getContext(), "데이터베이스 로드 에러", Toast.LENGTH_SHORT).show();
        }

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
            if (item.getNowGrade() == 0) continue;
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

    @Override
    public boolean onLongClick(final View v) {

        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnchantItem item = (EnchantItem) v.getTag();
                ImageView image = (ImageView) v;
                String loadImg = "";
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


                if (item.getTableName().equals("방어구")) {

                    String sql2 = "select * from Blunt where `_id` = '" + searcheditem.get(position).get_id() + "' and `Enchant` = '" + item.getNowGrade() + "'";
                    if (db.isOpen()) {

                        Cursor cursor = db.rawQuery(sql2, null);


                        while (cursor.moveToNext()) {


                            item.setMinDMG(cursor.getInt(cursor.getColumnIndex("MDV")) + cursor.getInt(cursor.getColumnIndex("MPV")));


                        }

                    }

                } else if (item.getTableName().equals("무기")) {

                    String sql2 = "select * from LongSword where `_id` = '" + searcheditem.get(position)._id + "' and `Enchant` = '" + item.getNowGrade() + "'";
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
                            }
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

        Toast.makeText(getContext(), item.getItemId() + "시발 " + item.getTableName(), Toast.LENGTH_SHORT).show();


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

    @Override
    public void onClick(final View v) {

        EnchantItem item = (EnchantItem) v.getTag();
        gotov.setTag(item);


        if (v instanceof Button) {

            if (gotov.getTag() == null) return;

            EnchantItem temp = (EnchantItem) gotov.getTag();
            if (temp.getNowGrade() == temp.getMaxGrade()) return;
            temp.success();

            Log.i("씨발", temp.getNowGrade() + ".......");
            reloadData(temp);
        }


        StringBuffer buffer = new StringBuffer();
        buffer.append("현재 단계 : " + item.getNowGrade() + "\n");
        buffer.append("테이블 이름 " + item.getItemType() + "\n");
        buffer.append("공격력 증가량 : " + item.getMaxDMG() + "\n");
        buffer.append("방어력 증가량 : " + item.getMinDMG() + "\n");
        buffer.append("서브 타입 : " + item.getSubType());
        buffer.append("맥스 강화수치 : " + item.getMaxGrade());
//        Toast.makeText(getContext(), buffer.toString(), Toast.LENGTH_SHORT).show();

        ImageView imageView;
        for (int i = 0; i < 13; i++) {
            imageView = (ImageView) mainView.findViewById(R.id.gear_ear1_selected + i);
            imageView.setVisibility(View.INVISIBLE);
        }

        imageView = (ImageView) mainView.findViewById(item.idForSelect);
        imageView.setVisibility(View.VISIBLE);
        imageView = null;

        Picasso.with(getContext()).load(item.getImgUrl()).resize(100, 100).into(enchantItem);


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
            }


            String sql = "";
            if (Type.equals("W_PRI") || Type.equals("W_AWAKE") || Type.equals("W_SECOND")) {
                //검색하려는 아이템 종류가 무기류 인경우..
                sql = "select * from db_items_base where `TYPE` = 'WEAPON' and `SUBTYPE` = '" + itemType + "' and `SEARCHDATA` LIKE '%" + search +
                        "%'";
            } else {
                //검색하려는 아이템 종류가 무기가 아닌경우..
                sql = "select * from db_items_base where `SUBTYPE` = '" + Type + "' and `SEARCHDATA` LIKE '%" + search +
                        "%'";
            }
            buffer = new StringBuffer();
            int _id = 0;

            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql, null);

                while (cursor.moveToNext()) {
                    buffer.append(cursor.getString(cursor.getColumnIndex("NAME_KR")));
                    buffer.append("\n");
//                    _id = cursor.getInt(cursor.getColumnIndex("_id"));

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

}
