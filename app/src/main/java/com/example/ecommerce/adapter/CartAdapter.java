package com.example.ecommerce.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    public interface OnCartItemActionListener {
        void onRemoveFromCart(CartItem cartItem, int position);
        void onQuantityChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.imageViewProduct.setImageResource(cartItem.getProduct().getImageResourceId());
        holder.textViewName.setText(cartItem.getProduct().getName());
        holder.textViewPrice.setText(String.format("$%.2f", cartItem.getProduct().getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.textViewTotalPrice.setText(String.format("$%.2f", cartItem.getTotalPrice()));

        // Set click listeners
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onRemoveFromCart(cartItem, adapterPosition);
                }
            }
        });

        holder.buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQuantity();
                if (quantity > 1) {
                    CartManager.getInstance().updateQuantity(cartItem.getProduct(), quantity - 1);
                    holder.textViewQuantity.setText(String.valueOf(quantity - 1));
                    holder.textViewTotalPrice.setText(String.format("$%.2f", cartItem.getTotalPrice()));
                    listener.onQuantityChanged();
                }
            }
        });

        holder.buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQuantity();
                CartManager.getInstance().updateQuantity(cartItem.getProduct(), quantity + 1);
                holder.textViewQuantity.setText(String.valueOf(quantity + 1));
                holder.textViewTotalPrice.setText(String.format("$%.2f", cartItem.getTotalPrice()));
                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewQuantity;
        TextView textViewTotalPrice;
        ImageButton buttonDecrease;
        ImageButton buttonIncrease;
        ImageButton buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewCartProduct);
            textViewName = itemView.findViewById(R.id.textViewCartProductName);
            textViewPrice = itemView.findViewById(R.id.textViewCartProductPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewTotalPrice = itemView.findViewById(R.id.textViewCartItemTotal);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}