package com.example.ateeb.Models;

public class AttendeesModel
{
    public AttendeesModel(int id, int meetingID, String name, String uniid) {
        this.id = id;
        this.meetingID = meetingID;
        this.name = name;
        this.uniid = uniid;
    }

    int id;
    int meetingID;
    String name;
    String uniid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
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
}
