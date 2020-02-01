package com.example.ateeb.Models;

public class MeetingModel
{
    public MeetingModel(int id, String course, String name, String uniid, String time, String date, String venue,String capacity,String topic,String description ) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.uniid = uniid;
        this.time = time;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.topic = topic;
        this.description = description;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String topic;
    String description;
    String capacity;
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
