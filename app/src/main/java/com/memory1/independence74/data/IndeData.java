package com.memory1.independence74.data;

public class IndeData {

    private String name;
    private String history;
    private String work;
    private int picture;
    private int picture2;
    private int picture3;
    private String inde_url;

    public IndeData() {}

    public IndeData(String name, String work, String history, int picture) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
    }

    public IndeData(String name, String work, String history, int picture, String inde_url) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
        this.inde_url = inde_url;
    }

    public IndeData(String name, String work, String history, int picture, int picture2) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
        this.picture2 = picture2;
    }

    public IndeData(String name, String work, String history, int picture, int picture2, String inde_url) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
        this.picture2 = picture2;
        this.inde_url = inde_url;
    }

    public IndeData(String name, String work, String history, int picture, int picture2, int picture3) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
        this.picture2 = picture2;
        this.picture3 = picture3;
    }

    public IndeData(String name, String work, String history, int picture, int picture2, int picture3, String inde_url) {
        this.name = name;
        this.work = work;
        this.history = history;
        this.picture = picture;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.inde_url = inde_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getPicture2() {
        return picture2;
    }

    public void setPicture2(int picture2) {
        this.picture2 = picture2;
    }

    public int getPicture3() {
        return picture3;
    }

    public void setPicture3(int picture3) {
        this.picture3 = picture3;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getInde_url() {
        return inde_url;
    }

    public void setInde_url(String inde_url) {
        this.inde_url = inde_url;
    }
}
