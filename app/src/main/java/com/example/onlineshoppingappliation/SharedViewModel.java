package com.example.onlineshoppingappliation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<ShoppingListItem>> cartItems;

    public SharedViewModel() {
        cartItems = new MutableLiveData<>();
        cartItems.setValue(new ArrayList<>());
    }

    public LiveData<List<ShoppingListItem>> getCartItems() {
        return cartItems;
    }

    public void addToCart(ShoppingListItem item) {
        List<ShoppingListItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.add(item);
            cartItems.setValue(currentItems);
        }
    }

    public void removeFromCart(ShoppingListItem item) {
        List<ShoppingListItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.remove(item);
            cartItems.setValue(currentItems);
        }
    }

    public boolean isInCartById(int itemId) {
        List<ShoppingListItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            for (ShoppingListItem item : currentItems) {
                if (item.getItemId() == itemId) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setItems(List<ShoppingListItem> items) {
        cartItems.setValue(new ArrayList<>(items));
    }

    public void clearCart() {
        cartItems.setValue(new ArrayList<>());
    }
}
