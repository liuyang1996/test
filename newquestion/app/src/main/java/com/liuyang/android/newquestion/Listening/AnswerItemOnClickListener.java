package com.liuyang.android.newquestion.Listening;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.liuyang.android.newquestion.JavaBeans.Answer;
import com.liuyang.android.newquestion.JavaBeans.Question;
import com.liuyang.android.newquestion.R;

import java.util.ArrayList;

/**
 * Created by liuyang on 2017/12/1.
 */

public class AnswerItemOnClickListener implements View.OnClickListener{
    private int i;
    private int j;
    private TextView txt;
    private ArrayList<Answer> the_answer_lists;
    Context context;
//    // 答案列表
//    private ArrayList<Answer> the_answer_list;
    // 问题列表
    private ArrayList<Question> the_question_list;
    private ArrayList<ArrayList<TextView>> textlist;

    public AnswerItemOnClickListener(Context context, int i, int j, ArrayList<Question> the_question_list, ArrayList<Answer>the_answer_list, TextView text, ArrayList<ArrayList<TextView>> textlist) {

        this.textlist = textlist;
        this.context = context;
        this.i = i;
        this.j = j;
        this.the_question_list = the_question_list;
        this.the_answer_lists = the_answer_list;
        this.txt = text;
    }

    // 实现点击选项后改变选中状态以及对应图片
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        // 判断当前问题是单选还是多选
        if (the_question_list.get(i).getType().equals("3")) {//1选择题；2判断题 3不定项
            //Question question = the_question_list.get(i);
            //标记：这一行为改进代码
            ArrayList<Answer> the_answer_list = the_question_list.get(i).getAnswers();

            for(int z=0;z<the_answer_list.size();z++){
                if(the_answer_list.get(z).getAns_state()==1){
                    the_question_list.get(i).setQue_state(1);
                }else{
                    the_question_list.get(i).setQue_state(0);
                }
            }
            if (the_answer_lists.get(j).getAns_state() == 0) {
//                    Toast.makeText(getApplication(), "0", Toast.LENGTH_SHORT).show();
                textlist.get(i).get(j).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_red));
                textlist.get(i).get(j).setTextColor(context.getResources().getColor(R.color.colorWhite));
                the_answer_lists.get(j).setAns_state(1);//注意一下这些相应的角标是用i 、j还是z。
                the_question_list.get(i).setQue_state(1);
            } else {
//                    Toast.makeText(getApplication(), "1", Toast.LENGTH_SHORT).show();
                textlist.get(i).get(j).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_white));
                textlist.get(i).get(j).setTextColor(context.getResources().getColor(R.color.colorblack));
                the_answer_lists.get(j).setAns_state(0);
                the_question_list.get(i).setQue_state(0);
            }
        }
        else {// 单选
            for (int z = 0; z < the_answer_lists.size(); z++) {
                if (z == j) {
//                        Toast.makeText(getApplication(), "3", Toast.LENGTH_SHORT).show();
                    // 如果当前未被选中
                    textlist.get(i).get(j).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_red));
                    textlist.get(i).get(j).setTextColor(context.getResources().getColor(R.color.colorWhite));
                    the_answer_lists.get(z).setAns_state(1);
                    the_question_list.get(i).setQue_state(1);
                } else {
//                        Toast.makeText(getApplication(), "4", Toast.LENGTH_SHORT).show();
                    the_answer_lists.get(z).setAns_state(0);
                    the_question_list.get(i).setQue_state(1);
                    textlist.get(i).get(z).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_white));
                    textlist.get(i).get(z).setTextColor(context.getResources().getColor(R.color.colorblack));
                }
            }
        }
    }
}
