package com.example.ateeb.Models;

public class ReminderModel
{
    String coursename;
    String date;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public ReminderModel()
    {

    }
    public ReminderModel(String c,String d, String t,String idd)
    {
        id = idd;
        coursename = c;
        date = d;
        type = t;
    }
}
