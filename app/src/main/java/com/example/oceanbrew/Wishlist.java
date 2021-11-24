package com.example.oceanbrew;

public class Wishlist {
    String username;
    int IdWishlist;

    public Wishlist(String username, int idWishlist) {
        this.username = username;
        IdWishlist = idWishlist;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdWishlist() {
        return IdWishlist;
    }

    public void setIdWishlist(int idWishlist) {
        IdWishlist = idWishlist;
    }
}
