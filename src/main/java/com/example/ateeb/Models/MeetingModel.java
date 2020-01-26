package com.example.ateeb.Models;

public class MeetingModel
{
    public MeetingModel(int id, String course, String name, String uniid, String time, String date, String venue) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.uniid = uniid;
        this.time = time;
        this.date = date;
        this.venue = venue;
    }


    int id;
    String course;
    String name;
    String uniid;
    String time;
    String date;
    String venue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniid() {
        return uniid;
    }

    public void setUniid(String uniid) {
        this.uniid = uniid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
