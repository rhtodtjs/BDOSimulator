package com.kkk8888.bdosimulator;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class EnchantActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    TabLayout tabLayout;
    ViewPager viewPager;
    EnchantPager pagerAdapter;
    OutlineTextView stack_0, stack_1, stack_2, stack_3, stack_4, stack_5;
    int seleceted = 0;
    int now = 0;

    EnchantSecond enchantSecond;

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
        Stack.stack_2 = 44;
        Stack.stack_3 = 58;
        Stack.stack_4 = 68;
        Stack.stack_5 = 95;


        reloadStack();


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
        Stack.stack_1 = 21;
        Stack.stack_2 = 44;
        Stack.stack_3 = 58;
        Stack.stack_4 = 68;
        Stack.stack_5 = 95;

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
        builder.setTitle("Stack : " + now);
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

                if(s.toString().equals("")) return;

                if ((Integer.parseInt(s.toString()) > 300)) {

                    et_stack.setText("300");
                    Toast.makeText(EnchantActivity.this, "최대 확률은 255를 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();

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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stackChanged(Integer.parseInt(et_stack.getText().toString()));
                dialog.dismiss();

            }
        });


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
