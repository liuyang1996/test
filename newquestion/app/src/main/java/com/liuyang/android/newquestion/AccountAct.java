package com.liuyang.android.newquestion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuyang.android.newquestion.thread.MyThread;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuyang on 2017/12/13.
 */

public class AccountAct extends AppCompatActivity {

    ImageView header;
    ImageView account_btn_back;
    TextView account_name;
    //TextView account_sign;
    TextView account_healthscore;
    TextView account_studyscore;
    Button account_logout;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            parseAacountJson(result);

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        initView();
        initData();

    }

    public void initView(){
        header = findViewById(R.id.account_header);
        account_logout = findViewById(R.id.account_logout);
        account_btn_back = findViewById(R.id.account_btn_back);
        account_name = findViewById(R.id.account_name);
        //account_sign = findViewById(R.id.account_sign);
        account_healthscore = findViewById(R.id.account_healthscore);
        account_studyscore = findViewById(R.id.account_studyscore);
    }

    private void initData() {
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        String url = "http://39.106.27.181:80/three/reqaccount/?username="+preferences.getString("username","");
        new MyThread(handler,url).start();
    }
    public void onClick_logout(View view){
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        Intent intent = new Intent(this,RegisterAndLogin.class);
        startActivity(intent);
    }
    public void parseAacountJson(String result){
        try {
            JSONObject object = new JSONObject(result);
            String sign = object.getString("sign");
            String healthscore = object.getString("healthscore");
            String studyscore = object.getString("studyscore");
            String name = object.getString("username");
            //account_sign.setText(sign);
            account_healthscore.setText(healthscore);
            account_studyscore.setText(studyscore);
            account_name.setText(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onClick_back(View view){
        finish();
    }

    public void onClick_header(View view) {
    }
}
