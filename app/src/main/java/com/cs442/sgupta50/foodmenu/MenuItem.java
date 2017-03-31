package com.cs442.sgupta50.foodmenu;

import android.media.Image;

import java.io.Serializable;

/**********************************
 Name : MenuItem

 Use: This class is for defining all the attributed of the menu item
 ***********************************/

public class MenuItem implements Serializable {

    String name;
    double price;
    String ingredients;
    String cuisine;
    int quantity;
    int id;
    Image img;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object obj) {
        MenuItem menuItem = (MenuItem) obj;
        if (this.getName().equals(menuItem.getName())) {
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return id + ". " + name + " " + quantity + "x" + price + "$";
    }
}
