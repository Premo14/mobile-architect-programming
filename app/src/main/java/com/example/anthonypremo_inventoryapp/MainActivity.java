package com.example.anthonypremo_inventoryapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btn_addItem, btn_removeItem, btn_updateItem, btn_signOut;
    EditText et_itemName, et_itemQuantity;
    ListView lv_inventoryList;
    ArrayAdapter itemArrayAdapter;
    Switch notiSwitch;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_addItem = findViewById(R.id.add_item_button);
        btn_removeItem = findViewById(R.id.remove_item_button);
        btn_updateItem = findViewById(R.id.update_item_button);
        btn_signOut = findViewById(R.id.sign_out_button);
        et_itemName = findViewById(R.id.edit_text_item_name);
        et_itemQuantity = findViewById(R.id.edit_text_item_quantity);
        lv_inventoryList = findViewById(R.id.inventory_grid);
        notiSwitch = findViewById(R.id.notificationsSwitch);

        dbHelper = new DatabaseHelper(MainActivity.this);

        showItemsList(dbHelper);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        btn_addItem.setOnClickListener(view -> {
            ItemModel itemModel;

            try {
                itemModel = new ItemModel(et_itemName.getText().toString(), Integer.parseInt(et_itemQuantity.getText().toString()));
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error creating item", Toast.LENGTH_SHORT).show();
                itemModel = new ItemModel("error", -1);
            }

            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            boolean success = dbHelper.addItem(itemModel, MainActivity.this);
            showItemsList(dbHelper);

            if (success) {
                Toast.makeText(MainActivity.this, "Successfully Created:\n" + itemModel, Toast.LENGTH_SHORT).show();
            }
        });

        btn_removeItem.setOnClickListener(view -> {
            ItemModel itemModel;

            try {
                itemModel = new ItemModel(et_itemName.getText().toString(), Integer.parseInt(et_itemQuantity.getText().toString()));
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error creating item", Toast.LENGTH_SHORT).show();
                itemModel = new ItemModel("error", -1);
            }

            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            boolean success = dbHelper.removeItem(itemModel, MainActivity.this);
            showItemsList(dbHelper);

            if (success) {
                Toast.makeText(MainActivity.this, "Successfully Removed:\n" + itemModel, Toast.LENGTH_SHORT).show();
            }
        });

        btn_updateItem.setOnClickListener(view -> {
            ItemModel itemModel;

            try {
                itemModel = new ItemModel(et_itemName.getText().toString(), Integer.parseInt(et_itemQuantity.getText().toString()));
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error creating item.", Toast.LENGTH_SHORT).show();
                itemModel = new ItemModel("error", -1);
            }

            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
            boolean success = dbHelper.updateItem(itemModel, MainActivity.this);
            showItemsList(dbHelper);

            if (success) {
                Toast.makeText(MainActivity.this, "Successfully Updated:\n" + itemModel, Toast.LENGTH_SHORT).show();
            }
            checkItemStock(itemModel);
        });

        btn_signOut.setOnClickListener(view -> openLoginActivity());
    }

    public void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void checkItemStock(ItemModel currentItem) {
        if (notiSwitch.isChecked()) {
            if (currentItem.getQuantity() < 1) {
                outOfStockNotification(currentItem);
            }
        }
    }

    public void outOfStockNotification(ItemModel itemModel) {
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, channelId);
        builder.setContentTitle(itemModel.getName() + " is out of stock.")
                .setSmallIcon(R.drawable.baseline_notifications_icon)
                .setContentText("Please add stock to " + itemModel.getName())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelId, itemModel.getName() + " is out of stock.", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

        notificationManager.notify(0, builder.build());
    }

    public void showItemsList(DatabaseHelper dbHelper2) {
        itemArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbHelper2.getAllItems());
        lv_inventoryList.setAdapter(itemArrayAdapter);
    }
}