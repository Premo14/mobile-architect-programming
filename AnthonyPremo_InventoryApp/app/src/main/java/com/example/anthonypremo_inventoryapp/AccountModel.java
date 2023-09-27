package com.example.anthonypremo_inventoryapp;

import androidx.annotation.NonNull;

public class AccountModel {
    private final String username;
    private final String password;

    public AccountModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    @Override
    public String toString() {
        return "AccountModel{" +
                "username: '" + username +
                "\npassword: '" + password;
    }
}
