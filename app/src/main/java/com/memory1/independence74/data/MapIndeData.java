package com.memory1.independence74.data;

public class MapIndeData {

    //역사관 이름
    private String name;
    //역사관 위치
    private String location;
    //내 위치와 역사관과의 거리
    private String distance;
    //역사관 사진
    private int picture;
    //역사관 홈페이지(필수 아님)
    private String homepage_url;

    public MapIndeData() {}

    //홈페이지가 없을때
    public MapIndeData(String name, String location, String distance, int picture) {
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.picture = picture;
    }

    //지도 사용해서 거리와 위치 정보 불러와서 필요가 없는 생성자
    public MapIndeData(String name, int picture, String distance, String homepage_url, String location) {
        this.name = name;
        this.picture = picture;
        this.distance = distance;
        this.homepage_url = homepage_url;
        this.location = location;
    }

    //홈페이지가 없을때
    public MapIndeData(String name, int picture, String distance, String location) {
        this.name = name;
        this.picture = picture;
        this.distance = distance;
        this.location = location;
    }

    //홈페이지가 있을때
    public MapIndeData(String name, String location, String distance, int picture, String homepage_url) {
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.picture = picture;
        this.homepage_url = homepage_url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getHomepage_url() {
        return homepage_url;
    }

    public void setHomepage_url(String homepage_url) {
        this.homepage_url = homepage_url;
    }

}
