package com.example.ecommerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CartAdapter;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.CartManager;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements CartAdapter.OnCartItemActionListener {

    private RecyclerView recyclerViewCart;
    private TextView textViewTotalPrice;
    private Button buttonCheckout;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shopping Cart");

        // Initialize UI components
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        // Set up RecyclerView
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        // Get cart items
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        // Set up adapter
        cartAdapter = new CartAdapter(this, cartItems, this);
        recyclerViewCart.setAdapter(cartAdapter);

        // Update total price
        updateTotalPrice();

        // Set up checkout button
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartManager.getInstance().getCartItems().isEmpty()) {
                    Toast.makeText(ShoppingCartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform checkout
                    Toast.makeText(ShoppingCartActivity.this, "Checkout feature coming soon!", Toast.LENGTH_SHORT).show();
                    // In a real app, you would navigate to a checkout screen
                }
            }
        });
    }

    private void updateTotalPrice() {
        double totalPrice = CartManager.getInstance().getTotalPrice();
        textViewTotalPrice.setText(String.format("Total: $%.2f", totalPrice));
    }

    @Override
    public void onRemoveFromCart(CartItem cartItem, int position) {
        // Remove item from cart
        CartManager.getInstance().removeFromCart(cartItem.getProduct());
        cartAdapter.notifyItemRemoved(position);
        updateTotalPrice();

        if (CartManager.getInstance().getCartItems().isEmpty()) {
            // Show empty cart message
            findViewById(R.id.textViewEmptyCart).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onQuantityChanged() {
        // Update total price when quantity changes
        updateTotalPrice();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}