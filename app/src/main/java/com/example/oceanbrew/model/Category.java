package com.example.oceanbrew.model;

public class Category {
    String name;
    int id;
    String urlimage;

    public Category() {
    }

    public Category(String name, int id, String URLImage) {
        this.name = name;
        this.id = id;
        this.urlimage = URLImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getURLImage() {
        return urlimage;
    }

    public void setURLImage(String URLImage) {
        this.urlimage = URLImage;
    }
}
