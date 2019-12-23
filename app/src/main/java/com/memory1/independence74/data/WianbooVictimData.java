package com.memory1.independence74.data;

public class WianbooVictimData {

    private String wianboovictim_name;
    private String wianboovictim_year;
    private int wianboovictim_picture;

    //사진 없는 경우
    public WianbooVictimData(String wianboovictim_name, String wianboovictim_year) {
        this.wianboovictim_name = wianboovictim_name;
        this.wianboovictim_year = wianboovictim_year;
    }

    //3가지 정보 다 있는 경우
    public WianbooVictimData(String wianboovictim_name, String wianboovictim_year, int wianboovictim_picture) {
        this.wianboovictim_name = wianboovictim_name;
        this.wianboovictim_year = wianboovictim_year;
        this.wianboovictim_picture = wianboovictim_picture;
    }

    public String getWianboovictim_name() {
        return wianboovictim_name;
    }

    public void setWianboovictim_name(String wianboovictim_name) {
        this.wianboovictim_name = wianboovictim_name;
    }

    public String getWianboovictim_year() {
        return wianboovictim_year;
    }

    public void setWianboovictim_year(String wianboovictim_year) {
        this.wianboovictim_year = wianboovictim_year;
    }

    public int getWianboovictim_picture() {
        return wianboovictim_picture;
    }

    public void setWianboovictim_picture(int wianboovictim_picture) {
        this.wianboovictim_picture = wianboovictim_picture;
    }
}
