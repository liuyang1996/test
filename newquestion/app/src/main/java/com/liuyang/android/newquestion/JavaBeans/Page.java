package com.liuyang.android.newquestion.JavaBeans;

import java.util.ArrayList;

/**
 * Created by liuyang on 2017/12/1.
 */
public class Page {
    //问卷id
    private String pageId;
    //问卷状态
    private String status;
    //问卷主题
    private String title;
    //题目
    private ArrayList<Question> questions;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
