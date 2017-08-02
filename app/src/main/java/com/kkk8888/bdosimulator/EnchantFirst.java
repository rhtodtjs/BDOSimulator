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
    ArrayList<String> searcheditem = new ArrayList<>();
    StringBuffer buffer;
    ListViewAdapter adapter;

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(final View v) {

        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView image = (ImageView) v;
                String loadImg = "";
                String sql = "select * from db_items_base where `NAME_KR` = '" + searcheditem.get(position) + "'";
                if (db.isOpen()) {

                    Cursor cursor = db.rawQuery(sql, null);
                    loadImg = "http://bddatabase.net/items/";


                    while (cursor.moveToNext()) {
                        loadImg += cursor.getString(cursor.getColumnIndex("ICONIMAGEFILE"));
                        loadImg = loadImg.replace(".dds", ".png");
                    }

                }
                Toast.makeText(getContext(), loadImg, Toast.LENGTH_SHORT).show();


                Picasso.with(getContext()).load(loadImg).resize(125,125).into(image);

                //Glide.with(getContext()).load(loadImg).into(image);

            }
        });


        searchView.requestFocus();

        TextView text = ((TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));

        text.setBackgroundColor(Color.WHITE);

        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String tagName = v.getTag().toString();
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
                String tagName = v.getTag().toString();

                searcheditem.clear();

                GetDataFromDB dataFromDB = new GetDataFromDB();
                dataFromDB.execute(classType, tagName);
                search = newText;


                return false;
            }
        });


    }


    class GetDataFromDB extends AsyncTask<String, String, ImageView> {


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if (search.equals("")) itemlist.setVisibility(View.GONE);
            else itemlist.setVisibility(View.VISIBLE);

            adapter.notifyDataSetChanged();
            //Toast.makeText(getContext(), values.toString(), Toast.LENGTH_SHORT).show();
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

            Log.i("시팔10234", "무기 종류 : " + itemType + " 장비 종류 : " + Type);


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
            if (db.isOpen()) {

                Cursor cursor = db.rawQuery(sql, null);

                while (cursor.moveToNext()) {
                    buffer.append(cursor.getString(cursor.getColumnIndex("NAME_KR")));
                    buffer.append("\n");
                }

                Log.i("조같넹", buffer.toString());
            }

            String[] tlqkf = buffer.toString().split("\n");


            searcheditem.clear();
            for (int i = 0; i < tlqkf.length; i++) {
                if (tlqkf[i].contains(search)) {
                    searcheditem.add(tlqkf[i]);
//                    Log.e("dasd", tlqkf[i]);
                }
            }


            publishProgress();


            return null;
        }

    }


    String classType;
    TextView info_ratio;
    ImageView gear_helmet, gear_body, gear_ring1, gear_ring2, gear_glove;
    ImageView gear_neak, gear_wea_sec, gear_wea_awake, gear_wea_pri;
    ImageView gear_ear1, gear_ear2, gear_shoes, gear_belt;

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

        searchView = (SearchView) view.findViewById(R.id.searchBtn);
        info_ratio = (TextView) view.findViewById(R.id.info_ratio);
        info_ratio.setText("직업 : " + classType);
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


        gear_helmet = (ImageView) view.findViewById(R.id.gear_helmet);
        gear_helmet.setTag("HELMET");
        gear_body = (ImageView) view.findViewById(R.id.gear_body);
        gear_body.setTag("BODY");
        gear_ring1 = (ImageView) view.findViewById(R.id.gear_ring1);
        gear_ring1.setTag("RING");
        gear_ring2 = (ImageView) view.findViewById(R.id.gear_ring2);
        gear_ring2.setTag("RING");
        gear_glove = (ImageView) view.findViewById(R.id.gear_glove);
        gear_glove.setTag("GLOVES");
        gear_neak = (ImageView) view.findViewById(R.id.gear_neck);
        gear_neak.setTag("NECKLACE");
        gear_wea_sec = (ImageView) view.findViewById(R.id.gear_wea_second);
        gear_wea_sec.setTag("W_SECOND");
        gear_wea_awake = (ImageView) view.findViewById(R.id.gear_wea_awake);
        gear_wea_awake.setTag("W_AWAKE");
        gear_wea_pri = (ImageView) view.findViewById(R.id.gear_wea_pri);
        gear_wea_pri.setTag("W_PRI");
        gear_ear1 = (ImageView) view.findViewById(R.id.gear_ear1);
        gear_ear1.setTag("EARRING");
        gear_ear2 = (ImageView) view.findViewById(R.id.gear_ear2);
        gear_ear2.setTag("EARRING");
        gear_shoes = (ImageView) view.findViewById(R.id.shoe);
        gear_shoes.setTag("SHOES");
        gear_belt = (ImageView) view.findViewById(R.id.belt);
        gear_belt.setTag("BELT");

        gear_helmet.setOnClickListener(this);
        gear_body.setOnClickListener(this);
        gear_ring1.setOnClickListener(this);
        gear_ring2.setOnClickListener(this);
        gear_glove.setOnClickListener(this);
        gear_neak.setOnClickListener(this);
        gear_wea_sec.setOnClickListener(this);
        gear_wea_awake.setOnClickListener(this);
        gear_wea_pri.setOnClickListener(this);
        gear_ear1.setOnClickListener(this);
        gear_ear2.setOnClickListener(this);
        gear_shoes.setOnClickListener(this);
        gear_belt.setOnClickListener(this);

        getDBfile();


        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.kkk8888.bdosimulator/databases/itemlist.db", null);


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
            Toast.makeText(getContext(), "데이터베이스 로드 완료", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getContext(), "데이터베이스 로드 에러", Toast.LENGTH_SHORT).show();
        }

    }


}
