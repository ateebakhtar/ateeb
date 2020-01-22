package com.example.ateeb.Models;

public class UserData
{
    String name;
    String ID;
    String password;
    String uniid;

    public String getUniid() {
        return uniid;
    }

    public void setUniid(String uniid) {
        this.uniid = uniid;
    }

    public UserData()
    {

    }

    UserData(String n,String id,String pass)
    {
        name = n;
        ID = id;
        password = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
