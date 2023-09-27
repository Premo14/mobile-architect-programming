package com.example.anthonypremo_inventoryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_createAccount;
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.login_button);
        btn_createAccount = findViewById(R.id.btn_create_account);
        et_username = findViewById(R.id.username_edit_text);
        et_password = findViewById(R.id.password_edit_text);

        btn_login.setOnClickListener(view -> {
            AccountModel accountModel;

            try {
                accountModel = new AccountModel(et_username.getText().toString(), et_password.getText().toString());
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Error Creating Account", Toast.LENGTH_SHORT).show();
                accountModel = new AccountModel("Error", "Error");
            }

            DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
            boolean success = dbHelper.login(accountModel, LoginActivity.this);

            if (success) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btn_createAccount.setOnClickListener(view -> {
            AccountModel accountModel;

            try {
                accountModel = new AccountModel(et_username.getText().toString(), et_password.getText().toString());
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Error Creating Account.", Toast.LENGTH_SHORT).show();
                accountModel = new AccountModel("Error", "Error");
            }

            DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
            boolean success = dbHelper.addAccount(accountModel, LoginActivity.this);
            if (success) {
                Toast.makeText(LoginActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
