package com.example.oceanbrew.model;

public class Search {
    String category;
    String garnish;
    String ingradients;
    String methol;
    String nameDrinks;
    String username;
    String link;

    public Search() {
    }

    public Search(String category, String garnish, String ingradients, String link, String methol, String nameDrinks, String username) {
        this.category = category;
        this.garnish = garnish;
        this.ingradients = ingradients;
        this.methol = methol;
        this.nameDrinks = nameDrinks;
        this.username = username;
        this.link = link;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
