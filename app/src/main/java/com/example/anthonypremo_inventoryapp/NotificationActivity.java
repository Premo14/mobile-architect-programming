package com.example.anthonypremo_inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    TextView tv_outOfNotification;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        tv_outOfNotification =findViewById(R.id.notification);
        String data = getIntent().getStringExtra("outOfStockNotification");
        tv_outOfNotification.setText(data);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> openMainActivity());
    }

    public void openMainActivity() {
        Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
