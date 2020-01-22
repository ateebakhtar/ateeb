package com.example.ateeb.Models;

import java.util.Date;

public class Workload
{
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayleft() {
        return dayleft;
    }

    public void setDayleft(String dayleft) {
        this.dayleft = dayleft;
    }

    String day;
    String dayleft;
    String type;
    String Course;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Workload()
    {

    }

    public void insert(String date, String type, String course, String dayl,String id) {
        this.day = date;
        this.type = type;
        Course = course;
        this.dayleft = dayl;
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }


}
