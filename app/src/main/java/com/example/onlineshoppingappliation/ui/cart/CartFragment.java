package com.example.onlineshoppingappliation.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingappliation.R;
import com.example.onlineshoppingappliation.SharedViewModel;
import com.example.onlineshoppingappliation.ShoppingListItem;
import com.example.onlineshoppingappliation.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private List<ShoppingListItem> cartItems;
    private RecyclerView recyclerView;
    private CartListAdapter adapter;
    private SharedViewModel sharedViewModel;
    private TextView textNoItems;
    private Button btn_place_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        recyclerView = binding.cartRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        textNoItems = rootView.findViewById(R.id.text_cart);
        btn_place_order = rootView.findViewById(R.id.btn_place_order);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        cartItems = new ArrayList<>();
        adapter = new CartListAdapter(cartItems);
        recyclerView.setAdapter(adapter);


        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();

                sharedViewModel.clearCart();
                btn_place_order.setVisibility(View.INVISIBLE);
            }
        });


        sharedViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(List<ShoppingListItem> shoppingList) {
                adapter.setItems(shoppingList);
                updateNoItemsVisibility(shoppingList);
            }
        });

        return rootView;
    }

    private void updateNoItemsVisibility(List<ShoppingListItem> shoppingList) {
        if (shoppingList != null && shoppingList.isEmpty()) {
            textNoItems.setVisibility(View.VISIBLE);
        } else {
            textNoItems.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class CartListAdapter extends RecyclerView.Adapter<CartViewHolder> {
        private List<ShoppingListItem> cartItems;

        public CartListAdapter(List<ShoppingListItem> cartItems) {
            this.cartItems = cartItems;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            ShoppingListItem item = cartItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        public void setItems(List<ShoppingListItem> cartItems) {
            this.cartItems = cartItems;
            notifyDataSetChanged();
        }
    }

    private static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView subtitleTextView;
        TextView priceTextView;
        ImageView cartIcon;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.txt_company);
            subtitleTextView = itemView.findViewById(R.id.txt_product_title);
            priceTextView = itemView.findViewById(R.id.price);
            cartIcon = itemView.findViewById(R.id.cart_icon);
        }

        public void bind(ShoppingListItem item) {
            imageView.setImageResource(item.getImage());
            titleTextView.setText(item.getTitle());
            subtitleTextView.setText(item.getSubtitle());
            priceTextView.setText(item.getPrice());

            cartIcon.setVisibility(View.GONE);
        }
    }
}
