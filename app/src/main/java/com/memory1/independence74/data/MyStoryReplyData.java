package com.memory1.independence74.data;

public class MyStoryReplyData {
    private String user_image;
    private String user_name;
    private String user_text;
    private String date;

    public MyStoryReplyData(){}

    public MyStoryReplyData(String user_image, String user_name, String user_text, String date) {
        this.user_image = user_image;
        this.user_name = user_name;
        this.user_text = user_text;
        this.date = date;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_text() {
        return user_text;
    }

    public void setUser_text(String user_text) {
        this.user_text = user_text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
