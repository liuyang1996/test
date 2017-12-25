package com.liuyang.android.newquestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton head;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (ImageButton)findViewById(R.id.test1);
        button2 = (ImageButton)findViewById(R.id.test2);
        button3 = (ImageButton)findViewById(R.id.test3);
        head = (ImageButton) findViewById(R.id.head);
    }

    public void onClick_head(View view) {
        Intent intent = new Intent(MainActivity.this,AccountAct.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    public void onClick_test1(View view) {
        Intent intent1 = new Intent(MainActivity.this,Main1.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    public void onClick_test2(View view) {
        Intent intent2 = new Intent(MainActivity.this,Main2.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }

    public void onClick_test3(View view) {
        Intent intent3 = new Intent(MainActivity.this,Main3.class);
        startActivity(intent3);
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);

    }
}
