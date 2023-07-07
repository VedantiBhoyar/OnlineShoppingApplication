package com.example.onlineshoppingappliation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ShoppingListItem implements Parcelable{
    private int image;
    private String title;
    private String subtitle;
    private String price;
    private int itemId;

    public ShoppingListItem(int image, String title, String subtitle, String price, int itemId) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.itemId = itemId;
    }

    protected ShoppingListItem(Parcel in) {
        image = in.readInt();
        title = in.readString();
        subtitle = in.readString();
        price = in.readString();
    }

    public static final Parcelable.Creator<ShoppingListItem> CREATOR = new Parcelable.Creator<ShoppingListItem>() {
        @Override
        public ShoppingListItem createFromParcel(Parcel in) {
            return new ShoppingListItem(in);
        }

        @Override
        public ShoppingListItem[] newArray(int size) {
            return new ShoppingListItem[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(title);
        parcel.writeString(subtitle);
        parcel.writeString(price);
    }
}
