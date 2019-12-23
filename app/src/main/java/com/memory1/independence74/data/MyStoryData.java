package com.memory1.independence74.data;

public class MyStoryData {

    private String name;
    private String date;
    private String logined_name;
    private String logined_text;
    private String user_picture;
    private String text_picture;

    public MyStoryData() {}

    //text_picture가 없는 경우
//    public MyStoryData(Uri user_picture, String name, String date, String logined_name, String logined_text) {
//        this.user_picture = user_picture;
//        this.name = name;
//        this.date = date;
//        this.logined_name = logined_name;
//        this.logined_text = logined_text;
//    }

    //모든 데이터가 있는 경우
    public MyStoryData(String user_picture, String name, String date, String text_picture, String logined_name, String logined_text) {
        this.user_picture = user_picture;
        this.name = name;
        this.date = date;
        this.text_picture = text_picture;
        this.logined_name = logined_name;
        this.logined_text = logined_text;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogined_name() {
        return logined_name;
    }

    public void setLogined_name(String logined_name) {
        this.logined_name = logined_name;
    }

    public String getLogined_text() {
        return logined_text;
    }

    public void setLogined_text(String logined_text) {
        this.logined_text = logined_text;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getText_picture() {
        return text_picture;
    }

    public void setText_picture(String text_picture) {
        this.text_picture = text_picture;
    }
}
