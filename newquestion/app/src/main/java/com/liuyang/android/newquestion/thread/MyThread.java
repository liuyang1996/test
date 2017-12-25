package com.liuyang.android.newquestion.thread;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyang on 2017/12/15.
 */


public class MyThread extends Thread{
    private String url;
    private Handler handler;
    public MyThread(Handler handler, String url){
        this.url = url;
        this.handler = handler;
    }
    @Override
    public void run() {
        super.run();
        String result = "";
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();
            //接受后台传来的数据
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream(),"utf-8"));
            String str = "";

            while((str = reader.readLine()) != null){
                result += str;
            }
            reader.close();
            Message msg = Message.obtain();
            msg.obj = result;
            handler.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

