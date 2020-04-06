package com.ownplan.com.ownplan.utils;

import java.util.Date;

public class Diary {
    private  Integer id ;
    private  String Title;
    private String Context;
    private Date time ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Diary() {
    }
}
