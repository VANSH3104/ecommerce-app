package com.example.ecommerce.models;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(Product product) {
        // Check if product already exists in cart
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                // Increment quantity
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }

        // Add new cart item
        cartItems.add(new CartItem(product, 1));
    }

    public void removeFromCart(Product product) {
        CartItem itemToRemove = null;

        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
        }
    }

    public void updateQuantity(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(quantity);

                // Remove item if quantity is 0
                if (quantity <= 0) {
                    cartItems.remove(item);
                }

                return;
            }
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
    }

    public void clearCart() {
        cartItems.clear();
    }
}