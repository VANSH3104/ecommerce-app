package com.example.ecommerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CartManager;
import com.example.ecommerce.models.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imageViewProduct;
    private TextView textViewName;
    private TextView textViewPrice;
    private TextView textViewDescription;
    private Button buttonAddToCart;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");

        // Initialize UI components
        imageViewProduct = findViewById(R.id.imageViewProductDetail);
        textViewName = findViewById(R.id.textViewProductName);
        textViewPrice = findViewById(R.id.textViewProductPrice);
        textViewDescription = findViewById(R.id.textViewProductDescription);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        // Get product from intent
        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            // Display product information
            imageViewProduct.setImageResource(product.getImageResourceId());
            textViewName.setText(product.getName());
            textViewPrice.setText(String.format("$%.2f", product.getPrice()));
            textViewDescription.setText(product.getDescription());

            buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add product to cart
                    CartManager.getInstance().addToCart(product);
                    Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}