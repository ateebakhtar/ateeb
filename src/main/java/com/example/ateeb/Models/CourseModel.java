package com.example.ateeb.Models;

public class CourseModel
{
    public CourseModel(String name, String section, String id) {
        this.name = name;
        this.section = section;
        this.id = id;
    }

    String name;
    String section;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
