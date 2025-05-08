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
        productList.add(new Product(1, "Smartphone", "High-end smartphone with 6.5-inch display", 799.99, R.drawable.product_phone, 4.5, "Electronics"));
        productList.add(new Product(2, "Laptop", "Business laptop with 16GB RAM and 512GB SSD", 1299.99, R.drawable.product_laptop, 4.7, "Electronics"));
        productList.add(new Product(3, "Headphones", "Wireless noise-canceling headphones", 199.99, R.drawable.product_headphones, 4.2, "Accessories"));
        productList.add(new Product(4, "Smartwatch", "Fitness tracker with heart rate monitor", 249.99, R.drawable.product_smartwatch, 4.4, "Wearables"));
        productList.add(new Product(5, "Camera", "Digital camera with 24MP sensor", 699.99, R.drawable.product_camera, 4.6, "Electronics"));
        productList.add(new Product(6, "Tablet", "10-inch tablet with HD display", 399.99, R.drawable.product_tablet, 4.3, "Electronics"));
        productList.add(new Product(7, "Speaker", "Bluetooth wireless speaker", 129.99, R.drawable.product_speaker, 4.1, "Accessories"));
        productList.add(new Product(8, "Game Console", "Next-gen gaming console", 499.99, R.drawable.product_console, 4.8, "Gaming"));

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