package com.kkk8888.bdosimulator;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kakao.auth.Session;
import com.kakao.usermgmt.response.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by alfo06-18 on 2017-07-26.
 */

public class EnchantNotice extends Fragment {

    UserProfile profile;
    String userName;
    long userID;
    String userImg;

    RequestQueue queue;

    Button loadBtn, writeBtn;


    String loadBoardUrl = "http://ec2-52-78-134-69.ap-northeast-2.compute.amazonaws.com/loadBoard.php";
    String insertBoardUrl = "http://ec2-52-78-134-69.ap-northeast-2.compute.amazonaws.com/imgUpload.php";
    ArrayList<BoardItem> board = new ArrayList<>();
    RecyclerView boardList;
    BoardAdapter adapter;
    Uri uri = null;
    String imgPath;


    ImageView pickup;

    LinearLayout bar;

    final static long cooltime = 3000;
    long ltb;

    SwipeRefreshLayout swipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    bar.setVisibility(View.GONE);
                    loadBoard();
                    break;
                case 1:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 10);
                    break;
                case 2:
                    bar.setVisibility(View.GONE);
                    break;
                case 3:
                    pickup.setClickable(true);

                    break;
            }


        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enchant_notice, container, false);

        profile = UserProfile.loadFromCache();
        userID = profile.getId();
        userName = profile.getNickname();
        userImg = profile.getThumbnailImagePath();
        boardList = (RecyclerView) view.findViewById(R.id.boardList);
        loadBtn = (Button) view.findViewById(R.id.loadBtn);

        bar = (LinearLayout) view.findViewById(R.id.progress);

        bar.setVisibility(View.GONE);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // board.clear();
                loadBoard();
                bar.setVisibility(View.VISIBLE);
            }
        });


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //board.clear();
                loadBoard();
            }
        });

        adapter = new BoardAdapter(board, getContext());

        boardList.setAdapter(adapter);
        boardList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        loadBoard();

        writeBtn = (Button) view.findViewById(R.id.write);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!Session.getCurrentSession().isOpened()) {
                    Toast.makeText(getContext(), "비 로그인자는 글 작성을 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("글 작성하기");
                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_enchant_board_write, null);
                builder.setView(view);

                final AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

                ImageView img = (ImageView) view.findViewById(R.id.profile_img);
                Picasso.with(getContext()).load(userImg).into(img);
                TextView writer = (TextView) view.findViewById(R.id.profile_name);
                writer.setText(userName + "님");

                final EditText edit_title = (EditText) view.findViewById(R.id.edit_title);
                final EditText edit_content = (EditText) view.findViewById(R.id.edit_content);


                Button btn = (Button) view.findViewById(R.id.write_cancle);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn = (Button) view.findViewById(R.id.write_add);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (imgPath == null) return;

                        if (edit_title.getText().toString().equals("") || edit_content.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "필수 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        bar.setVisibility(View.VISIBLE);

                        RequestQueue queue = Volley.newRequestQueue(getContext());

                        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, insertBoardUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                handler.sendEmptyMessageDelayed(0, 100);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getContext(), "에러", Toast.LENGTH_SHORT).show();
                            }
                        });

                        smpr.addStringParam("name", userName);
                        smpr.addStringParam("title2", edit_title.getText().toString());
                        smpr.addStringParam("msg", edit_content.getText().toString());
                        smpr.addFile("upload", imgPath);
                        queue.add(smpr);


                        dialog.dismiss();

                    }
                });


                pickup = (ImageView) view.findViewById(R.id.edit_img);
                pickup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickup.setClickable(false);

                        //마시멜로 이상 버전이면. 런타임 퍼미션 획득해야한다.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int checkPermission = getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);

                            switch (checkPermission) {
                                case PackageManager.PERMISSION_DENIED:

                                    //다이얼로그 화면이 띄워진다
                                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                                    break;
                                case PackageManager.PERMISSION_GRANTED:

                                    break;
                            }
                        }


                        //내폰에있느 ㄴ이미지를 선택해야해해
                        //사진 목록을 보여주는 앱(갤러리,사진)의 화면을 불러야해

                        handler.sendEmptyMessageDelayed(1, 1000);
                        handler.sendEmptyMessageDelayed(3, 3000);



                    }
                });

            }
        });


        return view;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:


                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    Toast.makeText(getContext(), "사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode != RESULT_OK) return;

                //선택된 경로정보를 가지고있는 Uri객체를 얻어오자


                uri = data.getData();
                imgPath = uri.toString();
                imgPath = uri.toString();
                if (imgPath.contains("content://")) {
                    //이미지 경로가 db에있다는걸 아는거지..

                    ContentResolver resolver = getActivity().getContentResolver();

                    // null = * 과 같다.. select * from 일떄 *
                    Cursor cursor = resolver.query(uri, null, null, null, null);

                    if (cursor != null && cursor.getCount() != 0) {

                        cursor.moveToNext();
                        imgPath = cursor.getString(cursor.getColumnIndex("_data"));
                    }

                }
                Glide.with(getContext()).load(uri).into(pickup);
                break;
        }

    }


    void loadBoard() {


        if (System.currentTimeMillis() - ltb < cooltime) {

            Toast.makeText(getContext(), "잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            swipe.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(200);
            swipe.setRefreshing(false);
            handler.sendEmptyMessageDelayed(2, 200);

            return;
        }

        ltb = System.currentTimeMillis();

        boardList.setAdapter(null);

        board.clear();

        queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, loadBoardUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i("로그", response);
                String[] strs = response.split(";");


                for (int i = 0; i < strs.length; i++) {
                    String[] unitStrs = strs[i].split("&");

                    if (unitStrs.length < 4) continue;

                    if (unitStrs[4].equals("1")) {
                        board.add(0, new BoardItem(unitStrs[0], unitStrs[1], unitStrs[2], unitStrs[3], unitStrs[4]));

                    } else {
                        board.add(new BoardItem(unitStrs[0], unitStrs[1], unitStrs[2], unitStrs[3], unitStrs[4]));

                    }

                    //Log.i("시발",unitStrs[i]);
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                        boardList.setAdapter(adapter);
                        bar.setVisibility(View.GONE);
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }


}
