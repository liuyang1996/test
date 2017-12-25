package com.liuyang.android.newquestion.Listening;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liuyang.android.newquestion.JavaBeans.Answer;
import com.liuyang.android.newquestion.JavaBeans.Page;
import com.liuyang.android.newquestion.JavaBeans.Question;
import com.liuyang.android.newquestion.TestresultAct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyang on 2017/12/1.
 */
//要实现淡入淡出跳转动画
public class SubmitOnClickListener implements View.OnClickListener {
    private Context context;

    // 问题列表
    private ArrayList<Question> the_question_list;

    private List<View> list_que_view;

    public SubmitOnClickListener(Context context, Page page,List<View> list_que_view) {
        this.context = context;

        the_question_list = page.getQuestions();
        this.list_que_view = list_que_view;

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        // 判断是否答完题
        boolean isState = true;
        // 最终要的json数组
        JSONArray jsonArray = new JSONArray();
        //用户所得的总分
        int total_grade = 0;
        // 点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
        // 注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
        for (int i = 0; i < the_question_list.size(); i++) {
            ArrayList<Answer> the_answer_list = the_question_list.get(i).getAnswers();
            // 判断是否有题没答完
            if (the_question_list.get(i).getQue_state() == 0) {
//                list_que_view.get(i).setFocusable(true);
//                list_que_view.get(i).setFocusableInTouchMode(true);
//                list_que_view.get(i).requestFocus(View.FOCUS_UP);
//                list_que_view.get(i).requestFocusFromTouch();
//                handler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        //list_que_view.scrollTo(0, list_que_view.get(i).getHeight());
//                    }
//                });
                Toast.makeText(context.getApplicationContext(), "您第" + (i + 1) + "题没有答完", Toast.LENGTH_LONG).show();

                jsonArray = null;
                isState = false;
                return;
            } else {
                JSONObject json = new JSONObject();
                String answers2 = "";
                String answers = "";
                //该用户单个问题的得分
                int grade = 0;
                //当前问题的答案列表  遍历
                for (int j = 0; j < the_answer_list.size(); j++) {
                    //如果该选项被选中
                    if (the_answer_list.get(j).getAns_state() == 1) {

                        grade = the_answer_list.get(j).getGrade();
                        try {
                            answers2 = the_question_list.get(i).getQuestionId();
                            if (answers.length()==0){
                                answers = answers + j;
                            }else {
                                answers = answers +"-"+ j;
                            }

                            //===为不定项拼接答案================================
                            if (answers.contains("0")) {
                                answers = answers.replace("0", "A");
                            }
                            if (answers.contains("1")) {
                                answers = answers.replace("1", "B");
                            }
                            if (answers.contains("2")) {
                                answers = answers.replace("2", "C");
                            }
                            if (answers.contains("3")) {
                                answers = answers.replace("3", "D");
                            }
                            if (answers.contains("4")) {
                                answers = answers.replace("4", "E");
                            }
                            if (answers.contains("5")) {
                                answers = answers.replace("5", "F");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    json.put("answer", answers);
                    json.put("eid", answers2);
//                        Toast.makeText(getApplicationContext(), json + "", Toast.LENGTH_SHORT).show();
                    jsonArray.put(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                total_grade = total_grade + grade;
            }
        }
        Log.d("jsonArray-->", "" + jsonArray);
        //得到数据jsonArray
        Toast.makeText(context.getApplicationContext(), "提交的数据：" + jsonArray, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, TestresultAct.class);
        //Bundle bundle = new Bundle();
        intent.putExtra("num",String.valueOf(total_grade));
        intent.putExtra("type","1");
        context.startActivity(intent);


    }
}

