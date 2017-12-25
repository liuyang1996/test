package com.liuyang.android.newquestion;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.allen.comparsechart.CompareIndicator;
import com.liuyang.android.newquestion.thread.MyThread;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuyang on 2017/12/2.
 */
//这是一个投票系统
public class Main3 extends AppCompatActivity {
    ImageView img_back;
    Button btn_appr;
    Button btn_oppo;
    TextView vatecontent;
    CompareIndicator indicator ;
    boolean isExit;

    @SuppressLint("HandlerLeak")
    Handler reqHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            parseReqvateJson(result);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler setHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            parseSetvateJson(result);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        isExit = false;
        initView();
        initData();

    }

    public void onClick_Vate_back(View view){
        finish();
    }
    public void initView(){
        vatecontent = findViewById(R.id.vatecontent);
        indicator = findViewById(R.id.compareIndicator);
        btn_appr = findViewById(R.id.btn_appr);
        btn_oppo = findViewById(R.id.btn_oppo);
        img_back =  findViewById(R.id.main3_btn_back);
    }
    public void initData(){
        String url = "http://39.106.27.181:80/three/reqvote/?voteid=1";
        new MyThread(reqHandler,url).start();
    }
    //请求一个投票问题
    public void parseReqvateJson(String result) {
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            String content = object.getString("content");
            vatecontent.setText(content);
            int id = object.getInt("id");
            indicator.setTag(id);
            int agreenum = object.getInt("agreenum");
            int disagreenum = object.getInt("disagreenum");
            indicator.updateView(disagreenum,agreenum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //修改投票问题
    public void parseSetvateJson(String result){
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            int type = object.getInt("type");
            Toast.makeText(this,"type:"+type,Toast.LENGTH_SHORT).show();

            if (type == 1){
                int agreenum = object.getInt("agreenum");
                int disagreenum = object.getInt("disagreenum");
                Toast.makeText(Main3.this,""+agreenum+","+disagreenum,Toast.LENGTH_SHORT).show();
                indicator.updateView(disagreenum,agreenum);
            }else{
                Toast.makeText(this,"你已经参与的该投票！",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //支持
    public void onClick_appr(View view){
        int id = (int)indicator.getTag();
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        String username = preferences.getString("username","");
        String url = "http://39.106.27.181:80/three/setvote/?voteid="+id+"&username="+username+"&select="+1;
        new MyThread(setHandler,url).start();
    }
    //反对
    public void onClick_oppo(View view){
        int id = (int)indicator.getTag();
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        String username = preferences.getString("username","");
        String url = "http://39.106.27.181:80/three/setvote/?voteid="+id+"&username="+username+"&select="+0;
        new MyThread(setHandler,url).start();
    }
}
