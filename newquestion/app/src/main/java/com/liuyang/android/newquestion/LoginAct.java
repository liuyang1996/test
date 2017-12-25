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
import android.widget.EditText;
import android.widget.TextView;

import com.liuyang.android.newquestion.thread.MyThread;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuyang on 2017/12/13.
 */

public class LoginAct extends AppCompatActivity {

    EditText log_user;
    EditText log_pass;
    Button log_btn_login;
    TextView log_tv_warn;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //result为json数据，必须解析
            String result = msg.obj.toString();
            parseLoginJson(result);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        log_user = findViewById(R.id.log_user);
        log_pass = findViewById(R.id.log_password);
        log_btn_login = findViewById(R.id.log_btn_login);
        log_tv_warn = findViewById(R.id.log_tv_warn);
        log_tv_warn.setText("提示");
    }
    public void onClick_log_btn_login(View v){
        String user = String.valueOf(log_user.getText());
        String pass = String.valueOf(log_pass.getText());
        if(user == null || user.equals("")){
            log_tv_warn.setText("用户名为空！");
        }else{
            //用户名不为空值
            if(pass == null||pass.equals("")){
                log_tv_warn.setText("密码为空！");
            }else{
                //密码不为空值
                String url = "http://39.106.27.181:80/three/login/?username="+user+"&password="+pass;
                new MyThread(handler,url).start();

            }
        }
    }
    //若登录成功则跳转到mainActivity
    public void parseLoginJson(String jsonString){
        try {
            JSONObject object = new JSONObject(jsonString);
            int type = Integer.parseInt(object.getString("type"));
            String content = object.getString("content");
            if(type == 2){/*登录成功*/
//                存储用户名与密吗
                SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username",String.valueOf(log_user.getText()));
                editor.putString("password",String.valueOf(log_pass.getText()));
                editor.apply();
//                MyApplication myApplication = ((MyApplication)getApplication());
//                myApplication.setIslogin(true);
//                myApplication.setUsername(log_user.getText().toString());
//                Toast.makeText(myApplication.getApplicationContext(), myApplication.getUsername(), Toast.LENGTH_SHORT).show();
                log_tv_warn.setText(content);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else if(type == 1){
                log_tv_warn.setText(content);
            }else{
                log_tv_warn.setText(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

