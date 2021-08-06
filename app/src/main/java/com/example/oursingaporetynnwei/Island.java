package com.example.oursingaporetynnwei;

import java.io.Serializable;

public class Island implements Serializable {

    private int id;
    private String name;
    private String desc;
    private int sqkm;
    private int stars;

    public Island(String name, String desc, int sqkm, int stars) {
        this.name = name;
        this.desc = desc;
        this.sqkm = sqkm;
        this.stars = stars;
    }

    public Island(int id, String name, String desc, int sqkm, int stars) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.sqkm = sqkm;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Island setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Island setName(String title) {
        this.name = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Island setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getSqkm() {
        return sqkm;
    }

    public Island setSqkm(int sqkm) {
        this.sqkm = sqkm;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Island setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @Override
    public String toString() {
        String starsString = "";
        if (stars == 5){
            starsString = "*****";
        } else if (stars == 4){
            starsString = "****";
        }

        //or
        for(int i = 0; i < stars; i++){
            starsString += "*";
        }
        return name + "\n" + desc + " - " + sqkm + "\n" + starsString;

    }
}
