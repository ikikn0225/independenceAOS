package com.memory1.independence74.data;

public class UserInfoData {

    private String id;
    private String str_user_id;
    private String str_user_pw;
    private String str_user_name;
    private String str_user_image;
    private String image_str;

    public UserInfoData() {}

    public UserInfoData(String str_user_id, String str_user_pw, String str_user_name, String str_user_image) {
        this.str_user_id = str_user_id;
        this.str_user_pw = str_user_pw;
        this.str_user_name = str_user_name;
        this.str_user_image = str_user_image;
    }

    public UserInfoData(String id, String str_user_id, String str_user_pw, String str_user_name, String image_str) {
        this.id = id;
        this.str_user_id = str_user_id;
        this.str_user_pw = str_user_pw;
        this.str_user_name = str_user_name;
        this.image_str = image_str;
    }

    public String getStr_user_id() {
        return str_user_id;
    }

    public void setStr_user_id(String str_user_id) {
        this.str_user_id = str_user_id;
    }

    public String getStr_user_pw() {
        return str_user_pw;
    }

    public void setStr_user_pw(String str_user_pw) {
        this.str_user_pw = str_user_pw;
    }

    public String getStr_user_name() {
        return str_user_name;
    }

    public void setStr_user_name(String str_user_name) {
        this.str_user_name = str_user_name;
    }

    public String getStr_user_image() {
        return str_user_image;
    }

    public void setStr_user_image(String str_user_image) {
        this.str_user_image = str_user_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_str() {
        return image_str;
    }

    public void setImage_str(String image_str) {
        this.image_str = image_str;
    }
}
