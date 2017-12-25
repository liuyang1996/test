package com.liuyang.android.newquestion;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class RegisterAct extends AppCompatActivity {

    EditText reg_user;
    EditText reg_pass1;
    EditText reg_pass2;
    TextView reg_tv_warn;
    Button reg_btn_register;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            parseRegisterJson(result);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        reg_user = findViewById(R.id.reg_user);
        reg_pass1 = findViewById(R.id.reg_password1);
        reg_pass2 = findViewById(R.id.reg_password2);
        reg_btn_register = findViewById(R.id.reg_btn_register);
        reg_tv_warn = findViewById(R.id.reg_tv_warn);

    }
    public void onClick_reg_btn_register(View v){

        String user = String.valueOf(reg_user.getText());
        String pass1 = String.valueOf(reg_pass1.getText());
        String pass2 = String.valueOf(reg_pass2.getText());

        if(user == null || user.equals("")){
            reg_tv_warn.setText("用户名为空！");
        }else{
            //用户名不为空值
            if(pass1 == null||pass1.equals("")||pass2 == null||pass2.equals("")){
                reg_tv_warn.setText("密码为空！");
            }else if(!(pass1.equals(pass2))){
                reg_tv_warn.setText("密码前后不一致！");
            }else{
                //可以将信息提交到服务器了,
                String url = "http://39.106.27.181:80/three/register/?username="+user+"&password="+pass1;
                new MyThread(handler,url).start();
            }
        }
    }
    public void parseRegisterJson(String jsonString){
        try {
            JSONObject object = new JSONObject(jsonString);
            int  type = Integer.parseInt(object.getString("type"));
            String content = object.getString("content");
            if(type == 2){
                reg_tv_warn.setText(content);
                Intent intent = new Intent(this,LoginAct.class);
                startActivity(intent);
            }else{
                reg_tv_warn.setText(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
