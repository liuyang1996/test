package com.liuyang.android.newquestion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by liuyang on 2017/12/3.
 */

public class RegisterAndLogin extends AppCompatActivity {
    Button login_btn;
    Button register_btn;
    ImageView back_btn;
    boolean isExit;
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerandlogin);
        isExit = false;

    }
    public void onClick_login(View v){
        Intent intent = new Intent(this,LoginAct.class);
        startActivity(intent);
    }
    public void onClick_register(View v){
        Intent intent = new Intent(this,RegisterAct.class);
        startActivity(intent);
    }
    public void onClick_back(View v){
        finish();
    }

    /**
     * onKeyDown方法，该方法是接口KeyEvent.Callback中的抽象方法，
     * 所有的View全部实现了该接口并重写了该方法，该方法用来捕捉手机
     * 键盘被按下的事件。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

}
