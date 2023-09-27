package com.example.anthonypremo_inventoryapp;

import androidx.annotation.NonNull;

public class ItemModel {
    private final String name;
    private final int quantity;

    public ItemModel(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item: " + name + "\nStock: " + quantity;
    }
}
