package com.example.oceanbrew.model;

public class Posts {
    String NameOfDrink;
    String Ingredients;
    String TypeOfDrinks;
    String Garnish;
    String Method;
    String WhenPost;
    String Username;
    String Status;

    public Posts(String nameOfDrink, String ingredients, String typeOfDrinks, String garnish, String method, String whenPost, String username, String status) {
        NameOfDrink = nameOfDrink;
        Ingredients = ingredients;
        TypeOfDrinks = typeOfDrinks;
        Garnish = garnish;
        Method = method;
        WhenPost = whenPost;
        Username = username;
        Status = status;
    }

    public String getNameOfDrink() {
        return NameOfDrink;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public String getTypeOfDrinks() {
        return TypeOfDrinks;
    }

    public String getGarnish() {
        return Garnish;
    }

    public String getMethod() {
        return Method;
    }

    public String getWhenPost() {
        return WhenPost;
    }

    public String getUsername() { return Username; }

    public String getStatus() { return Status; }
}
