package com.kkk8888.bdosimulator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class EnchantActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    TabLayout tabLayout;
    ViewPager viewPager;
    EnchantPager pagerAdapter;
    OutlineTextView stack_0, stack_1, stack_2, stack_3, stack_4, stack_5;
    int seleceted = 0;
    int now = 0;

    EnchantSecond enchantSecond;
    EnchantFirst enchantFirst;

    FloatingActionButton fab;

    View rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enchant);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Intent intent = getIntent();
        String classType = intent.getStringExtra("class");


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setSelectedTabIndicatorColor((Color.GRAY));
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);


        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                    } else {
                        Bitmap bm = getScreenShot(rootView);
                        Toast.makeText(EnchantActivity.this, "캡처 화면이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date()) + "bdo.jpg";
                        store(bm, filename);


                    }
                }

            }
        });


        stack_0 = (OutlineTextView) findViewById(R.id.stack_0);
        stack_1 = (OutlineTextView) findViewById(R.id.stack_1);
        stack_2 = (OutlineTextView) findViewById(R.id.stack_2);
        stack_3 = (OutlineTextView) findViewById(R.id.stack_3);
        stack_4 = (OutlineTextView) findViewById(R.id.stack_4);
        stack_5 = (OutlineTextView) findViewById(R.id.stack_5);


        tabLayout.addTab(tabLayout.newTab().setText("탭"));
        pagerAdapter = new EnchantPager(getSupportFragmentManager(), classType);


        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);


        Fragment fragment = pagerAdapter.getItem(2);
        enchantSecond = (EnchantSecond) fragment;
        fragment = pagerAdapter.getItem(1);
        enchantFirst = (EnchantFirst) fragment;


        for (int i = 0; i < 6; i++) {
            OutlineTextView temp = (OutlineTextView) findViewById(R.id.stack_0 + i);
            temp.setOnClickListener(this);
            temp.setOnLongClickListener(this);
        }

        stack_0.setText(Stack.stack_0 + "");
        stack_1.setText(Stack.stack_1 + "");
        stack_2.setText(Stack.stack_2 + "");
        stack_3.setText(Stack.stack_3 + "");
        stack_4.setText(Stack.stack_4 + "");
        stack_5.setText(Stack.stack_5 + "");

        stack_0.setBackgroundResource(R.drawable.item_selected);

        seleceted = Integer.parseInt((stack_0).getText().toString());

        Stack.stack_0 = 0;
        Stack.stack_1 = 21;
        Stack.stack_2 = 31;
        Stack.stack_3 = 41;
        Stack.stack_4 = 71;
        Stack.stack_5 = 81;


        reloadStack();

        getDBfile();


    }


    public void stackReset(View v) {

        for (int i = 0; i < 6; i++) {
            OutlineTextView temp = (OutlineTextView) findViewById(R.id.stack_0 + i);
            temp.setBackgroundColor(0xffffff);
        }

        stack_0.setBackgroundResource(R.drawable.item_selected);

        now = 0;
        seleceted = Stack.stack_0;


        Stack.stack_0 = 0;

        if (Stack.stack_1 > 21) {

        } else Stack.stack_1 = 21;

        if (Stack.stack_2 > 31) {

        } else Stack.stack_2 = 31;

        if (Stack.stack_3 > 41) {

        } else Stack.stack_3 = 41;

        if (Stack.stack_4 > 71) {

        } else Stack.stack_4 = 71;

        if (Stack.stack_5 > 81) {

        } else Stack.stack_5 = 81;


        reloadStack();


    }

    @Override
    public boolean onLongClick(View v) {

        for (int i = 0; i < 6; i++) {
            OutlineTextView temp = (OutlineTextView) findViewById(R.id.stack_0 + i);
            temp.setBackgroundColor(0xffffff);
        }

        v.setBackgroundResource(R.drawable.item_selected);
        getStack(v);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stack : " + (now + 1));
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_stack, null);
        final EditText et_stack = (EditText) view.findViewById(R.id.edit_stack);
        et_stack.setText(seleceted + "");
        final Button ok, cancle;
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equals("")) return;

                if ((Integer.parseInt(s.toString()) > 290)) {

                    et_stack.setText("290");
                    Toast.makeText(EnchantActivity.this, "최대 확률(291)을 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };
        et_stack.addTextChangedListener(textWatcher);

        ok = (Button) view.findViewById(R.id.btn_ok);
        cancle = (Button) view.findViewById(R.id.btn_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stackChanged(Integer.parseInt(et_stack.getText().toString()));
                dialog.dismiss();

            }
        });

        if (enchantFirst.focusView != null)
            enchantFirst.previewRate(enchantFirst.focusView);
        if (enchantSecond.focusView != null)
            enchantSecond.previewRate(enchantSecond.focusView);

        return true;
    }

    void getStack(View v) {

        switch (v.getId()) {
            case R.id.stack_0:
                now = 0;
                seleceted = Stack.stack_0;

                break;
            case R.id.stack_1:
                now = 1;
                seleceted = Stack.stack_1;

                break;
            case R.id.stack_2:
                now = 2;
                seleceted = Stack.stack_2;
                break;
            case R.id.stack_3:
                now = 3;
                seleceted = Stack.stack_3;

                break;
            case R.id.stack_4:
                now = 4;
                seleceted = Stack.stack_4;

                break;
            case R.id.stack_5:
                now = 5;
                seleceted = Stack.stack_5;

                break;
        }

    }

    @Override
    public void onClick(View v) {


        for (int i = 0; i < 6; i++) {
            OutlineTextView temp = (OutlineTextView) findViewById(R.id.stack_0 + i);
            temp.setBackgroundColor(0xffffff);
        }

        v.setBackgroundResource(R.drawable.item_selected);

        getStack(v);

        if (enchantSecond.focusView != null)
            enchantSecond.previewRate(enchantSecond.focusView);
        if (enchantFirst.focusView != null)
            enchantFirst.previewRate(enchantFirst.focusView);


    }

    void stackChanged() {

        switch (now) {
            case 0:
                Stack.stack_0 = seleceted;
                stack_0.setText(Stack.stack_0 + "");
                break;
            case 1:
                Stack.stack_1 = seleceted;
                stack_1.setText(Stack.stack_1 + "");
                break;
            case 2:
                Stack.stack_2 = seleceted;
                stack_2.setText(Stack.stack_2 + "");
                break;
            case 3:
                Stack.stack_3 = seleceted;
                stack_3.setText(Stack.stack_3 + "");
                break;
            case 4:
                Stack.stack_4 = seleceted;
                stack_4.setText(Stack.stack_4 + "");
                break;
            case 5:
                Stack.stack_5 = seleceted;
                stack_5.setText(Stack.stack_5 + "");
                break;
        }


    }

    void stackChanged(int editStack) {

        seleceted = editStack;

        switch (now) {
            case 0:
                Stack.stack_0 = editStack;
                stack_0.setText(Stack.stack_0 + "");
                break;
            case 1:
                Stack.stack_1 = editStack;
                stack_1.setText(Stack.stack_1 + "");
                break;
            case 2:
                Stack.stack_2 = editStack;
                stack_2.setText(Stack.stack_2 + "");
                break;
            case 3:
                Stack.stack_3 = editStack;
                stack_3.setText(Stack.stack_3 + "");
                break;
            case 4:
                Stack.stack_4 = editStack;
                stack_4.setText(Stack.stack_4 + "");
                break;
            case 5:
                Stack.stack_5 = editStack;
                stack_5.setText(Stack.stack_5 + "");
                break;
        }


    }

    void reloadStack() {
        stack_0.setText(Stack.stack_0 + "");
        stack_1.setText(Stack.stack_1 + "");
        stack_2.setText(Stack.stack_2 + "");
        stack_3.setText(Stack.stack_3 + "");
        stack_4.setText(Stack.stack_4 + "");
        stack_5.setText(Stack.stack_5 + "");
    }

    long ltb;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (System.currentTimeMillis() - ltb <= 1500) {

            finish();
            return;
        }

        Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        ltb = System.currentTimeMillis();
    }


    File file;

    public void store(Bitmap bm, String fileName) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots/";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        file = new File(dirPath, fileName);

        Uri uri = Uri.fromFile(file);

        Log.d("uri", uri.toString());


        try {
            Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();
            FileOutputStream fOut = new FileOutputStream(dirPath + fileName);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            sendBroadcast(intent);
            Log.d("broadcast", "브로드캐스트");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());

        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }


    void getDBfile() {
        AssetManager am = this.getResources().getAssets();


        File folder = new File("data/data/com.kkk8888.bdosimulator/databases");
        if (!folder.exists()) {
            folder.mkdir();
            Toast.makeText(this, "폴더생성", Toast.LENGTH_SHORT).show();
        }

        File file = new File("data/data/com.kkk8888.bdosimulator/databases/itemlist.db");
        try {

            if(file.exists()){
                file.delete();
                Toast.makeText(this, "기존 파일을 지웠고.", Toast.LENGTH_SHORT).show();
            }

            InputStream is = am.open("data.seq");
            long filesize = is.available();

            byte[] tempdata = new byte[(int) filesize];

            is.read(tempdata);

            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(tempdata);
            fos.close();
            Toast.makeText(this, "Load OK..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(this, "데이터베이스 로드 에러", Toast.LENGTH_SHORT).show();
        }

    }


}
