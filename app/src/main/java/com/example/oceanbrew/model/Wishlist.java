package com.example.oceanbrew.model;

public class Wishlist {
    String category;
    String username;
    String nameofDrinks;

    public Wishlist() {
    }

    public Wishlist(String category,String nameofDrinks, String username) {
        this.nameofDrinks = nameofDrinks;
        this.username = username;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
