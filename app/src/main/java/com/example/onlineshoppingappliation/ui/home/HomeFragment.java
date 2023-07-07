package com.example.onlineshoppingappliation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingappliation.InfoActivity;
import com.example.onlineshoppingappliation.R;
import com.example.onlineshoppingappliation.SharedViewModel;
import com.example.onlineshoppingappliation.ShoppingListItem;
import com.example.onlineshoppingappliation.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ShoppingListItem> shoppingList = getShoppingList();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        adapter = new ShoppingListAdapter(shoppingList);
        recyclerView.setAdapter(adapter);

        sharedViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(List<ShoppingListItem> shoppingList) {
                adapter.updateCartIconColors();
                adapter.notifyDataSetChanged(); // Update the adapter when cart items change
            }
        });

        return rootView;
    }

    private List<ShoppingListItem> getShoppingList() {
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem(R.drawable.harrypotter1, "Harry Potter And Philosopher Stone", "BLOOMSBURY Publication", "Rs. 499", 1));
        items.add(new ShoppingListItem(R.drawable.hp2, "Harry Potter And Chamber Of Secret's", "BLOOMSBURY Publication", "Rs. 894", 2));
        items.add(new ShoppingListItem(R.drawable.hp3, "Harry Potter And Prisoner Of Azkaban", "BLOOMSBURY Publication", "Rs. 1,500", 3));
        items.add(new ShoppingListItem(R.drawable.hp4, "Harry Potter And Goblet Of Fire", "BLOOMSBURY Publication", "Rs. 1,200", 4));
        items.add(new ShoppingListItem(R.drawable.hp5, "Harry Potter And Order Of Phoenix", "BLOOMSBURY Publication", "Rs. 1,900", 5));
        items.add(new ShoppingListItem(R.drawable.hp6, "Harry Potter And Half Blood Prince", "BLOOMSBURY Publication", "Rs. 3,500", 6));
        items.add(new ShoppingListItem(R.drawable.hp7, "Harry Potter And Deathly Hollow", "BLOOMSBURY Publication ", "670", 7));
        items.add(new ShoppingListItem(R.drawable.hp8, "Harry Potter And Cursed Child", "BLOOMSBURY Publication", "Rs. 5,000", 8));
        items.add(new ShoppingListItem(R.drawable.book1, "Shiv Purana", "Ved Vyas Publication", "Rs. 900", 9));
        items.add(new ShoppingListItem(R.drawable.book2, "7 Secret's Of Shiva", "Devdooth Publication", "Rs. 499", 10));
        items.add(new ShoppingListItem(R.drawable.book3, "Death An Inside Story", "NewYork Publication", "Rs. 999", 11));
        items.add(new ShoppingListItem(R.drawable.book4, "Inner Engineering", "Isha Publication", "Rs. 399", 12));
        items.add(new ShoppingListItem(R.drawable.book5, "Fantastic Beasts And The Crimes Of Grindelwald", "J.K Rowling Publication", "Rs. 2000", 13));
        items.add(new ShoppingListItem(R.drawable.book6, "Fantastic Beasts And Where To Find Them", "J.K Rowling Publication", "Rs. 845", 14));


        // Add more items as needed
        return items;
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
        private List<ShoppingListItem> items;
        private List<Boolean> cartItemStates;

        public ShoppingListAdapter(List<ShoppingListItem> items) {
            this.items = items;
            this.cartItemStates = new ArrayList<>(Collections.nCopies(items.size(), false));
        }

        @NonNull
        @Override
        public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            return new ShoppingListViewHolder(view);
        }

        public void updateCartIconColors() {
            for (int i = 0; i < items.size(); i++) {
                ShoppingListItem item = items.get(i);
                boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
                cartItemStates.set(i, isInCart);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
            ShoppingListItem item = items.get(position);
            holder.bind(item);
            boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
            holder.updateCartIconColor(isInCart);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, cartIcon;
        TextView titleTextView;
        TextView subtitleTextView;
        TextView priceTextView;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.txt_company);
            subtitleTextView = itemView.findViewById(R.id.txt_product_title);
            priceTextView = itemView.findViewById(R.id.price);
            cartIcon = itemView.findViewById(R.id.cart_icon);
        }

        public void updateCartIconColor(boolean isInCart) {
            if (isInCart) {
                cartIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.green));
            } else {
                cartIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.red));
            }
        }

        public void bind(ShoppingListItem item) {
            imageView.setImageResource(item.getImage());
            titleTextView.setText(item.getTitle());
            subtitleTextView.setText(item.getSubtitle());
            priceTextView.setText(item.getPrice());

            boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
            if (isInCart) {
                cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            } else {
                cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red));
            }

            cartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
                    if (!isInCart) {
                        sharedViewModel.addToCart(item);
                        cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                        Toast.makeText(requireContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Item is already in cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = item.getItemId();
                    Intent intent = new Intent(getActivity(), InfoActivity.class);
                    switch (id) {
                        case 1: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_first));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 2: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_second));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 3: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_third));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 4: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_four));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 5: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_five));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 6: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_six));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }

                        case 7: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_seven));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 8: {
                            intent.putExtra("harry-potter1", getString(R.string.hp_eigth));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 9: {
                            intent.putExtra("harry-potter1", getString(R.string.book));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 10: {
                            intent.putExtra("harry-potter1", getString(R.string.book_one));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 11: {
                            intent.putExtra("harry-potter1", getString(R.string.book_two));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 12: {
                            intent.putExtra("harry-potter1", getString(R.string.book_third));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 13: {
                            intent.putExtra("harry-potter1", getString(R.string.book_four));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }
                        case 14: {
                            intent.putExtra("harry-potter1", getString(R.string.book_five));
                            intent.putExtra("ID",item.getTitle());
                            startActivity(intent);
                            break;
                        }

                    }
                }
            });
        }
    }
}
