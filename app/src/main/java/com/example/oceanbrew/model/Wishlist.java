package com.example.oceanbrew.model;

public class Wishlist {
    String category;
    String username;
    String nameofDrinks;
    String link;

    public Wishlist() {
    }

    public Wishlist(String category, String link, String nameofDrinks, String username) {
        this.category = category;
        this.username = username;
        this.nameofDrinks = nameofDrinks;
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameofDrinks() {
        return nameofDrinks;
    }

    public void setNameofDrinks(String nameofDrinks) {
        this.nameofDrinks = nameofDrinks;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
