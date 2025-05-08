package com.example.ecommerce;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance;
    private List<CartItem> items;

    private ShoppingCart() {
        items = new ArrayList<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addProduct(Product product) {
        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // If not, add new item
        items.add(new CartItem(product, 1));
    }

    public void removeProduct(Product product) {
        CartItem itemToRemove = null;
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public void updateQuantity(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                if (quantity <= 0) {
                    items.remove(item);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    public String getTotalPrice() {
        float total = 0;
        for (CartItem item : items) {
            String priceStr = String.valueOf(item.getProduct().getPrice());
            try {
                float price = Float.parseFloat(priceStr);
                total += price * item.getQuantity();
            } catch (NumberFormatException e) {
                // Skip if unable to parse
            }
        }
        return "₹" + String.format("%,.0f", total);
    }

    public void clear() {
        items.clear();
    }
}