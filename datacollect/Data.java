package com.example.alice_wang.datacollect;

/**
 * Created by alice_wang on 16/8/10.
 */
import java.util.ArrayList;

public class Data {

    public String name;
    public String password;
    public String sex;
    public String age;

    public int lcount;
    public ArrayList time;
    public ArrayList acceleration;
    public ArrayList gyroscope;
    public ArrayList everyacceleration;
    public ArrayList everygyroscope;
    public ArrayList maxacceleration;
    public ArrayList minacceleration;
    public ArrayList maxgyroscope;
    public ArrayList mingyroscope;
    public ArrayList averageacc;
    public ArrayList averagegyr;
    public ArrayList pressure;
    public ArrayList size;


    public Data(String name, String password) {
        this.name = name;
        this.password=password;
    }

    public void putInData(String sex, String age, int lcount, ArrayList time, ArrayList acceleration, ArrayList gyroscope,
                        ArrayList everyacceleration, ArrayList everygyroscope, ArrayList maxacceleration, ArrayList minacceleration,
                          ArrayList maxgyroscope, ArrayList mingyroscope, ArrayList averageacc, ArrayList averagegyr,
                          ArrayList prs, ArrayList size){
        this.sex = sex;
        this.age = age;
        this.lcount = lcount;
        this.time = time;
        this.acceleration = acceleration;
        this.gyroscope = gyroscope;
        this.everyacceleration = everyacceleration;
        this.everygyroscope = everygyroscope;
        this.maxacceleration = maxacceleration;
        this.minacceleration = minacceleration;
        this.maxgyroscope = maxgyroscope;
        this.mingyroscope = mingyroscope;
        this.averageacc = averageacc;
        this.averagegyr = averagegyr;
        this.pressure = prs;
        this.size = size;

    }
}