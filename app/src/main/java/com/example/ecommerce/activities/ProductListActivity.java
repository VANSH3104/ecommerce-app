package com.example.ecommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.ProductAdapter;
import com.example.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("E-Commerce App");

        // Initialize RecyclerView
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Create dummy data
        createDummyProducts();

        // Set up adapter
        productAdapter = new ProductAdapter(this, productList, this);
        recyclerViewProducts.setAdapter(productAdapter);
    }

    private void createDummyProducts() {
        productList = new ArrayList<>();

        // Add dummy products
        productList.add(new Product(1, "Smartphone", "Premium smartphone with 6.5-inch OLED display, powerful processor, and long-lasting battery", 799.99, R.drawable.product_phone, 4.5, "Electronics"));
        productList.add(new Product(2, "Laptop", "High-performance business laptop with 16GB RAM, 512GB SSD, and sleek design", 1299.99, R.drawable.product_laptop, 4.7, "Electronics"));
        productList.add(new Product(3, "Headphones", "Wireless noise-canceling headphones with rich sound quality and comfortable fit", 199.99, R.drawable.product_headphones, 4.2, "Accessories"));
        productList.add(new Product(4, "Smartwatch", "Stylish smartwatch with fitness tracking, heart rate monitor, and long battery life", 249.99, R.drawable.product_smartwatch, 4.4, "Wearables"));
        productList.add(new Product(5, "Camera", "Compact digital camera with 24MP sensor, 4K video support, and optical zoom", 699.99, R.drawable.product_camera, 4.6, "Electronics"));
        productList.add(new Product(6, "Tablet", "Lightweight 10-inch tablet with HD display, powerful performance, and dual speakers", 399.99, R.drawable.product_tablet, 4.3, "Electronics"));
        productList.add(new Product(7, "Speaker", "Portable Bluetooth speaker with deep bass and waterproof design", 129.99, R.drawable.speaker, 4.1, "Accessories"));
        productList.add(new Product(8, "Game Console", "Next-gen gaming console with ultra-fast load times and immersive graphics", 499.99, R.drawable.product_console, 4.8, "Gaming"));

// New products
        productList.add(new Product(9, "Shirt", "Casual slim-fit shirt made from breathable cotton fabric", 49.99, R.drawable.shirt, 4.3, "Clothing"));
        productList.add(new Product(10, "Shoes", "Stylish running shoes with cushioned sole and breathable mesh upper", 89.99, R.drawable.shoes, 4.6, "Footwear"));
        productList.add(new Product(11, "T-Shirt", "Comfortable cotton t-shirt with modern fit and durable stitching", 29.99, R.drawable.tshirt, 4.4, "Clothing"));
        productList.add(new Product(12, "Wallet", "Premium leather wallet with RFID protection and multiple compartments", 39.99, R.drawable.wallet, 4.5, "Accessories"));
        productList.add(new Product(13, "Power Bank", "10000mAh power bank with fast charging support and compact design", 59.99, R.drawable.powerbank, 4.4, "Electronics"));
        productList.add(new Product(14, "Sunglasses", "UV-protected stylish sunglasses with polarized lenses", 79.99, R.drawable.sunglasses, 4.3, "Accessories"));
        productList.add(new Product(15, "Jeans", "Classic straight-fit jeans made with stretchable denim for comfort", 69.99, R.drawable.jeans, 4.2, "Clothing"));


    }

    @Override
    public void onProductClick(Product product) {
        // Navigate to product detail activity
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            // Navigate to shopping cart
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            // Logout and return to login screen
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}