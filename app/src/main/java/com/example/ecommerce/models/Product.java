package com.example.ecommerce.models;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private double price;
    private int imageResourceId;
    private double rating;
    private String category;  // New field for product category

    // Updated constructor to include category and rating
    public Product(int id, String name, String description, double price, int imageResourceId, double rating, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.rating = rating;
        this.category = category;  // Initialize category
    }

    // Existing constructor (for example, if it's used for dummy data)
    public Product(String name, String price, int imageResourceId, String description, String category, float rating) {
        this.name = name;
        this.price = Double.parseDouble(price.replace("₹", "").trim());  // Assuming price includes currency symbol
        this.imageResourceId = imageResourceId;
        this.description = description;
        this.rating = rating;
        this.category = category;  // Initialize category
    }

    // Getter methods for all fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public double getRating() {
        return rating;  // Return the product rating
    }

    public String getCategory() {
        return category;  // Return the product category
    }

    // Overriding equals and hashCode for object comparison based on 'id'
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
