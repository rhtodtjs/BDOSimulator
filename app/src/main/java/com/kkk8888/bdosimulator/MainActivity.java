package com.kkk8888.bdosimulator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private SessionCallback callback;
    TextView user_nickname;
    LinearLayout success_layout;
    LoginButton loginButton;

    AQuery aQuery;

    LinearLayout selectLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aQuery = new AQuery(this);
        callback = new SessionCallback();
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.kkk8888.bdosimulator", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Session.getCurrentSession().addCallback(callback);
        Toast.makeText(this, "gd", Toast.LENGTH_SHORT).show();


        // 카카오톡 로그인 버튼
        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isConnected()) {
                        Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                }

                if (isConnected()) {
                    return false;
                } else {
                    return true;
                }
            }
        });

        // 로그인 성공 시 사용할 뷰
        success_layout = (LinearLayout) findViewById(R.id.success_layout);
        user_nickname = (TextView) findViewById(R.id.user_nickname);
        selectLayout = (LinearLayout) findViewById(R.id.select_panel);
        selectLayout.setVisibility(View.VISIBLE);


////        if (Session.getCurrentSession().isOpened()) {
////            requestMe();
////        } else {
//        success_layout.setVisibility(View.GONE);
//        loginButton.setVisibility(View.GONE);
//
////        }

    }


    //인터넷 연결상태 확인
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            //access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태. 일반적으로 로그인 후의 다음 activity로 이동한다.
            if (Session.getCurrentSession().isOpened()) { // 한 번더 세션을 체크해주었습니다.
                requestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    private void requestLogout() {

        success_layout.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        selectLayout.setVisibility(View.GONE);
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void requestMe() {
        success_layout.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        selectLayout.setVisibility(View.VISIBLE);

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("onFailure", errorResult + "");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("onSessionClosed", errorResult + "");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("onSuccess", userProfile.toString());
                user_nickname.setText("접속 계정 : " + userProfile.getNickname());
            }

            @Override
            public void onNotSignedUp() {
                Log.e("onNotSignedUp", "onNotSignedUp");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    public void quit(View v) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "종료합니다..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        finish();
    }

    public void Enchant(View v) {

        startActivity(new Intent(this, SelectcharActivity.class));
    }


}



