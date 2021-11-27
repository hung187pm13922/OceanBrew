package com.example.oceanbrew.model;

public class Drinks {

    String category;
    String garnish;
    String ingradients;
    String methol;
    String nameDrinks;
    String whenPost;

    public Drinks() {
    }

    public Drinks(String category, String garnish, String ingradients, String methol, String nameDrinks, String whenPost) {
        this.category = category;
        this.garnish = garnish;
        this.ingradients = ingradients;
        this.methol = methol;
        this.nameDrinks = nameDrinks;
        this.whenPost = whenPost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGarnish() {
        return garnish;
    }

    public void setGarnish(String garnish) {
        this.garnish = garnish;
    }

    public String getIngradients() {
        return ingradients;
    }

    public void setIngradients(String ingradients) {
        this.ingradients = ingradients;
    }

    public String getMethol() {
        return methol;
    }

    public void setMethol(String methol) {
        this.methol = methol;
    }

    public String getNameDrinks() {
        return nameDrinks;
    }

    public void setNameDrinks(String nameDrinks) {
        this.nameDrinks = nameDrinks;
    }

    public String getWhenPost() {
        return whenPost;
    }

    public void setWhenPost(String whenPost) {
        this.whenPost = whenPost;
    }
}
