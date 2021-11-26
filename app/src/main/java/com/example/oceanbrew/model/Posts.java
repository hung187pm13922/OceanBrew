package com.example.oceanbrew.model;

public class Posts {
    String garnish;
    String ingredients;
    String method;
    String nameOfDrink;
    String status;
    String typeOfDrinks;
    String username;
    String whenPost;

    public Posts() {
    }

    public Posts(String garnish, String ingredients, String method, String nameOfDrink, String status, String typeOfDrinks, String username, String whenPost) {
        this.garnish = garnish;
        this.ingredients = ingredients;
        this.method = method;
        this.nameOfDrink = nameOfDrink;
        this.status = status;
        this.typeOfDrinks = typeOfDrinks;
        this.username = username;
        this.whenPost = whenPost;
    }

    public String getGarnish() {
        return garnish;
    }

    public void setGarnish(String garnish) {
        this.garnish = garnish;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNameOfDrink() {
        return nameOfDrink;
    }

    public void setNameOfDrink(String nameOfDrink) {
        this.nameOfDrink = nameOfDrink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeOfDrinks() {
        return typeOfDrinks;
    }

    public void setTypeOfDrinks(String typeOfDrinks) {
        this.typeOfDrinks = typeOfDrinks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWhenPost() {
        return whenPost;
    }

    public void setWhenPost(String whenPost) {
        this.whenPost = whenPost;
    }
}
