package com.ownplan.com.ownplan.utils;

import java.io.Serializable;

public class Diarygroup implements Serializable {
    Integer id;
    String name ;
    Integer diarycount;
    String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiarycount() {
        return diarycount;
    }

    public void setDiarycount(Integer diarycount) {
        this.diarycount = diarycount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @param id 合集的id
     * @param name 合集的名字
     * @param diarycount 合集里边的个数
     * @param image 图片的路径
     */
    public Diarygroup(Integer id, String name, Integer diarycount, String image) {
        this.id = id;
        this.name = name;
        this.diarycount = diarycount;
        this.image = image;
    }
}
