package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.activities.ProductDetailActivity;
import com.example.ecommerce.activities.ShoppingCartActivity;
import com.example.ecommerce.adapter.ProductAdapter;
import com.example.ecommerce.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;
    private ArrayList<Product> filteredList;
    private Spinner categorySpinner;
    private FloatingActionButton cartFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        categorySpinner = findViewById(R.id.categorySpinner);
        cartFab = findViewById(R.id.cartFab);

        // Setup RecyclerView with Grid Layout (2 columns)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize product data
        initializeProducts();

        // Setup adapter
        filteredList = new ArrayList<>(productList);
        adapter = new ProductAdapter(this, filteredList, this);
        recyclerView.setAdapter(adapter);

        // Setup category filter
        setupCategoryFilter();

        // Setup cart button
        cartFab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

        updateCartBadge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void initializeProducts() {
        productList = new ArrayList<>();

        // Clothing
        productList.add(new Product("Basic T-shirt", "₹499", R.drawable.tshirt,
                "Comfortable cotton t-shirt for everyday wear", "Clothing", 4.2f));
        productList.add(new Product("Denim Jeans", "₹1,299", R.drawable.jeans,
                "Classic blue denim jeans with perfect fit", "Clothing", 4.5f));
        productList.add(new Product("Formal Shirt", "₹899", R.drawable.shirt,
                "Crisp formal shirt for office wear", "Clothing", 4.0f));

        // Footwear
        productList.add(new Product("Running Shoes", "₹1,999", R.drawable.shoes,
                "Comfortable running shoes with cushioned soles", "Footwear", 4.7f));
        productList.add(new Product("Casual Sneakers", "₹1,499", R.drawable.product_speaker,
                "Trendy sneakers for casual outings", "Footwear", 4.3f));
        productList.add(new Product("Formal Shoes", "₹2,499", R.drawable.formal_shoes,
                "Classic formal shoes for professional settings", "Footwear", 4.1f));

        // Accessories
        productList.add(new Product("Classic Watch", "₹2,999", R.drawable.product_smartwatch,
                "Elegant timepiece for all occasions", "Accessories", 4.8f));
        productList.add(new Product("Leather Wallet", "₹799", R.drawable.wallet,
                "Genuine leather wallet with multiple card slots", "Accessories", 4.4f));
        productList.add(new Product("Sunglasses", "₹999", R.drawable.sunglasses,
                "UV protected stylish sunglasses", "Accessories", 4.2f));

        // Electronics
        productList.add(new Product("Wireless Earbuds", "₹3,999", R.drawable.earbuds,
                "Premium wireless earbuds with noise cancellation", "Electronics", 4.6f));
        productList.add(new Product("Bluetooth Speaker", "₹1,499", R.drawable.speaker,
                "Portable Bluetooth speaker with deep bass", "Electronics", 4.3f));
        productList.add(new Product("Power Bank", "₹1,299", R.drawable.powerbank,
                "10000mAh fast charging power bank", "Electronics", 4.5f));
    }

    private void setupCategoryFilter() {
        // Get unique categories
        Set<String> categories = new HashSet<>();
        categories.add("All Categories");

        for (Product product : productList) {
            categories.add(product.getCategory());
        }

        // Setup spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new ArrayList<>(categories));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterProducts(parent.getItemAtPosition(position).toString(), null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void filterProducts(String category, String query) {
        filteredList.clear();

        for (Product product : productList) {
            boolean categoryMatch = category.equals("All Categories") || product.getCategory().equals(category);
            boolean queryMatch = query == null || query.isEmpty() ||
                    product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(query.toLowerCase());

            if (categoryMatch && queryMatch) {
                filteredList.add(product);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void updateCartBadge() {
        int itemCount = ShoppingCart.getInstance().getItemCount();
        if (itemCount > 0) {
            cartFab.setContentDescription("Shopping Cart with " + itemCount + " items");
            // Note: In a real app you'd set a badge on the FAB using BadgeDrawable
        } else {
            cartFab.setContentDescription("Shopping Cart (empty)");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String category = categorySpinner.getSelectedItem().toString();
                filterProducts(category, newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("PRODUCT_NAME", product.getName());
        intent.putExtra("PRODUCT_PRICE", product.getPrice());
        intent.putExtra("PRODUCT_IMAGE", product.getImageResourceId());
        intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
        intent.putExtra("PRODUCT_CATEGORY", product.getCategory());
        intent.putExtra("PRODUCT_RATING", product.getRating());
        startActivity(intent);
    }
}