package com.liuyang.android.newquestion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.liuyang.android.newquestion.adapter.MyPagerAdapter;
import com.liuyang.android.newquestion.thread.MyThread;
import com.liuyang.android.newquestion.view.MyViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyang on 2017/12/1.
 */

public class Main2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBack;
    private MyViewPager viewPager;
    // 存放子项view
    private List<View> viewItems = new ArrayList<>();
    // adapter
    private MyPagerAdapter viewpagerAdpter;

    private List<String> questionList;
    private LinearLayout mViewpagef;


    private LinearLayout vpLinparentnow; // 当前的viewpager嵌套在外层的linerlayout
    private LinearLayout vpLinparentnext; // 下一个的viewpager嵌套在外层的linerlayout
    private LinearLayout vpLinparenttop; // 上一个的viewpager嵌套在外层的linerlayout

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result  = msg.obj.toString();
            questionList = parse_Json(result);
            initData();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_oldtest);
        setView();
        //initData();
        String url = "http://39.106.27.181:80/three/reqhealth/";
        new MyThread(handler,url).start();
    }

    /**
     * 设置返回按钮
     * 填充viewitems
     */
    protected void setView() {
        mViewpagef = (LinearLayout) findViewById(R.id.vpfather);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        viewPager = (MyViewPager) findViewById(R.id.vote_submit_viewpager);
        //设置返回按钮
        btnBack.setOnClickListener(this);

        for (int i = 0; i < 10; i++) {
            viewItems.add(getLayoutInflater().inflate(R.layout.act_oldtesting,
                    null));
        }
        // 第一次进入的时候不执行该方法，设置透明度
        vpLinparentnext = (LinearLayout) viewItems.get(1).findViewById(
                R.id.vp_parent);
        vpLinparentnext.setAlpha(0.5f);// 初始化的时候默认把第二页透明度设置为0.5
    }

    protected void initData() {

        viewpagerAdpter = new MyPagerAdapter(this, viewItems, questionList);
        viewPager.setOffscreenPageLimit(3);// viewPager设置缓存的页面数
        viewPager.setPageMargin(-20);// 设置2张图之前的间距，-20就是会有边距的效果
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(viewpagerAdpter);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * @param position 页面改变后的position
         */
        @Override
        public void onPageSelected(int position) {
            vpLinparentnow = (LinearLayout) viewItems.get(position)
                    .findViewById(R.id.vp_parent);
            vpLinparentnow.setAlpha(1f); // 设置当前的alpha为1
            if (position == 0) {
                vpLinparentnext = (LinearLayout) viewItems.get(position + 1)
                        .findViewById(R.id.vp_parent);
                vpLinparentnext.setAlpha(0.5f);
            } else if (position == viewItems.size() - 1) {
                vpLinparenttop = (LinearLayout) viewItems.get(position - 1)
                        .findViewById(R.id.vp_parent);
                vpLinparenttop.setAlpha(0.5f);
            } else {
                vpLinparentnext = (LinearLayout) viewItems.get(position + 1)
                        .findViewById(R.id.vp_parent);
                vpLinparenttop = (LinearLayout) viewItems.get(position - 1)
                        .findViewById(R.id.vp_parent);
                vpLinparentnext.setAlpha(0.5f);
                vpLinparenttop.setAlpha(0.5f);
            }
            // 判断当前的选项是否有选中的
            RadioButton chkNothave = (RadioButton) viewItems.get(position)
                    .findViewById(R.id.chk_nothave);
            RadioButton chkSometimes = (RadioButton) viewItems.get(position)
                    .findViewById(R.id.chk_sometimes);
            RadioButton chkUsual = (RadioButton) viewItems.get(position)
                    .findViewById(R.id.chk_usual);
            // 如果当前有选中的项，则可以滑动
            if (chkNothave.isChecked() || chkSometimes.isChecked()
                    || chkUsual.isChecked()) {
                viewPager.isCompleteable(true);
            } else {
                viewPager.isCompleteable(false);
            }
        }

        // 做刷新操作，不然View会重叠有重影
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // to refresh frameLayout
            if (mViewpagef != null) {
                mViewpagef.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

    /**
     * @param index
     *  根据索引值切换页面
     */
    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public List<String> parse_Json(String result){
        List<String> questions = null;
        try {
             questions = new ArrayList<>();//问题列表
            JSONObject resultJson = new JSONObject(result);
            JSONArray arrayJson = resultJson.optJSONArray("questions");

            for (int i=0;i<arrayJson.length();i++){
                JSONObject subObject = arrayJson.getJSONObject(i);

                String question = subObject.getString("content");
                questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

//    public List<String> getQuestionList(){
//        List<String> question = new ArrayList<>();
//        Random rand = new Random();
//        int randNum = rand.nextInt(5) + 1;
//        Log.w("randNum", String.valueOf(randNum));
//        // 根据产生的随机数获取题目
//        switch (randNum) {
//            case 1:
//                for (int i = 1; i <= 10; i++) {
//                    question.add(i + "、"
//                            + questionsList.get(i - 1).getTest_cotent());
//                }
//                break;
//            case 2:
//                for (int i = 1; i <= 10; i++) {
//                    question.add(i + "、"
//                            + questionsList.get(i + 9).getTest_cotent());
//                }
//                break;
//            case 3:
//                for (int i = 1; i <= 10; i++) {
//                    question.add(i + "、"
//                            + questionsList.get(i + 19).getTest_cotent());
//                }
//                break;
//            case 4:
//                for (int i = 1; i <= 10; i++) {
//                    question.add(i + "、"
//                            + questionsList.get(i + 29).getTest_cotent());
//                }
//                break;
//            case 5:
//                for (int i = 1; i <= 10; i++) {
//                    question.add(i + "、"
//                            + questionsList.get(i + 39).getTest_cotent());
//                }
//                break;
//        }
//        return question;
//
//    }

}



