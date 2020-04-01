package com.ownplan.com.ownplan.utils;

public class Plan {

    private  String doWhat;
    private String time ;

    public Plan(String time, String doWhat) {

        this.doWhat = doWhat;
        this.time = time;
    }

    public String getDoWhat() {
        return doWhat;
    }

    public void setDoWhat(String doWhat) {
        this.doWhat = doWhat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
