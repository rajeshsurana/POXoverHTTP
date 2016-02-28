/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajeshsurana.Concrete;

/**
 *
 * @author Rajesh Surana
 */
public class FoodItem {
    private String country;
    private String name;
    private String description;
    private String category;
    private String price;
    private String id;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "<FoodItem country=\"" + country + "\"> <id>"+ id +"</id> <name>" + name + "</name> <description>" + description + "</description> <category>" + category + "</category> <price>" + price + "</price> </FoodItem>";
    }
}
