package com.liuyang.android.newquestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuyang.android.newquestion.JavaBeans.Answer;
import com.liuyang.android.newquestion.JavaBeans.Page;
import com.liuyang.android.newquestion.JavaBeans.Question;
import com.liuyang.android.newquestion.Listening.AnswerItemOnClickListener;
import com.liuyang.android.newquestion.Listening.SubmitOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** 
 * Created by liuyang on 2017/12/1.
 */

public class Main1 extends AppCompatActivity {

    // 数据源 假数据
    //json数据可以通过网络请求来获得

    private String Str_Json = "{\n" +
            "    \"result\": \"1\",\n" +
            "    \"type\": \"1\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 1,\n" +
            "            \"problem\": \"你是否喜欢本专业？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"持喜欢态度\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"还可以（一般）\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"喜欢一两科\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"持不喜欢态度\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 2,\n" +
            "            \"problem\": \"入学以来你对自己的专业了解多少？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"很了解\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"大致了解\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"一般了解\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"不太了解\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 3,\n" +
            "            \"problem\": \"你平均每天的自习时间是多少？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"3小时以上\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"2小时\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"一小时\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"几乎不自习\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 4,\n" +
            "            \"problem\": \"你认为你的学习自主性高吗？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"很高\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"较高\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"一般\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"不高\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 5,\n" +
            "            \"problem\": \"上课时你记笔记吗？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"必须记\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"有时记\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"偶尔记\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"根本不记\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 6,\n" +
            "            \"problem\": \"上课时你会向老师问问题吗？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"经常\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"有时\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"偶尔\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"根本不\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 7,\n" +
            "            \"problem\": \"上课前你对有些课程进行预习吗？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"经常\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"有时\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"偶尔\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"不预习\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 8,\n" +
            "            \"problem\": \"你是否严格按照作息时间进行学习？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"很严格\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"比较严格\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"一般\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"随自己\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 9,\n" +
            "            \"problem\": \"逃过课吗？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"经常\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"1\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"有时\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"偶尔\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"几乎没有\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"type\": 2,\n" +
            "            \"eid\": 10,\n" +
            "            \"problem\": \"若对自己现在的学习成绩不满意，有想过哪些改变现状的方法？\",\n" +
            "            \"optionData\": [\n" +
            "                {\n" +
            "                    \"option\": \"制订了明确的学习计划并严格执行 \",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"10\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"会努力一段时间，但没有坚持到底 \",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"7\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"循序渐进，慢慢调整 \",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"4\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"option\": \"没想过\",\n" +
            "                    \"isChecked\": \"0\",\n" +
            "                    \"grade\": \"0\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private List<View> list_que_view;
    private LinearLayout test_layout;
    private Page the_page;
    // 答案列表
    private ArrayList<Answer> the_answer_list;
    // 问题列表
    private ArrayList<Question> the_question_list;
    // 问题所在的View
    private View que_view;
    // 答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;
    private Page page;
    // 下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    // 存每个问题下的TextView
    private ArrayList<ArrayList<TextView>> textlist = new ArrayList<ArrayList<TextView>>();
    // 存每个答案的TextView
    private ArrayList<TextView> textlist2;
    //返回按钮
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        xInflater = (LayoutInflater) LayoutInflater.from(this);
        // 初始化数据
        initDate();
        back = (ImageView)findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.btn_back:
                        Main1.this.finish();
                        break;
                }
            }
        });
        // 提交按钮
        TextView button = (TextView) findViewById(R.id.tv_commit);
        button.setOnClickListener(new SubmitOnClickListener(this,page,list_que_view));
    }

    private void initDate() {
//        //通过Url获取Json
//        String url = "";
//        UrlToJson(url);

        //解析json
        page = parse_Json();
        // 加载布局
        initView(page);
    }

    private String UrlToJson(String url) {
        String result = null;

        return result;
    }

    private void initView(Page page) {
        // TODO Auto-generated method stub
        list_que_view = new ArrayList<>();
        // 这是要把问题的动态布局加入的布局
        test_layout = (LinearLayout) findViewById(R.id.lly_test);
        //当前问卷的题目
        TextView page_txt = (TextView) findViewById(R.id.tv_class_title);
        page_txt.setText(page.getTitle());
        // 获得问题即第二层的数据
        the_question_list = page.getQuestions();
        // 根据第二层问题的多少，来动态加载布局
        for (int i = 0; i < the_question_list.size(); i++) {
            que_view = xInflater.inflate(R.layout.question_layout, null);
            list_que_view.add(que_view);
            ImageView iv_type = (ImageView) que_view.findViewById(R.id.iv_type);
            TextView txt_que = (TextView) que_view.findViewById(R.id.txt_question_item);
            // 这是第三层布局要加入的地方
            LinearLayout add_layout = (LinearLayout) que_view.findViewById(R.id.lly_answer);
            //v_line为每一个问题的分隔条
            View v_line = (View) que_view.findViewById(R.id.v_line);
            if(i == 0){//第一道题目的分隔条不用显示
                v_line.setVisibility(View.GONE);
            }

            //类型：1选择题；2判断题 3不定项
            if (the_question_list.get(i).getType().equals("2")) {
                iv_type.setImageResource(R.mipmap.single_menu);
            } else if (the_question_list.get(i).getType().equals("1")){
                iv_type.setImageResource(R.mipmap.judge_menu);
            } else if (the_question_list.get(i).getType().equals("3")){
                iv_type.setImageResource(R.mipmap.more_menu);
            }
            String str = (i+1) +"、"+the_question_list.get(i).getContent();

            txt_que.setText(str);//设置问题题目
            // 获得答案即第三层数据
            the_answer_list = the_question_list.get(i).getAnswers();
            textlist2 = new ArrayList<>();
            for (int j = 0; j < the_answer_list.size(); j++) {

                ans_view = xInflater.inflate(R.layout.answer_layout, null);
                //答案内容
                TextView txt_ans = (TextView) ans_view.findViewById(R.id.txt_answer_item);
                //答案的字母序（ABCD）
                TextView tv_menu = (TextView) ans_view.findViewById(R.id.tv_menu);

                //自己手动为每个问题的选项加上相应的ABCD...
                if (j==0){
                    tv_menu.setText("A");
                }else if (j==1){
                    tv_menu.setText("B");
                }else if (j==2){
                    tv_menu.setText("C");
                }else if (j==3){
                    tv_menu.setText("D");
                }else if (j==4){
                    tv_menu.setText("E");
                }else if (j==5){
                    tv_menu.setText("F");
                }else if (j==6){
                    tv_menu.setText("G");
                }

                textlist2.add(tv_menu);
                txt_ans.setText(the_answer_list.get(j).getAnswer_content());
                LinearLayout lly_answer_size = (LinearLayout) ans_view.findViewById(R.id.lly_answer_size);

                if (j%2!=0){//为了美观了，将答案的背景隔开一下
                    lly_answer_size.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                //设置点击事件
                lly_answer_size .setOnClickListener(new AnswerItemOnClickListener(this, i, j, the_question_list, the_answer_list,txt_ans,textlist));
                add_layout.addView(ans_view);
            }
            textlist.add(textlist2);

            test_layout.addView(que_view);//将生成的问题都添加到一个布局文件中
        }
    }


    public Page parse_Json(){
        ArrayList<Question> questionsList = null;
        try {
            questionsList = new ArrayList<>();//问题列表

            JSONObject resultJson = new JSONObject(Str_Json);
            JSONArray arrayJson = resultJson.optJSONArray("data");


            for (int i=0;i<arrayJson.length();i++){
                //subObject为一个完整问题的json格式
                JSONObject subObject = arrayJson.getJSONObject(i);

                ArrayList<Answer> answers = new ArrayList<>();

                JSONArray arrayAnswerJson = subObject.optJSONArray("optionData");//问题的答案
                for (int j=0; j< arrayAnswerJson.length(); j++) {
                    JSONObject answerObject = arrayAnswerJson.getJSONObject(j);
                    Answer a_answer = new Answer();
                    a_answer.setAnswerId(""+j);
                    a_answer.setAnswer_content(answerObject.getString("option"));
                    a_answer.setAns_state(Integer.parseInt(answerObject.getString("isChecked")));
                    a_answer.setGrade(Integer.parseInt(answerObject.getString("grade")));
                    answers.add(a_answer);
                }
                Question q_question = new Question();
                q_question.setQuestionId(subObject.getString("eid"));//问题的id
                q_question.setType(subObject.getString("type"));//类型，1判断 2单选 3不定项
                q_question.setContent(subObject.getString("problem"));//问题
                q_question.setAnswers(answers);
                q_question.setQue_state(0);

                questionsList.add(q_question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        page = new Page();
        page.setPageId("1");
        page.setStatus("0");
        //问卷的题目也需要添加到Json数据中--------------------------
        page.setTitle("学习状况问卷调查");
        page.setQuestions(questionsList);
        // 加载布局
        return page;
    }

}
